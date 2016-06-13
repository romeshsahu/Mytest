// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.request_appointment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import au.com.capitalradiology.BaseActivity;
import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Utils;

public class Cost_Estimate extends BaseActivity
{
    private TextView txt_Cost;
    private MyApplication myApplication;
    private Context context = Cost_Estimate.this;
    private Toolbar mToolbar;

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.cost_estimate);

        initUI();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.Cost_Estimate));

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Cost_Estimate));

        if(getIntent().getExtras()!=null)
        {
            txt_Cost.setText("The estimated cost for your procedure is AUD "+getIntent().getDoubleExtra("cost_estimate",0)+" \n\nPlease note this is just an estimate. Final amount can change based on your care at the clinic.");
        }

      }

    private void initUI()
    {
        txt_Cost = (TextView)findViewById(R.id.txt_Cost);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_action);

    }
}
