// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import au.com.capitalradiology.BaseActivity;
import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Adapter;
import au.com.capitalradiology.Utils.GPSTracker;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.listadapter.LocationAdapter;
import au.com.capitalradiology.model.Location;

public class LocationListActivity extends BaseActivity
{
    private List<Location.Resource> feedsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LocationAdapter adapter;
    private Context context = LocationListActivity.this;
    private GoogleMap map = null;
    private Button btnList = null;
    private Button btnMap = null;
    private LinearLayout lnr_Map = null;
    private Toolbar mToolbar;
    private MyApplication myApplication;
    private LatLng latlon = new LatLng(37.8136,144.9631); // Melbourne

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.location_list_screen);

        initUI();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.location_name));
        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.location_name));

        initList();

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnList.setTextColor(getResources().getColor(R.color.white));
                btnList.setBackgroundColor(getResources().getColor(R.color.accent_material_light));
                btnMap.setTextColor(getResources().getColor(R.color.accent_material_light));
                btnMap.setBackgroundColor(getResources().getColor(R.color.white));

                lnr_Map.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnList.setTextColor(getResources().getColor(R.color.accent_material_light));
                btnList.setBackgroundColor(getResources().getColor(R.color.white));
                btnMap.setTextColor(getResources().getColor(R.color.white));
                btnMap.setBackgroundColor(getResources().getColor(R.color.accent_material_light));
                lnr_Map.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                getCurrent_Location();
            }
        });
    }

    private void initUI()
    {
        mRecyclerView = (RecyclerView)findViewById(R.id.myRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lnr_Map = (LinearLayout)findViewById(R.id.lnr_map);
        btnList = (Button)findViewById(R.id.btnList);
        btnMap = (Button)findViewById(R.id.btnMap);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_action);
        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
    }

    private void initList()
    {
        Adapter adb = new Adapter(context);
        adb.getLocation(new Adapter.SynceDataListener<Location>() {
            @Override
            public void onSynced(Location bin) {
                if(bin!=null && bin.getResource().size()>0) {
                    feedsList = bin.getResource();
                    adapter = new LocationAdapter(LocationListActivity.this, feedsList);
                    mRecyclerView.setAdapter(adapter);
                }
                else
                {
                    adapter = new LocationAdapter(LocationListActivity.this, feedsList);
                    mRecyclerView.setAdapter(adapter);
                }
            }
        });
    }

    private void getCurrent_Location()
    {
        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation())
        {
            map.clear();

            if(feedsList.size()>0)
             setUpMap(feedsList);
        }
        else {
            mapDisplay(latlon.latitude,latlon.longitude,map);
            gps.showSettingsAlert();
        }
    }

    private void mapDisplay(double marker_latitude,double marker_longitude, GoogleMap map)
    {
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

    private void setUpMap(final List<Location.Resource> list_markers) {

        for (int i=0;i<list_markers.size();i++)
        {
            map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(list_markers.get(i).getLatitude()),Double.parseDouble(list_markers.get(i).getLongitude()))).icon(
                    BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .draggable(false));

        }
        LatLng location = new LatLng(Double.parseDouble(list_markers.get(0).getLatitude()),Double.parseDouble(list_markers.get(0).getLongitude()));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 8.5f));

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                LatLng latlong = marker.getPosition();
                if(list_markers.size()>0)
                {
                    for(int i =0;i<list_markers.size();i++)
                    {
                        if(list_markers.get(i).getLatitude().equalsIgnoreCase(String.valueOf(latlong.latitude)) && list_markers.get(i).getLongitude().equalsIgnoreCase(String.valueOf(latlong.longitude)))
                        {
                            Intent intent = new Intent(context, LocationDetail.class);
                            intent.putExtra("LocationName", list_markers.get(i).getLocation());
                            intent.putExtra("Image_Url", list_markers.get(i).getImageMainUrl());
                            intent.putExtra("Time", list_markers.get(i).getOpeningTimes());
                            intent.putExtra("Fax", list_markers.get(i).getFax());
                            intent.putExtra("Phone", list_markers.get(i).getPhone());
                            intent.putExtra("Address", list_markers.get(i).getFullAddress());
                            intent.putExtra("Services", list_markers.get(i).getServiceNames());
                            intent.putExtra("Location_ID",list_markers.get(i).getLocationId());
                            startActivity(intent);
                            break;
                        }
                    }
                }
                return false;
            }
        });
    }
}
