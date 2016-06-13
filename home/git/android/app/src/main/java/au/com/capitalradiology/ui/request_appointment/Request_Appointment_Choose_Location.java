package au.com.capitalradiology.ui.request_appointment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import au.com.capitalradiology.BaseActivity;
import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Adapter;
import au.com.capitalradiology.Utils.GPSTracker;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.listadapter.PlaceAutocompleteAdapter;
import au.com.capitalradiology.listadapter.SpinAdapter;
import au.com.capitalradiology.model.BinForChooseLocation;
import au.com.capitalradiology.model.BinForServiceList;


public class Request_Appointment_Choose_Location extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks
{
    private TextView txt_Urgent;
    private TextView txt_Normal;
    private Spinner spn_ServiceType;
    private Toolbar mToolbar;
    private Context context = Request_Appointment_Choose_Location.this;
    private PlaceAutocompleteAdapter mAdapter;
    private AutoCompleteTextView mAutocomplete_Places;
    private AutoCompleteTextView mAutocomplete_Destination;
    private GoogleMap map = null;
    protected GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    private String strService_Type;
    private int strService_ID;
    private SpinAdapter dataAdapter;
    private MyApplication myApplication;
    private double lat ;
    private double lon ;
    private int ServiceCost;
    private int OriginalServiceCost;
    private String currencyType;
    private boolean isUrgent = false;
    private LatLng latlon = new LatLng(37.8136,144.9631); // Melbourne

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.request_apointment_choose_location);

        initUI();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.Request_Appointment));

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Request_Appointment_chooseService));

        if (mGoogleApiClient == null)
        {
            rebuildGoogleApiClient();
        }

        mAutocomplete_Places.setOnItemClickListener(mAutocomplete_place_ClickListener);
        mAutocomplete_Destination.setOnItemClickListener(mAutocomplete_destomation_ClickListener);

        mAdapter = new PlaceAutocompleteAdapter(this, android.R.layout.simple_list_item_1,BOUNDS_GREATER_SYDNEY, null);
        mAutocomplete_Places.setAdapter(mAdapter);
        mAutocomplete_Destination.setAdapter(mAdapter);

        getCurrent_Location();
        getServices();

        txt_Urgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.yes_no_alertDialog(context, getResources().getString(R.string.app_name), "You will pay $xx for same day request", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == -1) {
                            txt_Urgent.setTextColor(getResources().getColor(R.color.white));
                            txt_Urgent.setBackgroundColor(getResources().getColor(R.color.color_app_primary));
                            txt_Normal.setTextColor(getResources().getColor(R.color.color_app_primary));
                            txt_Normal.setBackgroundColor(getResources().getColor(R.color.white));
                            ServiceCost = ServiceCost*2;
                            isUrgent  = true;
                        } else {
                            txt_Normal.setTextColor(getResources().getColor(R.color.white));
                            txt_Normal.setBackgroundColor(getResources().getColor(R.color.color_app_primary));
                            txt_Urgent.setTextColor(getResources().getColor(R.color.color_app_primary));
                            txt_Urgent.setBackgroundColor(getResources().getColor(R.color.white));
                            ServiceCost = OriginalServiceCost;
                            isUrgent = false;
                        }
                    }
                });
            }
        });

        txt_Normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt_Normal.setTextColor(getResources().getColor(R.color.white));
                txt_Normal.setBackgroundColor(getResources().getColor(R.color.color_app_primary));
                txt_Urgent.setTextColor(getResources().getColor(R.color.color_app_primary));
                txt_Urgent.setBackgroundColor(getResources().getColor(R.color.white));
                ServiceCost = OriginalServiceCost;
                isUrgent = false;
            }
        });

        spn_ServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(dataAdapter!=null) {
                    strService_Type = dataAdapter.getItem(pos).getServiceName().toString();
                    strService_ID = dataAdapter.getItem(pos).getServiceId();
                    ServiceCost = dataAdapter.getItem(pos).getCost();
                    OriginalServiceCost = dataAdapter.getItem(pos).getCost();
                    currencyType = dataAdapter.getItem(pos).getCurrency();
                    getNearestClinics(lat,lon);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initUI()
    {
        txt_Urgent = (TextView)findViewById(R.id.txt_Urgent);
        txt_Normal = (TextView)findViewById(R.id.txt_Noraml);
        spn_ServiceType = (Spinner)findViewById(R.id.spn_ServiceType);
        mAutocomplete_Places=(AutoCompleteTextView)findViewById(R.id.autocomplete_places);
        mAutocomplete_Destination=(AutoCompleteTextView)findViewById(R.id.autocomplete_destination);
        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        mToolbar = (Toolbar)findViewById(R.id.toolbar_action);
    }

    private void getCurrent_Location()
    {
        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation())
        {
            lat = gps.getLatitude();
            lon = gps.getLongitude();
            mapDisplay(gps.getLatitude(), gps.getLongitude(), map);

        }
        else {
            mapDisplay(latlon.latitude,latlon.longitude,map);
            gps.showSettingsAlert();
        }
    }

    private void mapDisplay(double marker_latitude,double marker_longitude, GoogleMap map)
    {
        map.clear();
        map.getUiSettings().setZoomControlsEnabled(false);
        LatLng location = new LatLng(marker_latitude, marker_longitude);
        map.addMarker(new MarkerOptions().position(location).icon(
                BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))/*
																			 * BitmapDescriptorFactory
																			 * .
																			 * fromResource
																			 * (R.
																			 * drawable
																			 * .
																			 * form_locate
																			 * ))
																			 */);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10.5f));

    }

    private AdapterView.OnItemClickListener mAutocomplete_place_ClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a PlaceAutocomplete object from which we
             read the place ID.
              */
            final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Utils.LogE("Autocomplete place item selected: " + item.description);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Utils.LogE("Called getPlaceById to get Place details for " + item.placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>()
    {
        @Override
        public void onResult(PlaceBuffer places)
        {
            if (!places.getStatus().isSuccess())
            {
                // Request did not complete successfully
                Utils.LogE("Place query did not complete. Error: " + places.getStatus().toString());
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            // Format details of the place for display and show it in a TextView.
            mAutocomplete_Places.setText(place.getName()+","+place.getAddress());
            Utils.hideSoftKeyboard(Request_Appointment_Choose_Location.this);
            mapDisplay(place.getLatLng().latitude,place.getLatLng().longitude,map);

        }
    };


    private AdapterView.OnItemClickListener mAutocomplete_destomation_ClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a PlaceAutocomplete object from which we
             read the place ID.
              */
            final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Utils.LogE("Autocomplete Destination item selected: " + item.description);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdateDestinationDetailsCallback);

            Utils.LogE("Called getPlaceById to get Place details for " + item.placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdateDestinationDetailsCallback = new ResultCallback<PlaceBuffer>()
    {
        @Override
        public void onResult(PlaceBuffer places)
        {
            if (!places.getStatus().isSuccess())
            {
                // Request did not complete successfully
                Utils.LogE("Place query did not complete. Error: " + places.getStatus().toString());
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            // Format details of the place for display and show it in a TextView.
            mAutocomplete_Destination.setText(place.getName()+","+place.getAddress());
            lat = place.getLatLng().latitude;
            lon = place.getLatLng().longitude;

            Utils.hideSoftKeyboard(Request_Appointment_Choose_Location.this);

            getNearestClinics(lat,lon);

        }
    };

    private void getNearestClinics(double lat,double lon)
    {
        Adapter adb = new Adapter(context);
        adb.getNearestClinic(strService_ID,lat,lon,new Adapter.SynceDataListener<BinForChooseLocation>()
        {
            @Override
            public void onSynced(BinForChooseLocation bin) {
                if(bin!=null)
                {
                    if(bin.getRecord().size()>0) {
                        ArrayList<BinForChooseLocation.Record> list = new ArrayList<BinForChooseLocation.Record>();
                        list = bin.getRecord();
                        setUpMap(list);
                    }
                    else
                        Utils.showToast(context,"No Clinic found");

                }
            }
        });
    }

    private void getServices()
    {
        Adapter adb = new Adapter(context);
        adb.getService(new Adapter.SynceDataListener<BinForServiceList>() {
            @Override
            public void onSynced(BinForServiceList bin) {
                if(bin!=null) {
                    try {

                        dataAdapter = new SpinAdapter(context,bin.getResource());
                        spn_ServiceType.setAdapter(dataAdapter);
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        });
    }


    private void setUpMap(final List<BinForChooseLocation.Record> list_markers) {

        map.clear();
        for (int i=0;i<list_markers.size();i++)
        {
            map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(list_markers.get(i).getLatitude()), Double.parseDouble(list_markers.get(i).getLongitude()))).icon(
                    BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title(list_markers.get(i).getLocation_name())
                    .snippet("Clinic")
                    .draggable(false));

        }
        LatLng location = new LatLng(Double.parseDouble(list_markers.get(0).getLatitude()),Double.parseDouble(list_markers.get(0).getLongitude()));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 8.5f));

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                LatLng latlong = marker.getPosition();
                Utils.LogE("lat=================="+latlong.latitude);
                Utils.LogE("long=============="+latlong.longitude);
                if(list_markers.size()>0)
                {
                    for(int i =0;i<list_markers.size();i++)
                    {
                        if(list_markers.get(i).getLatitude().equalsIgnoreCase(String.valueOf(latlong.latitude)) && list_markers.get(i).getLongitude().equalsIgnoreCase(String.valueOf(latlong.longitude)))
                        {
                            Intent intent = new Intent(context, Request_Appointment_Apply.class);
                            intent.putExtra("Latitude", Double.parseDouble(list_markers.get(i).getLatitude()));
                            intent.putExtra("Longtitude", Double.parseDouble(list_markers.get(i).getLongitude()));
                            intent.putExtra("Location_ID", list_markers.get(i).getLocationId());
                            intent.putExtra("Service_ID",strService_ID);
                            intent.putExtra("Service_Cost",ServiceCost);
                            intent.putExtra("CurrencyType",currencyType);
                            intent.putExtra("isUrgent",isUrgent);
                            startActivity(intent);
                            break;
                        }
                    }
                }
                return false;
            }
        });
    }

    protected synchronized void rebuildGoogleApiClient() {
        // When we build the GoogleApiClient we specify where connected and connection failed
        // callbacks should be returned, which Google APIs our app uses and which OAuth 2.0
        // scopes our app requests.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addConnectionCallbacks(this)
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    /**
     * Called when the Activity could not connect to Google Play services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be inspected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Utils.LogE("onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();

        // Disable API access in the adapter because the client was not initialised correctly.
        mAdapter.setGoogleApiClient(null);
    }


    @Override
    public void onConnected(Bundle bundle) {
        // Successfully connected to the API client. Pass it to the adapter to enable API access.
        mAdapter.setGoogleApiClient(mGoogleApiClient);
        Utils.LogE("GoogleApiClient connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Connection to the API client has been suspended. Disable API access in the client.
        mAdapter.setGoogleApiClient(null);
        Utils.LogE("GoogleApiClient connection suspended.");
    }

    private void displayCurrentLocation(double latitude,double longitude)
    {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();

            mAutocomplete_Places.setText(address+","+state+","+country);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
