// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.procedure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import au.com.capitalradiology.BaseActivity;
import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Adapter;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.model.BinForProcedure;

public class ProcedureListActivity extends BaseActivity
{
    private List<BinForProcedure.Resource> feedsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
     private Context context = ProcedureListActivity.this;
    private Toolbar mToolbar;
    private MyApplication myApplication;
    private ImageLoader loader = ImageLoader.getInstance();

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.pain_mgmt_list);

        initUI();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.procedure_name));

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Procedure_list));

        getProcedureList();
    }

    private void initUI()
    {
        mRecyclerView = (RecyclerView)findViewById(R.id.myRecycler);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_action);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void getProcedureList()
    {
        Adapter adb = new Adapter(context);
        adb.getProcedure_List(new Adapter.SynceDataListener<BinForProcedure>() {
            @Override
            public void onSynced(BinForProcedure bin) {

                if(bin!=null && bin.getResource().size()>0) {
                    feedsList = bin.getResource();
                    initListeners();
                }
            }
        });
    }


    private void initListeners()
    {
        final ParallaxRecyclerAdapter<BinForProcedure.Resource> adapter = new ParallaxRecyclerAdapter<BinForProcedure.Resource>(feedsList) {
            @Override
            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<BinForProcedure.Resource> adapter, int i) {
                ((CustomViewHolder) viewHolder).txt_Title.setText(adapter.getData().get(i).getServiceName());
                ((CustomViewHolder) viewHolder).txt_Description.setVisibility(View.GONE);
                ((CustomViewHolder) viewHolder).img_Thumbnail.getLayoutParams().height = Utils.getHeight(context, R.drawable.location_pin);
                ((CustomViewHolder) viewHolder).img_Thumbnail.getLayoutParams().width = Utils.getWidth(context, R.drawable.location_pin);
                loader.displayImage((Utils.makeUrl(context,false,adapter.getData().get(i).getImageUrl())),((CustomViewHolder)viewHolder).img_Thumbnail);
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, final ParallaxRecyclerAdapter<BinForProcedure.Resource> adapter, int i) {
                return new CustomViewHolder(getLayoutInflater().inflate(R.layout.listitem_procedure, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<BinForProcedure.Resource> adapter) {
                return feedsList.size();
            }
        };

        adapter.setOnClickEvent(new ParallaxRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(context,Procedure_Detail.class);
                intent.putExtra("Name",feedsList.get(position).getServiceName());
                intent.putExtra("Description",feedsList.get(position).getDescription());
                intent.putExtra("Image_Url",feedsList.get(position).getImageUrl());
                startActivity(intent);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        View header = getLayoutInflater().inflate(R.layout.header_home, mRecyclerView, false);

        adapter.setParallaxHeader(header, mRecyclerView);
        adapter.setData(feedsList);
        mRecyclerView.setAdapter(adapter);
    }

    protected class CustomViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_Title;
        TextView txt_Description;
        ImageView img_Thumbnail;
        RelativeLayout rel_Main;

        public CustomViewHolder(View view) {
            super(view);
            this.img_Thumbnail = (ImageView) view.findViewById(R.id.imgView);
            this.txt_Title = (TextView) view.findViewById(R.id.txtPrimary);
            this.txt_Description = (TextView) view.findViewById(R.id.txtSecondary);
            this.rel_Main = (RelativeLayout)view.findViewById(R.id.rel_main);
        }
    }


}
