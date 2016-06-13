// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.pain_mgmt;

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

public class Pain_Mgmt_Detail extends BaseActivity {
    private TextView txt_Description;
    private ImageView img_Pain;
    private TextView txt_PainName;
    private Toolbar mToolbar;
    private Context context = Pain_Mgmt_Detail.this;
    private ImageLoader loader = ImageLoader.getInstance();
    private MyApplication myApplication;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.pain_mgmt_detail);

        initUI();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Pain_Mgmt_details));

        if (getIntent().getExtras() != null) {
            txt_Description.setText(getIntent().getStringExtra("Description"));
            loader.displayImage(getIntent().getStringExtra("Image_Url"),img_Pain);
            getSupportActionBar().setTitle(getIntent().getStringExtra("Name"));
        }

    }

    private void initUI() {
        txt_Description = (TextView) findViewById(R.id.txt_Description);
        txt_PainName = (TextView) findViewById(R.id.txt_Name);
        img_Pain = (ImageView)findViewById(R.id.img_Pain);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_action);
    }

}
