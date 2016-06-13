// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.my_health_record;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import au.com.capitalradiology.BaseActivity;
import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Adapter;
import au.com.capitalradiology.Utils.UserSession;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.listadapter.Health_Record_Adapter;
import au.com.capitalradiology.model.BinForRecords;


public class My_Health_RecordActivity extends BaseActivity
{
    private List<BinForRecords.Result> record_list = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Health_Record_Adapter adapter;
    private Context context = My_Health_RecordActivity.this;
    private Toolbar mToolbar;
    private MyApplication myApplication;

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.pain_mgmt_list);

        initUI();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.Health_Record));

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Health_Record));

        getRecords();

    }

    private void initUI()
    {
        mRecyclerView = (RecyclerView)findViewById(R.id.myRecycler);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_action);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void getRecords()
    {
        Adapter adb = new Adapter(context);
        adb.getRecords(UserSession.currentSession().patient_ID,new Adapter.SynceDataListener<BinForRecords>() {
            @Override
            public void onSynced(BinForRecords bin) {
                if(bin!=null && bin.getResult().size()>0)
                {
                    record_list = bin.getResult();
                    adapter = new Health_Record_Adapter(My_Health_RecordActivity.this, record_list);
                    mRecyclerView.setAdapter(adapter);
                }
            }
        });

    }

}
