// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.ui.SignInActivity;

public class SignUpAlreadyPatient extends AppCompatActivity
{
    private Button btnNext;
    private ImageView imgNo;
    private ImageView imgYes;
    private TextView txt_No;
    private TextView txt_Yes;
    private int isPatient;
    private MyApplication myApplication;

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_signup_already_patient);

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Signup_Gender));

        initUI();
        initListeners();
        SetAnimation();
    }

    private void initListeners()
    {
        imgNo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                imgYes.setImageResource(R.drawable.img_yes_deactive);
                imgNo.setImageResource(R.drawable.img_no_active);
                txt_No.setTextColor(getResources().getColor(R.color.white));
                txt_Yes.setTextColor(getResources().getColor(R.color.No_Yes));
                isPatient = 1;
                btnNext.setVisibility(View.VISIBLE);
            }

        });
        imgYes.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view)
            {
                imgYes.setImageResource(R.drawable.img_yes_active);
                imgNo.setImageResource(R.drawable.img_no_deactive);
                txt_No.setTextColor(getResources().getColor(R.color.No_Yes));
                txt_Yes.setTextColor(getResources().getColor(R.color.white));
                isPatient = 2;
                btnNext.setVisibility(View.VISIBLE);
            }

        });

        btnNext.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view)
            {
                if(isPatient==1) {
                    Intent intent = new Intent(SignUpAlreadyPatient.this, SignUpGenderActivity.class);
                    finish();
                    startActivity(intent);
                }
                else if(isPatient == 2)
                {
                    Intent intent = new Intent(SignUpAlreadyPatient.this, SignInActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private void SetAnimation()
    {
        Animation logoMoveAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_in);
        imgYes.startAnimation(logoMoveAnimation);
        txt_Yes.startAnimation(logoMoveAnimation);
        imgNo.startAnimation(logoMoveAnimation);
        txt_No.startAnimation(logoMoveAnimation);
    }


    private void initUI()
    {
        imgYes = (ImageView)findViewById(R.id.img_correct);
        imgNo = (ImageView)findViewById(R.id.img_wrong);
        txt_No = (TextView)findViewById(R.id.txt_No);
        txt_Yes = (TextView)findViewById(R.id.txt_Yes);
        btnNext = (Button)findViewById(R.id.btnNext);
        Utils.makeRoundedButton(btnNext,getResources().getColor(R.color.button_background_white));
    }
}
