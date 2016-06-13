package au.com.capitalradiology.ui.request_appointment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.phlox.datepick.CalendarNumbersView;
import com.phlox.datepick.CalendarPickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import au.com.capitalradiology.BaseActivity;
import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Adapter;
import au.com.capitalradiology.Utils.GetResponse;
import au.com.capitalradiology.Utils.ProfileAsync;
import au.com.capitalradiology.Utils.UserSession;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.dreamfactory_api.ApiException;
import au.com.capitalradiology.dreamfactory_api.BaseAsyncRequest;
import au.com.capitalradiology.dreamfactory_api.FileRequest;
import au.com.capitalradiology.listadapter.Available_Time_Adapter;
import au.com.capitalradiology.model.BinForAvailable_Time;


public class Request_Appointment_Apply extends BaseActivity
{
    private TextView txt_CostEstimate;
    private TextView txt_PromoCode;
    private PopupWindow calendarPopup;
    private TextView txt_RequestAppointment;
    private Spinner spn_Time;
    private ImageView img_Referral_Doc;
    private Toolbar mToolbar;
    private Context context = Request_Appointment_Apply.this;
    private GoogleMap map = null;
    protected GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    private MyApplication myApplication;
    private double lat;
    private double lon;
    private String location_ID;
    private int Service_ID;
    private Available_Time_Adapter dataAdapter;
    private ImageView img_Calender;
    private TextView txt_ChooseDate;

    private String[] select_photo_option = new String[]{"Take picture", "Open gallery",};
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    public static final int REQUEST_PROMO = 0x4;
    private Uri croppedImageUri;
    private Uri capturedImageUri;
    private String image_Path_ReferralDoc = "";
    private String slot_Id = "";
    private int service_Cost;
    private String currency_Type;
    private boolean isUrgent;
    private double final_service_Cost;
    private String strPromoCode = "";

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.request_apointment_apply);

        initUI();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.Request_Appointment_book));

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Request_Appointment_book));

        Utils.createDirectory();

       if(getIntent().getExtras()!=null)
       {
           lat = getIntent().getDoubleExtra("Latitude",0);
           lon = getIntent().getDoubleExtra("Longtitude",0);
           location_ID = getIntent().getStringExtra("Location_ID");
           Service_ID = getIntent().getIntExtra("Service_ID", 0);
           service_Cost = getIntent().getIntExtra("Service_Cost",0);
           final_service_Cost  = (double)service_Cost;
           currency_Type = getIntent().getStringExtra("CurrencyType");
           isUrgent = getIntent().getBooleanExtra("isUrgent", false);
           mapDisplay(lat,lon,map);
           getAvailable_Time_Slot(Utils.getcurrentDate());
       }

        img_Referral_Doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showImageDialog(context, "Choose Action", select_photo_option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (arg1 == 0)
                            takePicture();
                        else
                            openGallery();
                    }
                });
            }
        });

        txt_ChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarPopup == null) {
                    calendarPopup = new PopupWindow(context);
                    CalendarPickerView calendarView = new CalendarPickerView(context);
                    calendarView.setListener(dateSelectionListener);
                    calendarPopup.setContentView(calendarView);
                    calendarPopup.setWindowLayoutMode(
                            View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    calendarPopup.setHeight(1);
                    calendarPopup.setWidth(view.getWidth());
                    calendarPopup.setOutsideTouchable(true);

                }
                calendarPopup.showAsDropDown(view);
            }
        });

        txt_RequestAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(image_Path_ReferralDoc.equalsIgnoreCase("")) {
                    Utils.alertDialogShow(context, getString(R.string.app_name), "Please attach referral doc", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                }
                else if(slot_Id.equalsIgnoreCase(""))
                {
                    Utils.alertDialogShow(context, getString(R.string.app_name), "Please select time slot", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                }
                else
                    checkPatient_Already_Booked();

            }
        });

        spn_Time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(dataAdapter!=null)
                    slot_Id = dataAdapter.getItem(pos).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        txt_PromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,PromoCode.class);
                startActivityForResult(i,REQUEST_PROMO);
            }
        });

        txt_CostEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,Cost_Estimate.class);
                i.putExtra("cost_estimate",final_service_Cost);
                startActivity(i);
            }
        });

    }

    private void initUI()
    {
        txt_PromoCode = (TextView)findViewById(R.id.txt_PromoCode);
        txt_CostEstimate = (TextView)findViewById(R.id.txt_CostEstimate);
        txt_RequestAppointment = (TextView)findViewById(R.id.txt_RequestAppointment);
        img_Referral_Doc = (ImageView)findViewById(R.id.imgReferalDoc);
        spn_Time = (Spinner) findViewById(R.id.spn_Time);
        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        mToolbar = (Toolbar)findViewById(R.id.toolbar_action);
        img_Calender = (ImageView)mToolbar.findViewById(R.id.img_calender);
        txt_ChooseDate = (TextView)findViewById(R.id.txt_pick_date);
        txt_ChooseDate.setText(Utils.getcurrentDate());
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

    private void Book_Patient_Appointment(String referral_doc)
    {
        Adapter adb = new Adapter(context);
        adb.Book_Patient_Appointment(UserSession.currentSession().patient_ID,location_ID,Service_ID,referral_doc,slot_Id,isUrgent,strPromoCode,new Adapter.SynceDataListener<JSONObject>()
        {
            @Override
            public void onSynced(JSONObject bin) {

                if(bin!=null)
                {
                    Utils.showToast(context,"Your Appointment has been scheduled");
                    finish();
                }
            }
        });
    }

    private void checkPatient_Already_Booked()
    {
        Adapter adb = new Adapter(context);
        adb.Patient_Already_Booked(slot_Id,new Adapter.SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject bin) {

                if(bin.has("resource"))
                {
                    try {
                        if (bin.getJSONArray("resource").length() > 0) {
                            Utils.dismissProgressDialog();
                            Utils.showToast(context,"You already booked appointment for this slot");
                        }
                        else
                        {
                            new ProfileAsync(image_Path_ReferralDoc,new GetResponse() {
                                @Override
                                public Void getData(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Utils.LogE("Referral image path is  : " + response);
                                        if (jsonObject.has("path"))
                                            Book_Patient_Appointment(jsonObject.getString("path"));
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                    return null;
                                }
                            }).execute();
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        });
    }


    private class referral_file_upload extends BaseAsyncRequest
    {
        referral_file_upload() {
            super();
        }

        @Override
        protected void onPreExecute() {
            //Utils.showProgressDialog(context);
            super.onPreExecute();
        }

        @Override
        protected void doSetup() throws ApiException, JSONException
        {
            // register goes to user/register while login goes to user/session
            use_logging = true;
            callerName = "registerActivity";
            serviceName = "s3";
            endPoint = "patient/"+ Utils.getUniqueName();
            verb = "POST";

            requestBody = new JSONObject();
            fileRequest = new FileRequest();
            fileRequest.setName(Utils.getUniqueName());
            fileRequest.setPath(image_Path_ReferralDoc);
            fileRequest.setContent_type("image/*");
        }

        @Override
        protected void processResponse(String response) throws ApiException, JSONException {
            JSONObject jsonObject = new JSONObject(response);
            Utils.LogE("Referral image path is  : " + response);
            if(jsonObject.has("path"))
                Book_Patient_Appointment(jsonObject.getString("path"));
        }
    }

    private void getAvailable_Time_Slot(String strDate)
    {
        Adapter adb = new Adapter(context);
        adb.getAvailableTime(location_ID,Service_ID,strDate,new Adapter.SynceDataListener<BinForAvailable_Time>() {
            @Override
            public void onSynced(BinForAvailable_Time bin) {
                if (bin != null) {
                    try {
                        if(bin.getResource().size()>0) {
                            dataAdapter = new Available_Time_Adapter(context, bin.getResource());
                            // dataAdapter.setDropDownViewResource(R.layout.custom_spinner);
                            spn_Time.setAdapter(dataAdapter);
                        }
                        else
                            Utils.showToast(context,"Time slot is not available");
                    } catch (Exception e) {

                    }
                }
             }
        });
    }

    private void takePicture() {
        capturedImageUri = Utils.getCaptureUri(context);
        Intent intent = new Intent();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
    }

    private void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(photoPickerIntent, "Select File"), REQUEST_CODE_GALLERY);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_GALLERY:
                try {
                    if (Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)
                            && !Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED_READ_ONLY)) {

                        croppedImageUri = Uri.fromFile(File.createTempFile("abc_123", ".jpg",
                                new File(Environment.getExternalStorageDirectory() + "/Capital_TempFiles/")));

                        Utils.LogE("Uri is : " + croppedImageUri);

                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(data.getData(), "image/*");
                        intent.putExtra("outputX", 700);
                        intent.putExtra("outputY", 700);
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("scale", true);
                        intent.putExtra("noFaceDetection", true);
                        intent.putExtra("output", croppedImageUri);
                        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
                    } else {
                        Utils.showToast(context, "Please insert memory card to take pictures and make sure it is write able");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case REQUEST_CODE_TAKE_PICTURE:
                try {

                    if (Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)
                            && !Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED_READ_ONLY)) {

                        croppedImageUri = Uri.fromFile(File.createTempFile("abc_123",
                                ".jpg", new File(Environment.getExternalStorageDirectory() + "/Capital_TempFiles/")));

                        Utils.LogE("Camera Uri is : " + croppedImageUri);

                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(capturedImageUri, "image/*");
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("scale", true);
                        intent.putExtra("noFaceDetection", true);
                        intent.putExtra("output", croppedImageUri);
                        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);

                    } else {
                        Utils.showToast(context, "Please insert memory card to take pictures and make sure it is writable");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case REQUEST_CODE_CROP_IMAGE:
                    image_Path_ReferralDoc = croppedImageUri.toString().replace("file:///", "").replace("file:/", "").replace("file://", "");
                    Utils.LogE("Result Uri is : " + croppedImageUri);
                    Bitmap bitmap1 = BitmapFactory.decodeFile(image_Path_ReferralDoc);
                    img_Referral_Doc.setImageBitmap(bitmap1);
                    txt_RequestAppointment.setBackgroundColor(getResources().getColor(R.color.wallet_holo_blue_light));
                    txt_RequestAppointment.setEnabled(true);

                break;
            case REQUEST_PROMO:
                double perc = data.getDoubleExtra("PromoPercentage",0);
                strPromoCode = data.getStringExtra("PromoCode");
                double total_discount  = ((double)service_Cost * perc)/100;
                final_service_Cost = service_Cost-total_discount;
         }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private CalendarNumbersView.DateSelectionListener dateSelectionListener = new CalendarNumbersView.DateSelectionListener() {
        @Override
        public void onDateSelected(Calendar selectedDate) {
            if (calendarPopup.isShowing()) {
                calendarPopup.getContentView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        calendarPopup.dismiss();
                    }
                }, 500);//For clarity, we close the popup not immediately.
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Utils.LogE("Selected Date is : "+formatter.format(selectedDate.getTime()));
            txt_ChooseDate.setText(formatter.format(selectedDate.getTime()));
            getAvailable_Time_Slot(formatter.format(selectedDate.getTime()));
        }
    };
}
