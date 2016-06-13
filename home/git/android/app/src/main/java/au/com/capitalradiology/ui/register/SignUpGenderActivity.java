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

public class SignUpGenderActivity extends AppCompatActivity
{
    private Button btnNext;
    private ImageView imgFemale;
    private ImageView imgMale;
    private TextView txt_Male;
    private TextView txt_FeMale;
    private int selectedGender;
    private MyApplication myApplication;

    public SignUpGenderActivity()
    {
        selectedGender = -1;
    }


    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sign_up_gender);

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Signup_Gender));

        initUI();
        initListeners();
        SetAnimation();
    }

    private void initListeners()
    {
        imgMale.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                imgMale.setImageResource(R.drawable.img_male_selected);
                imgFemale.setImageResource(R.drawable.img_female);
                txt_Male.setTextColor(getResources().getColor(R.color.white));
                txt_FeMale.setTextColor(getResources().getColor(R.color.No_Yes));
                selectedGender = 1;
                btnNext.setVisibility(View.VISIBLE);
            }

        });
        imgFemale.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view)
            {
                imgMale.setImageResource(R.drawable.img_male);
                imgFemale.setImageResource(R.drawable.img_female_selected);
                txt_Male.setTextColor(getResources().getColor(R.color.No_Yes));
                txt_FeMale.setTextColor(getResources().getColor(R.color.white));
                selectedGender = 0;
                btnNext.setVisibility(View.VISIBLE);
            }

        });
        btnNext.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view)
            {
                if (selectedGender >= 0)
                {
                    Intent intent = new Intent(SignUpGenderActivity.this, SignUpDOBActivity.class);
                    intent.putExtra("selectedGender", selectedGender);
                    startActivity(intent);
                }
            }
        });
    }

    private void SetAnimation()
    {
        Animation logoMoveAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_in);
        imgMale.startAnimation(logoMoveAnimation);
        imgFemale.startAnimation(logoMoveAnimation);
        txt_Male.startAnimation(logoMoveAnimation);
        txt_FeMale.startAnimation(logoMoveAnimation);
    }


    private void initUI()
    {
        imgMale = (ImageView)findViewById(R.id.imgUserMale);
        imgFemale = (ImageView)findViewById(R.id.imgUserFemale);
        txt_Male = (TextView)findViewById(R.id.txt_Male);
        txt_FeMale = (TextView)findViewById(R.id.txt_Female);
        btnNext = (Button)findViewById(R.id.btnNext);
        Utils.makeRoundedButton(btnNext,getResources().getColor(R.color.button_background_white));

    }


}
