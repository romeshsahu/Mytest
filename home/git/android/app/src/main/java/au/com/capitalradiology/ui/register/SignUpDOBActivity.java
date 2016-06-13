// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Utils;

public class SignUpDOBActivity extends AppCompatActivity
{
   private Button btnNext;
   private DatePicker pickerDOB;
   private int selectedGender;
   private  MyApplication myApplication;


    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sign_up_dob);

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Signup_DOB));

        getGender();
        initUI();
        initListeners();
    }

    private void getGender()
    {
        if (getIntent().hasExtra("selectedGender"))
        {
            selectedGender = getIntent().getIntExtra("selectedGender", -1);
        }
    }

    private void initListeners()
    {
        btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
               Intent intent = new Intent(SignUpDOBActivity.this, SignUpRegisterActivity.class);
                if (selectedGender != -1)
                {
                    intent.putExtra("selectedGender", selectedGender);
                }
                intent.putExtra("dob_year", pickerDOB.getYear());
                intent.putExtra("dob_month", pickerDOB.getMonth());
                intent.putExtra("dob_dayOfMonth", pickerDOB.getDayOfMonth());
                startActivity(intent);
            }
        });
    }

    private void initUI()
    {
        pickerDOB = (DatePicker)findViewById(R.id.pickerDOB);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        pickerDOB.updateDate(year-18, month, day);
        btnNext = (Button)findViewById(R.id.btnNext);
        Utils.makeRoundedButton(btnNext,getResources().getColor(R.color.button_background_white));
    }


}
