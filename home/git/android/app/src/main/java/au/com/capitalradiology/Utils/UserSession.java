// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

public class UserSession
{

    private static final String APP_NAME = "Capital";
    private static Context mContext;
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferences mPrefs;
    private static UserSession mSharedInstance = null;
    public String firstName;
    public String fullName;
    public String host;
    public boolean isAdmin;
    public String lastLoginDate;
    public String lastName;
    public String role;
    public String roleId;
    public String userEmail;
    public String userId;
    public String userToken;
    public int patient_ID;
    public String Profile_url;
    public String App_Name;
    public String App_Arn;
    public String Api_Key;

    public UserSession()
    {
    }

    public static UserSession currentSession()
    {
        mSharedInstance.userId = mPrefs.getString("CURRENT_USER_ID", null);
        mSharedInstance.userEmail = mPrefs.getString("CURRENT_USER_EMAIL", null);
        mSharedInstance.userToken = mPrefs.getString("CURRENT_USER_TOKEN", null);
        mSharedInstance.fullName = mPrefs.getString("CURRENT_USER_FULL_NAME", null);
        mSharedInstance.firstName = mPrefs.getString("CURRENT_USER_FIRST_NAME", null);
        mSharedInstance.lastName = mPrefs.getString("CURRENT_USER_LAST_NAME", null);
        mSharedInstance.isAdmin = mPrefs.getBoolean("CURRENT_USER_IS_ADMIN", false);
        mSharedInstance.lastLoginDate = mPrefs.getString("CURRENT_USER_LAST_LOGIN", null);
        mSharedInstance.host = mPrefs.getString("CURRENT_USER_HOST", null);
        mSharedInstance.role = mPrefs.getString("CURRENT_USER_ROLE", null);
        mSharedInstance.roleId = mPrefs.getString("CURRENT_USER_ROLE_ID", null);
        mSharedInstance.patient_ID = mPrefs.getInt("Patient_ID",0);
        mSharedInstance.Profile_url = mPrefs.getString("Profile_url",null);
        mSharedInstance.App_Name = mPrefs.getString("App_Name",null);
        mSharedInstance.App_Arn = mPrefs.getString("App_Arn",null);
        mSharedInstance.Api_Key = mPrefs.getString("Api_Key",null);
        return mSharedInstance;
    }

    public static void removeCurrentUser()
    {
        mEditor.clear();
        mEditor.commit();
        mSharedInstance = null;
    }

    public static void save_App_Arn(String App_name,String App_Arn)
    {
        mEditor.putString("App_Name",App_name);
        mEditor.putString("App_Arn",App_Arn);

        Utils.LogE("In Save Application Arn >>>>>>>>>>>>>>>>>>"+App_Arn);
        mEditor.apply();
        mEditor.commit();
    }

    public static void save_Api_Key(String api_Key)
    {
        mEditor.putString("Api_Key",api_Key);
        mEditor.apply();
        mEditor.commit();
    }


    public static void save_PatientID(int patientId,String profile_image_url)
    {
        mEditor.putInt("Patient_ID",patientId);
        mEditor.putString("Profile_url",profile_image_url);

        Utils.LogE("In Save profile url >>>>>>>>>>>>>>>>>>"+profile_image_url);
        mEditor.apply();
        mEditor.commit();
    }


    public static void saveCurrentSession(JSONObject jsonobject)
    {
        try
        {
            if (jsonobject.has("id"))
            {
                mEditor.putString("CURRENT_USER_ID", jsonobject.getString("id"));
            }
            if (jsonobject.has("email"))
            {
                mEditor.putString("CURRENT_USER_EMAIL", jsonobject.getString("email"));
            }
            if (jsonobject.has("session_token"))
            {
                mEditor.putString("CURRENT_USER_TOKEN", jsonobject.getString("session_token"));
            }
            if (jsonobject.has("name"))
            {
                mEditor.putString("CURRENT_USER_FULL_NAME", jsonobject.getString("name"));
            }
            if (jsonobject.has("first_name"))
            {
                mEditor.putString("CURRENT_USER_FIRST_NAME", jsonobject.getString("first_name"));
            }
            if (jsonobject.has("last_name"))
            {
                mEditor.putString("CURRENT_USER_LAST_NAME", jsonobject.getString("last_name"));
            }
            if (jsonobject.has("is_sys_admin"))
            {
                mEditor.putBoolean("CURRENT_USER_IS_ADMIN", jsonobject.getBoolean("is_sys_admin"));
            }
            if (jsonobject.has("last_login_date"))
            {
                mEditor.putString("CURRENT_USER_LAST_LOGIN", jsonobject.getString("last_login_date"));
            }
            if (jsonobject.has("host"))
            {
                mEditor.putString("CURRENT_USER_HOST", jsonobject.getString("host"));
            }
            if (jsonobject.has("role"))
            {
                mEditor.putString("CURRENT_USER_ROLE", jsonobject.getString("role"));
            }
            if (jsonobject.has("role_id"))
            {
                mEditor.putString("CURRENT_USER_ROLE_ID", jsonobject.getString("role_id"));
            }
            mEditor.apply();
         }
        // Misplaced declaration of an exception variable
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static UserSession sharedInstance(Context context)
    {
        mContext = context;
        mPrefs = mContext.getSharedPreferences("Capital", 0);
        mEditor = mPrefs.edit();
        if (mSharedInstance == null)
        {
            mSharedInstance = new UserSession();
        }
        return mSharedInstance;
    }

}
