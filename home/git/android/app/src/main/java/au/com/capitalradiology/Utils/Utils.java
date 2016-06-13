// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.model.BinForContactEmail;
import au.com.capitalradiology.request.VolleyErrorHelper;
import au.com.capitalradiology.ui.SignInActivity;
import au.com.capitalradiology.ui.WebViewActivity;
import au.com.capitalradiology.volley.Response;
import au.com.capitalradiology.volley.VolleyError;

public class Utils
{

    private static final String LOG_TAG = "CapitalUtils";
    private static Context mContext;
    private static Utils mSharedInstace = null;
    private static boolean isLogEnable = true;
    private static ProgressDialog pDialog;
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_ENDPOINT = "App_endPoint";
    public static final String PROPERTY_ENDPOINT_NAME = "App_endPoint_Name";
    public static final String PROPERTY_ENDPOINT_ARN = "App_endPoint_ARN";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    public Utils()
    {
    }

    public static void showProgressDialog(Context context) {

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        /*pDialog = new ProgressDialog(context);

        pDialog.setCancelable(false);
        pDialog.show();*/
    }

    public static boolean isLoading()
    {
        if(pDialog!=null && pDialog.isShowing())
            return  true;
        else
            return  false;
    }


    public static void dismissProgressDialog() {
        pDialog.dismiss();
        pDialog.cancel();

    }

    public static void clearDialog() {
        pDialog = null;
        //activityIndicator = null;
    }

    public static void LogV(String message) {
        if (isLogEnable)
            Log.v("log", message);
    }

    public static void LogD(String message) {
        if (isLogEnable)
            Log.d("log", message);
    }

    public static void LogI(String message) {
        if (isLogEnable)
            Log.i("log", message);
    }

    public static void LogE(String message) {
        if (isLogEnable)
            Log.e("log", message);
    }

    public static void LogW(String message) {
        if (isLogEnable)
            Log.w("log", message);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static void yes_no_alertDialog(Context context,String title,String message,DialogInterface.OnClickListener clickListener)
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("YES", clickListener);
        alertDialog.setNegativeButton("NO", clickListener);
        alertDialog.show();
        alertDialog.setCancelable(false);


    }
    public static void showImageDialog(Context context, String title, String[] items, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items, clickListener);
        builder.create();
        builder.show();
    }
    public static Utils sharedInstace(Context context)
    {

        mContext = context;
        if (mSharedInstace == null)
        {
            mSharedInstace = new Utils();
        }
        return mSharedInstace;

    }

    public static int getHeight(Context context, int drawable) {
        return BitmapFactory.decodeResource(context.getResources(), drawable)
                .getHeight();
    }

    public static int getWidth(Context context, int drawable) {
        return BitmapFactory.decodeResource(context.getResources(), drawable)
                .getWidth();
    }

    public void showAlertDialog(String s, String s1)
    {
        /*android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext, 0x7f090080);
        builder.setTitle(s);
        builder.setMessage(s1);
        builder.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                dialoginterface.dismiss();
            }

        });
        builder.create().show();*/
    }

    public static void alertDialogShow(Context context, String title, String message, DialogInterface.OnClickListener clickListener)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", clickListener);
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
    }

    public static void createDirectory()
    {
        File dir = new File(Environment.getExternalStorageDirectory(), "/Capital_User");
        File dir1 = new File(Environment.getExternalStorageDirectory(), "/Capital_TempFiles");
        try{
            if(dir.mkdir()) {
                Utils.LogE("Directory created");
            } else {
                //Utils.LogE("Directory is not created");
            }
            if(dir1.mkdir()) {
                Utils.LogE("Directory_Temp created");
            } else {
                //Utils.LogE("Directory_Temp is not created");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static ArrayList<BinForContactEmail> getContactEmail(Context context)
    {
        ArrayList<BinForContactEmail> list_email= new ArrayList<>();
        BinForContactEmail bin = new BinForContactEmail();
        final String[] PROJECTION = new String[]
                {
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Email.DATA
                };
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, null, null, null);
        if (cursor != null) {
            try {
                final int contactIdIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID);
                final int displayNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                long contactId;
                String displayName, address;
                while (cursor.moveToNext())
                {
                    contactId = cursor.getLong(contactIdIndex);
                    displayName = cursor.getString(displayNameIndex);
                    address = cursor.getString(emailIndex);

                    //Utils.LogE("EmailId is : ==========="+address);
                    ///Utils.LogE("Name is : ==========="+displayName);
                    //Utils.LogE("********************************************");
                    bin = new BinForContactEmail();
                    bin.setEmailId(address);
                    bin.setUsername(displayName);
                    list_email.add(bin);
                }
            } finally {
                cursor.close();
            }
        }
        return  list_email;
    }

    public static void sortConnections(List<BinForContactEmail> playerList) {
        Collections.sort(playerList, new Comparator<BinForContactEmail>() {
            public int compare(BinForContactEmail p1, BinForContactEmail p2) {

                return p1.getUsername().compareToIgnoreCase(
                        p2.getUsername());
            }
        });
    }

    public static String getUniqueName()
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        if(UserSession.currentSession().patient_ID!=0)
            return "Android_Profile_"+UserSession.currentSession().patient_ID+"_"+timeStamp+".jpg";
        else
            return "Android_Profile_"+timeStamp+".jpg";

        //iOS_Profile_11_ABCD123.png.
    }

    public static String getcurrentDate_Time()
    {
        String timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(new Date());
        return timeStamp ;
    }

    public static String getcurrentDate()
    {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return timeStamp ;
    }


    /**TODO**********Application End Point *********************************/
    public static String getEndPoint_Name(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_ENDPOINT, Context.MODE_PRIVATE);
        String endPoint = prefs.getString(PROPERTY_ENDPOINT_NAME, "");
        if (endPoint.isEmpty()) {
            Log.i("", "Endpoint not found.");
            return "";
        }

        return endPoint;
    }

    public static String getEndPoint_ARN(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_ENDPOINT, Context.MODE_PRIVATE);
        String endPoint_Arn = prefs.getString(PROPERTY_ENDPOINT_ARN, "");
        if (endPoint_Arn.isEmpty()) {
            Log.i("", "Endpoint not found.");
            return "";
        }

        return endPoint_Arn;
    }

     public static void saveEndPoint(Context context,String strEndpoint_Name,String strEndpoint_Arn) {
         final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_ENDPOINT, Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = prefs.edit();
         editor.putString(PROPERTY_ENDPOINT_NAME, strEndpoint_Name);
         editor.putString(PROPERTY_ENDPOINT_ARN, strEndpoint_Arn);
         editor.commit();
    }

    /**TODO**********GCM Preference *********************************/
    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_REG_ID, Context.MODE_PRIVATE);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i("", "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i("", "App version changed.");
            return "";
        }
        return registrationId;
    }

    public static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_REG_ID, Context.MODE_PRIVATE);
        int appVersion = Utils.getAppVersion(context);
        Log.i("", "Saving regId on app version " + regId);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public static void clearRegisterationID(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_REG_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }


    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }



    /***********************************************************************/

    public static void googleAnalytics(MyApplication application,String activityName)
    {
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName(activityName);
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .build());
       /* application.getDefaultTracker().send(new HitBuilders.EventBuilder("Activity", "open")
                .setLabel("Test iGotForms")
            .build());*/
    }

    public static void makeRoundedButton(Button btn,int bgColor)
    {
        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(bgColor);
        gdDefault.setCornerRadius(6);
        btn.setBackgroundDrawable(gdDefault);
    }

    public static String makeUrl(Context context,boolean isS3,String image_url)
    {
        if(!isS3)
            return  context.getResources().getString(R.string.BASE_URL)+image_url + "?api_key="+ context.getResources().getString(R.string.DF_ANDROID_API_KEY)+"&session_token="+ UserSession.currentSession().userToken+"&download=false";
        else
            return context.getResources().getString(R.string.BASE_URL)+"/s3/"+image_url+"?api_key="+context.getResources().getString(R.string.DF_ANDROID_API_KEY)+"&session_token="+UserSession.currentSession().userToken+"&download=false";
    }

    public static String makeLocationUrl(Context context,String image_url)
    {
        String url = image_url.replace("com.capitalradiologist/", context.getResources().getString(R.string.BASE_URL)+"/s3/");
        String actual = url + "?api_key="+ context.getResources().getString(R.string.DF_ANDROID_API_KEY)+"&session_token="+ UserSession.currentSession().userToken+"&download=false";
        Utils.LogE("My Url is : " + actual);
        return  actual;
    }

    public static Uri getCaptureUri(Context context)
    {
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)
                    && !Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED_READ_ONLY)) {

                return Uri.fromFile(File.createTempFile("abc_123",
                        ".jpg", new File(Environment.getExternalStorageDirectory() + "/Capital_TempFiles/")));

            } else {
                Utils.showToast(context, "Please insert memory card to take pictures and make sure it is writable");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void customTextView(final Context context,TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                "I agree to the ");
        spanTxt.append("Terms of services");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent i = new Intent(context,WebViewActivity.class);
                i.putExtra(context.getResources().getString(R.string.key_url),context.getResources().getString(R.string.privacy_link));
                i.putExtra(context.getResources().getString(R.string.key_title),context.getResources().getString(R.string.terms_of_services_text));
                context.startActivity(i);
            }
        }, spanTxt.length() - "Terms of services".length(), spanTxt.length(), 0);
        spanTxt.append(" and");
        spanTxt.append(" Privacy Policy");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent i = new Intent(context,WebViewActivity.class);
                i.putExtra(context.getResources().getString(R.string.key_url),context.getResources().getString(R.string.privacy_link));
                i.putExtra(context.getResources().getString(R.string.key_title),context.getResources().getString(R.string.privacy_policy_text));
                context.startActivity(i);
            }
        }, spanTxt.length() - " Privacy Policy".length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    public static void createRequest(int method,Context context,String entity,JSONObject jobj, final boolean isLoadingNeed,final Adapter.SynceDataListener... synceDataListener)
    {
        MyApplication Myapp = (MyApplication) context.getApplicationContext();
        VolleyCustomRequest request = new VolleyCustomRequest(method,context.getResources().getString(R.string.BASE_URL)+entity,jobj.toString(),null,new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                if(response!=null)
                {
                    if(synceDataListener!=null && synceDataListener[0]!=null){
                        if(isLoadingNeed)
                            Utils.dismissProgressDialog();
                        synceDataListener[0].onSynced(response);
                    }
                }
            }
        }, createMyReqErrorListener(context));
        Myapp.addToRequestQueue(request);
    }


    public static void createRequest_with_Response(int method,Context context,String entity,JSONObject jobj,final Adapter.SynceDataListener synceDataListener)
    {
        MyApplication Myapp = (MyApplication) context.getApplicationContext();
        VolleyCustomRequest request = new VolleyCustomRequest(method,context.getResources().getString(R.string.BASE_URL)+entity,jobj.toString(),null,new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                if(response!=null)
                {
                     synceDataListener.onSynced(response);
                }
            }
        }, createMyReqErrorListener(context));
        Myapp.addToRequestQueue(request);
    }

    public static Response.ErrorListener createMyReqErrorListener(final Context context)
    {
        return new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                if(Utils.isLoading())
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

}
