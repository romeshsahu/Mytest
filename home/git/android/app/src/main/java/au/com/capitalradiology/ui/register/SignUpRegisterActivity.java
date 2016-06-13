// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.register;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

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
import au.com.capitalradiology.model.BinForApplicationArn;
import au.com.capitalradiology.model.BinForEnd_Point;
import au.com.capitalradiology.model.BinForSNSAttributes;
import au.com.capitalradiology.ui.SignInActivity;

public class SignUpRegisterActivity extends AppCompatActivity
{
    private String DOB;
    private Button btnDone;
    private CheckBox checkTnC;
    private EditText editEmail;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editPassword;
    private EditText editPhone;
    private ImageView imgMedicareCard;
    private ImageView imgProfile;
    private LinearLayout lnr_add_photo;
    private int selectedGender;
    private TextView txtTnC;
    private ImageView imgViewPassword;
    private Context context = SignUpRegisterActivity.this;
    private int image_check = 0;
    private String user_id = "";
    private String strGender = "";

    private String[] select_photo_option = new String[]{"Take picture", "Open gallery",};
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_Image_1 = 45;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private Uri croppedImageUri;
    private Uri capturedImageUri;
    private String image_Path_Medicare = "";
    private String image_Path_Profile = "";

    /***********************GCM VARIABLE DECLARATION**********************************************/
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private String regId = "";
    private String strEndPoint = "";
    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    private String SENDER_ID = "";
    private GoogleCloudMessaging gcm;
    private MyApplication myApplication;
    private boolean isShowPwd = false;


    public SignUpRegisterActivity()
    {

    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sign_up_register);

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Signup_Register));

        getGenderAndDOB();
        initUI();
        initListeners();

        Utils.createDirectory();

        getApplicationArn();

        SENDER_ID = getResources().getString(R.string.SENDER_ID);
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regId = Utils.getRegistrationId(context);

            if (regId.isEmpty()) {
                registerInBackground();
            }
        } else {
            Utils.LogE("No valid Google Play Services APK found.");
        }

        imgMedicareCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showImageDialog(context, "Choose Action", select_photo_option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        image_check = 2;
                        if (arg1 == 0)
                            takePicture();
                        else
                            openGallery();
                    }
                });
            }
        });

        lnr_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showImageDialog(context, "Choose Action", select_photo_option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        image_check = 1;
                        if (arg1 == 0)
                            takePicture();
                        else
                            openGallery();
                    }
                });
            }
        });
    }

    private void getGenderAndDOB()
    {
        if (getIntent().hasExtra("selectedGender"))
        {
            selectedGender = getIntent().getIntExtra("selectedGender", -1);
            if(selectedGender==1)
                strGender = "Male";
            else
                strGender = "Female";

        }
        if (getIntent().hasExtra("dob_year") && getIntent().hasExtra("dob_month") && getIntent().hasExtra("dob_dayOfMonth"))
        {
            DOB = (new StringBuilder()).append(Integer.toString(getIntent().getIntExtra("dob_year", 1980))).append("-").append(Integer.toString(getIntent().getIntExtra("dob_month", 0) + 1)).append("-").append(Integer.toString(getIntent().getIntExtra("dob_dayOfMonth", 1))).toString();
        }
    }

    private void initListeners()
    {
        btnDone.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                if(validateFields()) {
                    if(Utils.getEndPoint_Name(context).equalsIgnoreCase(""))
                        create_Endpoint();
                    else
                        getEndpointAttributes();
                }

            }

        });

        editFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        checkTnC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                checkFieldsForEmptyValues();
            }
        });

        imgViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowPwd) {
                    editPassword.setInputType(129);
                    isShowPwd = false;
                }
                else
                {
                    editPassword.setInputType(1);
                    isShowPwd = true;
                }
            }
        });
    }

    private void initUI()
    {
        editFirstName = (EditText)findViewById(R.id.editFirstName);
        editLastName = (EditText)findViewById(R.id.editLastName);
        editEmail = (EditText)findViewById(R.id.editEmail);
        editPassword = (EditText)findViewById(R.id.editPassword);
        editPhone = (EditText)findViewById(R.id.editPhone);
        imgMedicareCard = (ImageView)findViewById(R.id.imgMedicareCard);
        imgProfile = (ImageView)findViewById(R.id.img_Profile);
        checkTnC = (CheckBox)findViewById(R.id.checkTnC);
        txtTnC = (TextView)findViewById(R.id.txtTnC);
        lnr_add_photo = (LinearLayout)findViewById(R.id.lnr_add_photo);
        imgViewPassword = (ImageView)findViewById(R.id.imgViewPassword);
        txtTnC.setText(Html.fromHtml(getString(R.string.title_activity_sign_in)));
        txtTnC.setClickable(true);
        txtTnC.setMovementMethod(LinkMovementMethod.getInstance());
        btnDone = (Button)findViewById(R.id.btnDone);
        Utils.customTextView(context,txtTnC);
        Utils.makeRoundedButton(btnDone,getResources().getColor(R.color.button_disabled_color));
    }

    private void signUp_User(final String phone,final String email, final String password,final String fName,final String lName,final String gender,final String DOB,final String medicare_url, final String profile_url)
    {
        Adapter adb = new Adapter(context);
        adb.register_User(phone, email, password, fName, lName, new Adapter.SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject bin) {

                //Utils.LogE("Register User Response :" + bin);
                if (bin.has("success")) {
                    login(email,password,phone,fName,lName,strGender,medicare_url,profile_url);
                } else if (bin.has("error")) {
                    try {
                        Utils.alertDialogShow(context, getResources().getString(R.string.app_name), bin.getString("message"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                    } catch (Exception e) {
                        Utils.LogE(e.toString());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void login(final String email,final  String password,final String phone,final String fName,final String lName,final String gender,final String medicare_url,final String profile_url)
    {
        Adapter adb = new Adapter(context);
        adb.login(email,password,false,new Adapter.SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if(response.has("session_token")) {
                    try {
                        user_id = response.getString("id");
                        signUp_Patient(phone, email, fName, lName, strGender, DOB, medicare_url,profile_url);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void signUp_Patient(String phone,String email,String fName,String lName,String gender,String DOB,String medicare_url,String profile_url)
    {
        Adapter adb = new Adapter(context);
        adb.register_Patient(user_id,phone, email, fName, lName, gender, DOB, medicare_url, profile_url,regId,strEndPoint,new Adapter.SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject bin) {
                if (bin!=null)
                {
                    Intent i = new Intent(context, SignInActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        });
    }

    private void create_Endpoint()
    {
        Adapter adb = new Adapter(context);
        adb.Create_EndPoint(regId, new Adapter.SynceDataListener<BinForEnd_Point>() {
            @Override
            public void onSynced(BinForEnd_Point bin) {
                if(bin!=null ) {
                    strEndPoint = bin.getEndpointArn();
                    Utils.saveEndPoint(context,bin.getEndpoint(),bin.getEndpointArn());
                    new medicare_file_upload().execute();
                }
            }
        });
    }

    private void getEndpointAttributes()
    {
        Adapter adb = new Adapter(context);
        adb.getEndpointAttributes(new Adapter.SynceDataListener<BinForSNSAttributes>() {
            @Override
            public void onSynced(BinForSNSAttributes bin) {
                if (bin != null) {
                    if (bin.getAttributes().getEnabled().equalsIgnoreCase("false")|| !bin.getAttributes().getToken().equalsIgnoreCase(Utils.getRegistrationId(context)))
                        setSns_Attributes();
                    else
                        new medicare_file_upload().execute();
                }
                else
                    create_Endpoint();
            }
        });
    }

    private void setSns_Attributes()
    {
        Adapter adb = new Adapter(context);
        adb.setSNS_Attributes(new Adapter.SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject bin) {
                if(bin!=null ) {
                     if(bin.has("success"))
                        new medicare_file_upload().execute();

                }
            }
        });
    }

    private boolean validateFields()
    {
        if (editFirstName.getText().toString().trim().isEmpty())
        {
            Utils.alertDialogShow(context, getString(R.string.app_name), getString(R.string.error_firstame), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            return false;
        }
        else if (editLastName.getText().toString().trim().isEmpty())
        {

            Utils.alertDialogShow(context, getString(R.string.app_name), getString(R.string.error_lastname), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            return false;
        }
        else if (editEmail.getText().toString().trim().isEmpty())
        {
            Utils.alertDialogShow(context, getString(R.string.app_name), getString(R.string.error_empty_email), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            return false;
        }
        else if(editPhone.getText().toString().trim().isEmpty())
        {
            //editPhone.setError(getString(R.string.error_empty_phone));
            Utils.alertDialogShow(context, getString(R.string.app_name), getString(R.string.error_empty_phone), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString().trim()).matches())
        {
            //editEmail.setError(getString(R.string.error_email));
            Utils.alertDialogShow(context, getString(R.string.app_name), getString(R.string.error_email), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            return false;
        }
        else if (editPassword.getText().toString().trim().isEmpty())
        {
           // editPassword.setError(getString(R.string.error_password));

            Utils.alertDialogShow(context, getString(R.string.app_name), getString(R.string.error_password), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            return false;
        }
        else if(image_Path_Medicare.equalsIgnoreCase(""))
        {
            Utils.alertDialogShow(context, getString(R.string.app_name), getString(R.string.error_medicare_card), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            return false;
        }
        else if (!checkTnC.isChecked())
        {
            Utils.alertDialogShow(context, getString(R.string.app_name), getString(R.string.error_terms), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            return false;
        } else
        {
            return true;
        }
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
                if(image_check==1) {
                    image_Path_Profile = croppedImageUri.toString().replace("file:///", "").replace("file:/", "").replace("file://", "");
                    Utils.LogE("Result Uri is : " + croppedImageUri);
                    Bitmap bitmap1 = BitmapFactory.decodeFile(image_Path_Profile);
                    imgProfile.getLayoutParams().height = Utils.getHeight(context, R.drawable.profile_min_placeholder);
                    imgProfile.getLayoutParams().width = Utils.getWidth(context, R.drawable.profile_min_placeholder);
                    imgProfile.setImageBitmap(getRoundedCornerBitmap(bitmap1));
                }
                else if(image_check==2)
                {
                    image_Path_Medicare = croppedImageUri.toString().replace("file:///", "").replace("file:/", "").replace("file://", "");
                    Utils.LogE("Medicare Uri is : " + image_Path_Medicare);
                    Bitmap bitmap1 = BitmapFactory.decodeFile(image_Path_Medicare);
                    imgMedicareCard.setImageBitmap(bitmap1);
                    checkFieldsForEmptyValues();
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private class medicare_file_upload extends BaseAsyncRequest
    {
        medicare_file_upload() {
            super();
        }

        @Override
        protected void onPreExecute() {
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

            // post email and password to get back session token
            // need session token to make every call other than login and challenge
            requestBody = new JSONObject();
            fileRequest = new FileRequest();
            fileRequest.setName(Utils.getUniqueName());
            fileRequest.setPath(image_Path_Medicare);
            fileRequest.setContent_type("image/*");
        }

        @Override
        protected void processResponse(String response) throws ApiException, JSONException {
            // store the session_token to be used later on
            JSONObject jsonObject = new JSONObject(response);
            Utils.LogE("Medicare file uploaded is  : " + response);

            if(jsonObject.has("path")) {
               if(image_Path_Profile.toString().trim().equalsIgnoreCase(""))
                   signUp_User(editPhone.getText().toString().trim(), editEmail.getText().toString().trim(), editPassword.getText().toString().trim(), editFirstName.getText().toString().trim(), editLastName.getText().toString().trim(), strGender, DOB, jsonObject.getString("path"),"");
                else {
                   image_Path_Medicare = jsonObject.getString("path");
                   new ProfileAsync(image_Path_Profile,new GetResponse() {
                       @Override
                       public Void getData(String response) {
                           try {
                               JSONObject jsonObject = new JSONObject(response);
                               Utils.LogE("Profile image path is  : " + response);
                               if (jsonObject.has("path"))
                                   signUp_User(editPhone.getText().toString().trim(), editEmail.getText().toString().trim(), editPassword.getText().toString().trim(), editFirstName.getText().toString().trim(), editLastName.getText().toString().trim(), strGender, DOB, image_Path_Medicare, jsonObject.getString("path"));
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
        }
    }

    private void takePicture()
    {
        capturedImageUri = Utils.getCaptureUri(context);
        Intent intent = new Intent();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
    }

    private void openGallery() {
        //Utils.hide_AlwaysSoftKeyboard(Fillup_Survey.this);
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(photoPickerIntent, "Select File"), REQUEST_CODE_GALLERY);

    }



    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 360;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    private void registerInBackground() {
        new AsyncTask()
        {
            @Override
            protected Object doInBackground(Object[] params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regId;

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    //sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the registration ID - no need to register again.
                    Utils.storeRegistrationId(context, regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

        }.execute(null, null, null);

    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Utils.LogE("This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private  void checkFieldsForEmptyValues()
    {
        String s1 = editFirstName.getText().toString();
        String s2 = editLastName.getText().toString();
        String s3 = editEmail.getText().toString();
        String s4 = editPassword.getText().toString();
        String s5 = editPhone.getText().toString();

        if(s1.equalsIgnoreCase("") || s2.equals("") || s3.equals("") || s4.equals("") || s5.equals("") || image_Path_Medicare.equalsIgnoreCase("") || !checkTnC.isChecked()) {
            btnDone.setEnabled(false);
            Utils.makeRoundedButton(btnDone,getResources().getColor(R.color.button_disabled_color));
        }
        else {
            btnDone.setEnabled(true);
            Utils.makeRoundedButton(btnDone,getResources().getColor(R.color.button_background_white));

        }
    }


    private void getApplicationArn()
    {
        Adapter adb = new Adapter(context);
        adb.getApp_Name(new Adapter.SynceDataListener<BinForApplicationArn>() {
            @Override
            public void onSynced(BinForApplicationArn bin) {

                if(bin!=null)
                {
                    UserSession.save_App_Arn(bin.getApplication(),bin.getPlatformApplicationArn());
                }
            }
        });
    }

}
