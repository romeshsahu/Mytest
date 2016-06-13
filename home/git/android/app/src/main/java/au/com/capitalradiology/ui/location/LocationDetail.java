// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.location;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import au.com.capitalradiology.BaseActivity;
import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Utils;

public class LocationDetail extends BaseActivity
{
    private TextView txt_LocationName;
    private TextView txt_Address;
    private ImageView img_Location;
    private TextView txt_OpeningTime;
    private TextView txt_Services;
    private TextView txt_Phone;
    private TextView txt_Fax;
    private Context context = LocationDetail.this;
    private Toolbar mToolbar;
    private ImageLoader loader = ImageLoader.getInstance();
    private MyApplication myApplication;

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.location_details);


        initUI();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication,getString(R.string.location_detail));


        if(getIntent().getExtras()!=null)
        {
            txt_LocationName.setText(getIntent().getStringExtra("LocationName"));
            txt_Address.setText(getIntent().getStringExtra("Address"));
            txt_OpeningTime.setText(getIntent().getStringExtra("Time"));
            txt_Fax.setText(getIntent().getStringExtra("Fax"));
            txt_Phone.setText(getIntent().getStringExtra("Phone"));
            loader.displayImage(Utils.makeLocationUrl(context,getIntent().getStringExtra("Image_Url")), img_Location,Default());
            setServices(getIntent().getStringExtra("Services"));
            getSupportActionBar().setTitle(getIntent().getStringExtra("LocationName"));

        }

        txt_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + txt_Phone.getText().toString()));
                startActivity(intent);
            }
        });
    }

    private void initUI()
    {
        txt_LocationName = (TextView)findViewById(R.id.txt_LocationName);
        txt_Address = (TextView)findViewById(R.id.txt_Address);
        txt_OpeningTime = (TextView)findViewById(R.id.txt_Time);
        txt_Fax = (TextView)findViewById(R.id.txt_FaxNumber);
        txt_Phone = (TextView)findViewById(R.id.txt_PhoneNumber);
        txt_Services = (TextView)findViewById(R.id.txt_Services);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_action);
        img_Location = (ImageView)findViewById(R.id.img_location);
    }

    private void setServices(String strService)
    {
        String strSer[] = strService.split(",");
        StringBuffer strServices = new StringBuffer();
        for(int i=0;i<strSer.length;i++)
        {
            if(i!=strSer.length-1)
                strServices.append("&#8226;  "+strSer[i]+"<br>");
            else
                strServices.append("&#8226;  "+strSer[i]);
        }
        txt_Services.setText(Html.fromHtml(strServices.toString()));
    }



    private DisplayImageOptions Default()
    {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.location_placeholder)
                .showImageOnFail(R.drawable.location_placeholder)
                .showStubImage(R.drawable.location_placeholder)
                        //rounded corner bitmap
                .delayBeforeLoading(1000)
                .resetViewBeforeLoading(false)  // default
                .cacheInMemory(true) // default
                .cacheOnDisc(true) // default
                .build();

        return options;
    }

}
