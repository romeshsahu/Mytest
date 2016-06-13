// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.radiologist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import au.com.capitalradiology.BaseActivity;
import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Utils;

public class RadiologistDetail extends BaseActivity
{
    private TextView txt_Description;
    private ImageView img_Doctor;
    private TextView txt_DoctorName;
    private TextView txt_DoctorDegree;
    private TextView txt_Interest;
    private TextView txt_Speciality;
    private Toolbar mToolbar;
    private Context context = RadiologistDetail.this;
    private ImageLoader loader = ImageLoader.getInstance();
    private MyApplication myApplication;

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.doctor_details);

        initUI();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Radiologist_details));

        if(getIntent().getExtras()!=null)
        {
            txt_DoctorDegree.setText(getIntent().getStringExtra("Education"));
            txt_Speciality.setText(getIntent().getStringExtra("Speciality"));
            txt_DoctorName.setText(getIntent().getStringExtra("Name"));
            txt_Interest.setText(getIntent().getStringExtra("Special_Interest"));
            txt_Description.setText(getIntent().getStringExtra("Description"));
            loader.displayImage(Utils.makeUrl(context,false,getIntent().getStringExtra("Image_Url")),img_Doctor);

            getSupportActionBar().setTitle(getIntent().getStringExtra("Name"));
        }
    }

    private void initUI()
    {
        txt_Description = (TextView)findViewById(R.id.txt_Description);
        txt_Interest = (TextView)findViewById(R.id.txt_Interest);
        txt_DoctorName = (TextView)findViewById(R.id.txt_Name);
        txt_DoctorDegree = (TextView)findViewById(R.id.txt_Degree);
        txt_Speciality = (TextView)findViewById(R.id.txt_Speciality);
        img_Doctor = (ImageView) findViewById(R.id.img_Doctor);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_action);
    }

}
