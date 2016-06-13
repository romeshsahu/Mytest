// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONObject;

import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Adapter;
import au.com.capitalradiology.Utils.UserSession;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.model.BinForPatient;

public class SignInActivity extends AppCompatActivity {
    //private Button btnCancel;
    // private Button btnSignIn;
    // private CheckBox checkTnC;
    private TextView tvTNC, tvForgotPassword, tvRegisterHere;
    private LinearLayout layoutLogin;
    private EditText editPassword;
    private EditText editUsername, edtLogin;
    //private ImageView imgViewPassword;
    private ProgressDialog progressDialog;
    //private TextView txtSigninTrouble;
    //private TextView txtTnC;
    private Context context = SignInActivity.this;
    private MyApplication myApplication;
    private boolean isShowPwd = false;

    public SignInActivity() {

    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sign_in);

        myApplication = (MyApplication) getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Signin));

        initUI();
        setListeners();
    }

    private void initUI() {
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        //imgViewPassword = (ImageView) findViewById(R.id.imgViewPassword);
        layoutLogin = (LinearLayout) findViewById(R.id.layoutLogin);
//        checkTnC = (CheckBox) findViewById(R.id.checkTnC);
//        txtTnC = (TextView) findViewById(R.id.txtTnC);
//        txtTnC.setText(Html.fromHtml(getString(R.string.title_activity_sign_in)));
//        txtTnC.setClickable(true);
//        txtTnC.setMovementMethod(LinkMovementMethod.getInstance());
//        btnCancel = (Button) findViewById(R.id.btnCancel);
//        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        tvTNC = (TextView) findViewById(R.id.tvTermsAndCondition);
        tvRegisterHere = (TextView) findViewById(R.id.tvRegisterHere);
        //  Utils.customTextView(context, txtTnC);
//        Utils.makeRoundedButton(btnSignIn, getResources().getColor(R.color.button_disabled_color));
//        Utils.makeRoundedButton(btnCancel, getResources().getColor(R.color.button_background_white));
        UserSession.save_Api_Key(getResources().getString(R.string.DF_ANDROID_API_KEY));
    }

    private void setListeners() {
//        imgViewPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isShowPwd) {
//                    editPassword.setInputType(129);
//                    isShowPwd = false;
//                } else {
//                    editPassword.setInputType(1);
//                    isShowPwd = true;
//                }
//            }
//        });

        tvRegisterHere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }

        });

        layoutLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (validateFields())
                    login(editUsername.getText().toString().trim(), editPassword.getText().toString().trim());
            }
        });
        edtLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (validateFields())
                    login(editUsername.getText().toString().trim(), editPassword.getText().toString().trim());
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intnet = new Intent(context, WebViewActivity.class);
                intnet.putExtra(getResources().getString(R.string.key_title), getResources().getString(R.string.sign_in_trouble_text));
                intnet.putExtra(getResources().getString(R.string.key_url), getResources().getString(R.string.sign_in_trouble_url));
                startActivity(intnet);
            }
        });

        tvTNC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View widget) {
                Intent i = new Intent(context, WebViewActivity.class);
                i.putExtra(context.getResources().getString(R.string.key_url), context.getResources().getString(R.string.privacy_link));
                i.putExtra(context.getResources().getString(R.string.key_title), context.getResources().getString(R.string.terms_of_services_text));
                context.startActivity(i);
            }
        });

        editUsername.addTextChangedListener(new TextWatcher() {
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
    }

    private void login(String email, String password) {
        Adapter adb = new Adapter(context);
        adb.login(email, password, true, new Adapter.SynceDataListener<JSONObject>() {
            @Override
            public void onSynced(JSONObject response) {
                if (response.has("session_token")) {
                    UserSession.sharedInstance(context);
                    UserSession.saveCurrentSession(response);
                    Get_Patient_Id(UserSession.currentSession().userId);
                }
            }
        });
    }

    private void Get_Patient_Id(String user_id) {
        Adapter adb = new Adapter(context);
        adb.getPatient_Detail(user_id, new Adapter.SynceDataListener<BinForPatient>() {
            @Override
            public void onSynced(BinForPatient response) {
                if (response != null) {
                    UserSession.save_PatientID(response.getResource().get(0).getId(), response.getResource().get(0).getProfileImageUrl());
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    //finish();
                    startActivity(intent);
                }
            }
        });

    }

    private boolean validateFields() {
        if (editUsername.getText().toString().trim().isEmpty()) {

            Utils.showToast(context, getString(R.string.error_username));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editUsername.getText().toString().trim()).matches()) {

            Utils.showToast(context, getString(R.string.error_email));
            return false;
        } else if (editPassword.getText().toString().trim().isEmpty()) {

            Utils.showToast(context, getString(R.string.error_password));
            return false;
        }
//        else if (!checkTnC.isChecked()) {
//
//            Utils.showToast(context, getString(R.string.error_terms));
//            return false;
//        }
        else {
            return true;
        }
    }

    private void checkFieldsForEmptyValues() {

        String s1 = editUsername.getText().toString();
        String s2 = editPassword.getText().toString();

        if (s1.equalsIgnoreCase("") || s2.equals("")) {
            layoutLogin.setEnabled(false);
            //Utils.makeRoundedButton(btnSignIn, getResources().getColor(R.color.button_disabled_color));
        } else {
            layoutLogin.setEnabled(true);
            // Utils.makeRoundedButton(btnSignIn, getResources().getColor(R.color.button_background_white));
        }
    }
}
