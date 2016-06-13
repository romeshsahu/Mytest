package au.com.capitalradiology.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.model.BinForApplicationArn;
import au.com.capitalradiology.model.BinForAvailable_Time;
import au.com.capitalradiology.model.BinForChooseLocation;
import au.com.capitalradiology.model.BinForEnd_Point;
import au.com.capitalradiology.model.BinForPain_Mgmt;
import au.com.capitalradiology.model.BinForPain_Mgmt_Category;
import au.com.capitalradiology.model.BinForPatient;
import au.com.capitalradiology.model.BinForProcedure;
import au.com.capitalradiology.model.BinForProfile;
import au.com.capitalradiology.model.BinForPromoCode;
import au.com.capitalradiology.model.BinForRadiologist;
import au.com.capitalradiology.model.BinForRecords;
import au.com.capitalradiology.model.BinForSNSAttributes;
import au.com.capitalradiology.model.BinForServiceList;
import au.com.capitalradiology.model.Location;
import au.com.capitalradiology.request.VolleyErrorHelper;
import au.com.capitalradiology.ui.SignInActivity;
import au.com.capitalradiology.volley.Request;
import au.com.capitalradiology.volley.Response;
import au.com.capitalradiology.volley.VolleyError;


/**
 * Created by Mehul on 2/12/2016.
 */
public class Adapter
{
    private Context context;
    private String DEVICE_ID;

    public Adapter(Context context){
        this.context = context;
        DEVICE_ID = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
    }

    public void login(String emailId,String password,boolean isLoadingNeeded,final SynceDataListener... synceDataListener)
    {
        if(isLoadingNeeded)
            Utils.showProgressDialog(context);

        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
            jobj.put("email", emailId);
            jobj.put("password", password);
            jobj.put("remember_me",true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest(Request.Method.POST, context, context.getResources().getString(R.string.USER_SESSION), jobj, false, synceDataListener);
    }

    public void register_User(String phone,String emailId,String password,String firstname,String lastname,
                         final SynceDataListener... synceDataListener)
    {
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
            jobj.put("email", emailId);
            jobj.put("first_name", firstname);
            jobj.put("last_name", lastname);
            jobj.put("display_name", firstname + " "+ lastname);
            jobj.put("new_password", password);
            jobj.put("phone", phone);
            jobj.put("code", "0");
         }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest(Request.Method.POST, context, context.getResources().getString(R.string.USER_REGISTER), jobj, true, synceDataListener);

    }

    public void register_Patient(String user_id,String phone,String emailId,String firstname,String lastname,String selectedGender,String DOB,
                         String medicare_url,String profile_url,String device_token,String end_point_arn,final SynceDataListener... synceDataListener)
    {
        JSONObject mainObj = new JSONObject();
        try {
            JSONObject jobj = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            jobj.put("email", emailId);
            jobj.put("first_name", firstname);
            jobj.put("last_name", lastname);
            jobj.put("gender", selectedGender);
            jobj.put("dob", DOB);
            jobj.put("created_by", 0);
            jobj.put("created_datetime", Utils.getcurrentDate_Time());
            jobj.put("lastupdated_by", 0);
            jobj.put("lastupdated_datetime", "");
            jobj.put("mobile_number", phone);
            jobj.put("medicare_image_url", medicare_url);
            jobj.put("profile_image_url", profile_url);
            jobj.put("mobile_os_id", context.getResources().getString(R.string.MOBILE_OS_ID));
            jobj.put("user_id", user_id);
            jobj.put("device_token", device_token);
            jobj.put("end_point_arn", end_point_arn);

            jsonArray.put(jobj);
            mainObj.put("resource", jsonArray);

            Utils.LogE(mainObj.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest(Request.Method.POST, context, context.getResources().getString(R.string.MYSQL_TABLE) + context.getResources().getString(R.string.USER_PATIENT), mainObj, false, synceDataListener);

    }


    public void getNearestClinic(int service_id,double lat,double lon,final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject mainObj = new JSONObject();
        try {
            JSONObject jobj = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            jobj.put("name", "service_id");
            jobj.put("param_type", "IN");
            jobj.put("value", service_id);
            jsonArray.put(jobj);

            JSONObject jobj1 = new JSONObject();
            jobj1.put("name", "origin_lat");
            jobj1.put("param_type", "IN");
            jobj1.put("value", lat);
            jsonArray.put(jobj1);

            JSONObject jobj2 = new JSONObject();
            jobj2.put("name", "origin_long");
            jobj2.put("param_type", "IN");
            jobj2.put("value", lon);
            jsonArray.put(jobj2);

            mainObj.put("params", jsonArray);
            mainObj.put("wrapper", "Record");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.POST,context,context.getResources().getString(R.string.MYSQL_PROCEDURE)+context.getResources().getString(R.string.NEAREST_CLINIC),mainObj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if(synceDataListener!=null && synceDataListener[0]!=null){
                    Utils.dismissProgressDialog();
                    JSONParsingAdapter<BinForChooseLocation> jAdapter = new JSONParsingAdapter<>();
                    BinForChooseLocation bin = (BinForChooseLocation) jAdapter.parseJsonObject(response, new BinForChooseLocation());
                    synceDataListener[0].onSynced(bin);
                }
            }
        });

    }

    public void getRecords(int patient_id,final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject mainObj = new JSONObject();
        try {
            JSONObject jobj = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            jobj.put("name", "patient_id");
            jobj.put("param_type", "IN");
            jobj.put("value", patient_id);
            jsonArray.put(jobj);

            mainObj.put("params", jsonArray);
            mainObj.put("wrapper", "Result");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.POST,context,context.getResources().getString(R.string.MYSQL_PROCEDURE)+context.getResources().getString(R.string.RECORDS),mainObj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if(synceDataListener!=null && synceDataListener[0]!=null){
                    Utils.dismissProgressDialog();
                    JSONParsingAdapter<BinForRecords> jAdapter = new JSONParsingAdapter<>();
                    BinForRecords bin = (BinForRecords) jAdapter.parseJsonObject(response, new BinForRecords());
                    synceDataListener[0].onSynced(bin);
                }
            }
        });


    }

    public void getAvailableTime(String location_ID,int service_ID,String strDate,final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject mainObj = new JSONObject();
        try {
            JSONObject jobj = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            jobj.put("name", "filter_date");
            jobj.put("param_type", "IN");
            jobj.put("value", strDate);
            jsonArray.put(jobj);

            JSONObject jobj1 = new JSONObject();
            jobj1.put("name", "location_id");
            jobj1.put("param_type", "IN");
            jobj1.put("value", location_ID);
            jsonArray.put(jobj1);

            JSONObject jobj2 = new JSONObject();
            jobj2.put("name", "service_id");
            jobj2.put("param_type", "IN");
            jobj2.put("value", service_ID);
            jsonArray.put(jobj2);

            mainObj.put("params", jsonArray);
            mainObj.put("wrapper", "resource");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.POST,context,context.getResources().getString(R.string.MYSQL_PROCEDURE)+context.getResources().getString(R.string.TABLE_AVAILABLE_TIME_SLOT),mainObj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if(synceDataListener!=null && synceDataListener[0]!=null){
                    Utils.dismissProgressDialog();
                    JSONParsingAdapter<BinForAvailable_Time> jAdapter = new JSONParsingAdapter<>();
                    BinForAvailable_Time bin = (BinForAvailable_Time) jAdapter.parseJsonObject(response, new BinForAvailable_Time());
                    synceDataListener[0].onSynced(bin);
                }
            }
        });

    }

    public void getPatient_Record_Details(String MRN, String ACC,final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest(Request.Method.GET, context, context.getResources().getString(R.string.TABLE_PATIEN_DETAILS) + "?mrn=" + MRN + "&acc=" + ACC, jobj, true, synceDataListener);


    }

    public void getService(final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
         }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.GET,context,context.getResources().getString(R.string.MYSQL_TABLE)+context.getResources().getString(R.string.SERVICES),jobj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {

                if(synceDataListener!=null && synceDataListener[0]!=null){
                    Utils.dismissProgressDialog();
                    JSONParsingAdapter<BinForServiceList> jAdapter = new JSONParsingAdapter<>();
                    BinForServiceList bin = (BinForServiceList) jAdapter.parseJsonObject(response, new BinForServiceList());
                    synceDataListener[0].onSynced(bin);
                }

            }
        });

    }

    public void Book_Patient_Appointment(int patient_id,String location_ID,int service_ID,String referral_doc,String slotID,
                                         boolean isUrgent,String strPromoCode,final SynceDataListener... synceDataListener)
    {
        JSONObject mainObj = new JSONObject();
        try {
            JSONObject jobj = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            jobj.put("created_by", 0);
            jobj.put("lastupdated_by", 0);
            jobj.put("created_datetime", Utils.getcurrentDate_Time());
            jobj.put("notes", "Some notes");
            jobj.put("patient_id", patient_id);
            jobj.put("provider_id", location_ID);
            jobj.put("lastupdated_datetime", "");
            jobj.put("lastupdated_by", 0);
            jobj.put("lastupdated_datetime", "");
            jobj.put("service_id", service_ID);
            jobj.put("is_urgent",isUrgent);
            jobj.put("promo_code",strPromoCode);
            jobj.put("referal_attachement_url", referral_doc);
            jobj.put("slot_id", slotID);

            jsonArray.put(jobj);
            mainObj.put("resource", jsonArray);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest(Request.Method.POST, context, context.getResources().getString(R.string.MYSQL_TABLE) + context.getResources().getString(R.string.TABLE_PATIENT_APPOINTMENT), mainObj, true, synceDataListener);

    }

    public void Create_EndPoint(String device_token,final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj = new JSONObject();
        JSONObject jobj_attr = new JSONObject();
         try {

            jobj.put("Application", UserSession.currentSession().App_Name);
            jobj.put("PlatformApplicationArn", UserSession.currentSession().App_Arn);
            jobj.put("Token", Utils.getRegistrationId(context));
            jobj.put("CustomUserData",DEVICE_ID);
            jobj_attr.put("Enabled","true");
            jobj.put("Attributes",jobj_attr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.POST,context,context.getResources().getString(R.string.CREATE_END_POINT),jobj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if(synceDataListener!=null && synceDataListener[0]!=null){
                    JSONParsingAdapter<BinForEnd_Point> jAdapter = new JSONParsingAdapter<>();
                    BinForEnd_Point bin = (BinForEnd_Point) jAdapter.parseJsonObject(response, new BinForEnd_Point());
                    synceDataListener[0].onSynced(bin);
                }
            }
        });
    }

    public void setSNS_Attributes(final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj = new JSONObject();
        JSONObject jobj_attr = new JSONObject();
        try {

            jobj.put("Application", UserSession.currentSession().App_Name);
            jobj.put("PlatformApplicationArn", UserSession.currentSession().App_Arn);
            jobj.put("Endpoint", Utils.getEndPoint_Name(context));
            jobj.put("Token", Utils.getRegistrationId(context));
            jobj.put("CustomUserData",DEVICE_ID);
            jobj_attr.put("Enabled","true");
            jobj.put("Attributes",jobj_attr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.PUT,context,context.getResources().getString(R.string.CREATE_END_POINT),jobj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if(synceDataListener!=null && synceDataListener[0]!=null){
                    synceDataListener[0].onSynced(response);
                }
            }
        });
    }

    public void getEndpointAttributes(final SynceDataListener... synceDataListener)
    {
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.GET,context,context.getResources().getString(R.string.CREATE_END_POINT)+"/"+Utils.getEndPoint_Name(context),jobj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if(synceDataListener!=null && synceDataListener[0]!=null){
                    Utils.dismissProgressDialog();
                    JSONParsingAdapter<BinForSNSAttributes> jAdapter = new JSONParsingAdapter<>();
                    BinForSNSAttributes bin = (BinForSNSAttributes) jAdapter.parseJsonObject(response, new BinForSNSAttributes());
                    synceDataListener[0].onSynced(bin);
                }

            }
        });

    }

    public void getPatient_Detail(String user_ID,final SynceDataListener... synceDataListener)
    {
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.GET,context,context.getResources().getString(R.string.MYSQL_TABLE)+context.getResources().getString(R.string.TABLE_PATIENT)+"?filter=user_id%3D"+user_ID,jobj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if(synceDataListener!=null && synceDataListener[0]!=null){
                    Utils.dismissProgressDialog();
                    JSONParsingAdapter<BinForPatient> jAdapter = new JSONParsingAdapter<>();
                    BinForPatient bin = (BinForPatient) jAdapter.parseJsonObject(response, new BinForPatient());
                    synceDataListener[0].onSynced(bin);
                }

            }
        });

    }


    public void getApp_Name(final SynceDataListener... synceDataListener) {
        JSONObject jobj = null;
        try {
            jobj = new JSONObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.GET, context, context.getResources().getString(R.string.SNS_APP)+ URLEncoder.encode(context.getResources().getString(R.string.APPLICATION_NAME)), jobj, new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if (synceDataListener != null && synceDataListener[0] != null) {

                    JSONParsingAdapter<BinForApplicationArn> jAdapter = new JSONParsingAdapter<>();
                    BinForApplicationArn bin = (BinForApplicationArn) jAdapter.parseJsonObject(response, new BinForApplicationArn());
                    synceDataListener[0].onSynced(bin);
                }

            }
        });
    }

    public void getLocation(final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.GET,context,context.getResources().getString(R.string.MYSQL_TABLE)+context.getResources().getString(R.string.TABLE_LOCATION),jobj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if(synceDataListener!=null && synceDataListener[0]!=null){
                    Utils.dismissProgressDialog();
                    JSONParsingAdapter<Location> jAdapter = new JSONParsingAdapter<>();
                    Location bin = (Location) jAdapter.parseJsonObject(response, new Location());
                    synceDataListener[0].onSynced(bin);
                }

            }
        });

    }

    public void getPain_Mgmt_List(final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.GET,context,context.getResources().getString(R.string.MYSQL_TABLE)+context.getResources().getString(R.string.TABLE_PAIN_CATEGORY),jobj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {

                if(synceDataListener!=null && synceDataListener[0]!=null){
                    Utils.dismissProgressDialog();
                    JSONParsingAdapter<BinForPain_Mgmt_Category> jAdapter = new JSONParsingAdapter<>();
                    BinForPain_Mgmt_Category bin = (BinForPain_Mgmt_Category) jAdapter.parseJsonObject(response, new BinForPain_Mgmt_Category());
                    synceDataListener[0].onSynced(bin);
                }

            }
        });


    }

    public void getPain_Mgmt_Details(int category_Id,final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.GET,context,context.getResources().getString(R.string.MYSQL_TABLE)+context.getResources().getString(R.string.TABLE_PAIN_MGMT)+"?filter=category_id="+category_Id,jobj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {

                if(synceDataListener!=null && synceDataListener[0]!=null){
                    Utils.dismissProgressDialog();
                    JSONParsingAdapter<BinForPain_Mgmt> jAdapter = new JSONParsingAdapter<>();
                    BinForPain_Mgmt bin = (BinForPain_Mgmt) jAdapter.parseJsonObject(response, new BinForPain_Mgmt());
                    synceDataListener[0].onSynced(bin);
                }

            }
        });

    }

    public void getRadiologist_List(final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.GET,context,context.getResources().getString(R.string.MYSQL_TABLE)+context.getResources().getString(R.string.TABLE_RADIOLOGIST),jobj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                Utils.dismissProgressDialog();
                if(synceDataListener!=null && synceDataListener[0]!=null){
                    JSONParsingAdapter<BinForRadiologist> jAdapter = new JSONParsingAdapter<>();
                    BinForRadiologist bin = (BinForRadiologist) jAdapter.parseJsonObject(response, new BinForRadiologist());
                    synceDataListener[0].onSynced(bin);
                }

            }
        });

    }

    public void getProcedure_List(final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.GET,context,context.getResources().getString(R.string.MYSQL_TABLE)+context.getResources().getString(R.string.TABLE_SERVICE),jobj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if(synceDataListener!=null && synceDataListener[0]!=null){
                    Utils.dismissProgressDialog();
                    JSONParsingAdapter<BinForProcedure> jAdapter = new JSONParsingAdapter<>();
                    BinForProcedure bin = (BinForProcedure) jAdapter.parseJsonObject(response, new BinForProcedure());
                    synceDataListener[0].onSynced(bin);
                }

            }
        });

    }

    public void Promo_Applied_OR_Not(String strPromoCode,final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest(Request.Method.GET, context, context.getResources().getString(R.string.MYSQL_TABLE) + context.getResources().getString(R.string.TABLE_PATIENT_APPOINTMENT) + "?filter=(patient_id%3D" + UserSession.currentSession().patient_ID + ")and(promo_code%3D" + strPromoCode + ")", jobj, false, synceDataListener);

    }

    public void Patient_Already_Booked(String slot_id,final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest(Request.Method.GET, context, context.getResources().getString(R.string.MYSQL_TABLE) + context.getResources().getString(R.string.TABLE_PATIENT_APPOINTMENT) + "?filter=(patient_id%3D" + UserSession.currentSession().patient_ID + ")and(slot_id%3D" + slot_id + ")", jobj, false, synceDataListener);

    }

    public void Get_Promo_Code(String strPromoCode,final SynceDataListener... synceDataListener)
    {
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest_with_Response(Request.Method.GET,context,context.getResources().getString(R.string.MYSQL_TABLE)+context.getResources().getString(R.string.TABLE_PROMO_CODE)+"?filter=code%3D",jobj,new SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if(synceDataListener!=null && synceDataListener[0]!=null){
                    Utils.dismissProgressDialog();
                    JSONParsingAdapter<BinForPromoCode> jAdapter = new JSONParsingAdapter<>();
                    BinForPromoCode bin = (BinForPromoCode) jAdapter.parseJsonObject(response, new BinForPromoCode());
                    synceDataListener[0].onSynced(bin);
                }
            }
        });


    }

    public void get_User_Profile(final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        MyApplication Myapp = (MyApplication) context.getApplicationContext();
        VolleyCustomRequest request = new VolleyCustomRequest(Request.Method.GET,context.getResources().getString(R.string.BASE_URL)+context.getResources().getString(R.string.USER_PROFILE),jobj.toString(),null,new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                if(response!=null)
                {
                    if(synceDataListener!=null && synceDataListener[0]!=null){
                        Utils.dismissProgressDialog();
                        JSONParsingAdapter<BinForProfile> jAdapter = new JSONParsingAdapter<>();
                        BinForProfile bin = (BinForProfile) jAdapter.parseJsonObject(response, new BinForProfile());
                        synceDataListener[0].onSynced(bin);
                    }
                }
            }
        }, createMyReqErrorListener(context));

        Myapp.addToRequestQueue(request);
    }

    public void update_Profile(String fname,String lname,String phone,String email,String strQuestion,String strAnswer,final SynceDataListener... synceDataListener)
    {
        Utils.showProgressDialog(context);
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
            jobj.put("first_name", fname);
            jobj.put("last_name",lname);
            jobj.put("email",email);
            jobj.put("display_name",fname+" "+lname);
            jobj.put("phone", phone);
            jobj.put("security_question",strQuestion);

            if(strAnswer.length()>0)
                jobj.put("security_answer",strAnswer);

            jobj.put("default_app_id","0");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest(Request.Method.POST, context, context.getResources().getString(R.string.USER_PROFILE), jobj, false, synceDataListener);
    }

    public void update_Patient_Profile(String fname,String lname,String email,String phone,String profile_url,final SynceDataListener... synceDataListener)
    {
        JSONObject jobj=null;
        try {
            jobj = new JSONObject();
            jobj.put("id", UserSession.currentSession().patient_ID);
            jobj.put("first_name", fname);
            jobj.put("last_name",lname);
            jobj.put("email",email);
            jobj.put("mobile_number",phone);
            jobj.put("profile_image_url",profile_url);
            jobj.put("user_id",UserSession.currentSession().userId);
            jobj.put("created_by", 0);
            jobj.put("created_datetime",Utils.getcurrentDate_Time());
            jobj.put("lastupdated_by",0);
            jobj.put("lastupdated_datetime","");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Utils.createRequest(Request.Method.PATCH, context, context.getResources().getString(R.string.USER_PROFILE), jobj, true, synceDataListener);
    }

    private Response.ErrorListener createMyReqErrorListener(final Context context)
    {
        return new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                Utils.dismissProgressDialog();
                String strError = VolleyErrorHelper.getMessage(context, error);
                if(strError.contains("has expired"))
                {
                    UserSession.sharedInstance(context);
                    UserSession.removeCurrentUser();
                    Intent intent = new Intent(context, SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
                    Utils.alertDialogShow(context, context.getResources().getString(R.string.app_name), strError, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                }
            }
        };
    }

    public interface SynceDataListener<T>{
        void onSynced(T bin);
    }


    /*** TODO***********************GET PATIENT DETAILS ************************************************/
    public String getPatientDetails(String MRN, String ACC)
    {
        String result="";
        try
        {
            java.net.URL url = new java.net.URL(context.getResources().getString(R.string.BASE_URL)+context.getResources().getString(R.string.TABLE_PATIEN_DETAILS)+"?mrn="+MRN+"&acc="+ACC);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.addRequestProperty("X-DreamFactory-Api-Key", context.getResources().getString(R.string.DF_ANDROID_API_KEY));
            conn.addRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
            /*conn.setRequestProperty("Content-Type","text/html");
            conn.setRequestProperty("Content-Encoding", "gzip");
            conn.setRequestProperty("x-dreamfactory-routed","true");
            conn.setRequestProperty("Connection","keep-alive");*/
            /*if (UserSession.currentSession().userToken != null)
            {
                conn.addRequestProperty("X-DreamFactory-Session-Token", UserSession.currentSession().userToken);
            }*/
            for (String header : conn.getRequestProperties().keySet()) {
                if (header != null) {
                    for (String value : conn.getRequestProperties().get(header)) {
                        Utils.LogE(header + ":" + value);
                    }
                }
            }

            conn.connect();

            int status = conn.getResponseCode();

            Utils.LogE("Status code is : "+status);
            InputStream is = new BufferedInputStream(conn.getInputStream());

            if (is != null) {
                StringBuilder sb = new StringBuilder();
                String line;
                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is,"UTF-8"));
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                } finally {
                    is.close();
                }
                result = sb.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }
}
