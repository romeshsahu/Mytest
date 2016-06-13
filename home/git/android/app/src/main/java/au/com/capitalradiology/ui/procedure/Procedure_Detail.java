// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.procedure;

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

public class Procedure_Detail extends BaseActivity {
    private TextView txt_Description;
    private ImageView img_Procedure;
    private TextView txt_PainName;
    private Toolbar mToolbar;
    private Context context = Procedure_Detail.this;
    private MyApplication myApplication;
    private ImageLoader loader = ImageLoader.getInstance();


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.pain_mgmt_detail);

        initUI();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myApplication = (MyApplication) getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Procedure_details));

        if (getIntent().getExtras() != null) {
            txt_PainName.setText(getIntent().getStringExtra("Name"));
            txt_Description.setText(getIntent().getStringExtra("Description"));
            loader.displayImage(Utils.makeUrl(context,false,getIntent().getStringExtra("Image_Url")),img_Procedure);
            getSupportActionBar().setTitle(getIntent().getStringExtra("Name"));
        }


    }

    private void initUI() {
        txt_Description = (TextView) findViewById(R.id.txt_Description);
        txt_PainName = (TextView) findViewById(R.id.txt_Name);
        img_Procedure = (ImageView)findViewById(R.id.img_Pain);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_action);
    }


}
