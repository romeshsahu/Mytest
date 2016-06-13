package au.com.capitalradiology.ui.profile;

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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONObject;

import java.io.File;

import au.com.capitalradiology.BaseActivity;
import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Adapter;
import au.com.capitalradiology.Utils.GetResponse;
import au.com.capitalradiology.Utils.ProfileAsync;
import au.com.capitalradiology.Utils.UserSession;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.Utils.Validate;
import au.com.capitalradiology.model.BinForProfile;

/**
 * Created by Mehul on 2/15/2016.
 */
public class Edit_Profile extends BaseActivity
{
    private Button btn_Save = null;
    private EditText edt_FName = null;
    private EditText edt_LName = null;
    private EditText edt_EmailId = null;
    private EditText edt_Phone = null;
    private EditText edt_Security_Question = null;
    private EditText edt_Security_Answer = null;
    private ImageView img_Profile;
    private LinearLayout lnr_add_photo;
    private Toolbar mToolbar = null;
    private Context context = Edit_Profile.this;
    private MyApplication myApplication;
    private String strSecurityQuestion = "";
    private String strSecurityAnswer = "";
    private ImageLoader loader = ImageLoader.getInstance();

    /***************************************************/
    private String[] select_photo_option = new String[]{"Take picture", "Open gallery",};
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_Image_1 = 45;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private Uri croppedImageUri;
    private Uri capturedImageUri;
    private String image_Path_Profile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        registerControl();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.edit_profile));

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.edit_profile));

        getUserDetail();

        lnr_add_photo.setOnClickListener(new View.OnClickListener() {
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

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Validate.hasText(edt_Security_Question))
                    strSecurityQuestion = edt_Security_Question.getText().toString().trim();
                if(Validate.hasText(edt_Security_Answer))
                    strSecurityAnswer = edt_Security_Answer.getText().toString().trim();

                if(!Validate.hasText(edt_FName))
                    Utils.showToast(context, "Please enter First name");
                else if(!Validate.hasText(edt_LName))
                    Utils.showToast(context, "Please enter Last name");
                else
                    update_User_Profile(edt_FName.getText().toString().trim(), edt_LName.getText().toString().trim(), edt_EmailId.getText().toString().trim(), edt_Phone.getText().toString().trim());
            }
        });
    }

    private void registerControl()
    {
         btn_Save=(Button)findViewById(R.id.btn_save);
         edt_FName=(EditText)findViewById(R.id.edt_FName);
         edt_LName=(EditText)findViewById(R.id.edt_LName);
         edt_EmailId=(EditText)findViewById(R.id.edt_EmailAddress);
         edt_Phone=(EditText)findViewById(R.id.edt_Phone);
         lnr_add_photo = (LinearLayout)findViewById(R.id.lnr_add_photo);
         edt_Security_Question = (EditText)findViewById(R.id.edt_SecurityQuestion);
         edt_Security_Answer = (EditText)findViewById(R.id.edt_SecurityAnswer);
         img_Profile = (ImageView)findViewById(R.id.img_Profile);
         img_Profile.getLayoutParams().height = Utils.getHeight(context,R.drawable.profile_placeholder);
         img_Profile.getLayoutParams().width = Utils.getWidth(context,R.drawable.profile_placeholder);
         loader.displayImage(Utils.makeUrl(context,true,UserSession.currentSession().Profile_url),img_Profile,options());

         mToolbar = (Toolbar)findViewById(R.id.toolbar_action);
         Utils.makeRoundedButton(btn_Save,getResources().getColor(R.color.color_app_primary));
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
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(photoPickerIntent, "Select File"), REQUEST_CODE_GALLERY);
    }

    private void getUserDetail()
    {
        Adapter adb = new Adapter(context);
        adb.get_User_Profile(new Adapter.SynceDataListener<BinForProfile>() {
            @Override
            public void onSynced(BinForProfile bin) {
                if(bin!=null)
                {
                    edt_FName.setText(bin.getFirstName());
                    edt_LName.setText(bin.getLastName());
                    edt_EmailId.setText(bin.getEmail());
                    edt_Phone.setText(bin.getPhone());
                    edt_Security_Question.setText(bin.getSecurityQuestion());
                }
            }
        });
    }


    private void update_User_Profile(final String fname, final String lname, final String email, final String phone)
    {
        Adapter adb = new Adapter(context);
        adb.update_Profile(fname, lname, phone, email, strSecurityQuestion, strSecurityAnswer, new Adapter.SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject bin) {

                if (bin.has("success")) {
                    try {
                        if (bin.getString("success").equalsIgnoreCase("true")) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("enail", email);
                            jsonObject.put("first_name", fname);
                            jsonObject.put("last_name", lname);
                            jsonObject.put("enail", phone);

                            UserSession.saveCurrentSession(jsonObject);
                            if(image_Path_Profile.length()>0)
                                new ProfileAsync(image_Path_Profile,new GetResponse() {
                                    @Override
                                    public Void getData(String objects) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(objects);
                                            Utils.LogE("Profile image path is  : " + objects);
                                            if (jsonObject.has("path")) {
                                                UserSession.save_PatientID(UserSession.currentSession().patient_ID, jsonObject.getString("path"));
                                                update_Patient_Profile(edt_FName.getText().toString(), edt_LName.getText().toString(), edt_EmailId.getText().toString(), edt_Phone.getText().toString());
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }
                                }).execute();
                            else {
                                update_Patient_Profile(fname, lname, email, phone);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void update_Patient_Profile(final String fname, final String lname, final String email, final String phone)
    {
        Adapter adb = new Adapter(context);
        adb.update_Patient_Profile(fname, lname, email, phone, UserSession.currentSession().Profile_url, new Adapter.SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject bin) {

                if (bin.has("success")) {
                    try {
                        if (bin != null) {
                            Utils.alertDialogShow(context, getResources().getString(R.string.app_name), "Profile update successfully", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
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

                       // Utils.LogE("Uri is : " + croppedImageUri);

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

                       // Utils.LogE("Camera Uri is : " + croppedImageUri);

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

                    image_Path_Profile = croppedImageUri.toString().replace("file:///", "").replace("file:/", "").replace("file://", "");
                    Utils.LogE("Result Uri is : " + croppedImageUri);
                    Bitmap bitmap1 = BitmapFactory.decodeFile(image_Path_Profile);
                    img_Profile.getLayoutParams().height = Utils.getHeight(context, R.drawable.profile_placeholder);
                    img_Profile.getLayoutParams().width = Utils.getWidth(context, R.drawable.profile_placeholder);
                    img_Profile.setImageBitmap(getRoundedCornerBitmap(bitmap1));


                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
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

    private DisplayImageOptions options()
    {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.profile_placeholder)
                .showImageOnFail(R.drawable.profile_placeholder)
                .showStubImage(R.drawable.profile_placeholder)
                        //rounded corner bitmap
                .displayer(new RoundedBitmapDisplayer(360))
                .delayBeforeLoading(1000)
                .resetViewBeforeLoading(false)  // default
                .cacheInMemory(true) // default
                .cacheOnDisc(true) // default
                .build();
        return options;
    }


}
