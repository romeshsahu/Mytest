// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.pain_mgmt;

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
import au.com.capitalradiology.model.BinForPain_Mgmt;


public class PainMgmt_ListActivity extends BaseActivity
{
    private List<BinForPain_Mgmt.Resource> feedsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Context context = PainMgmt_ListActivity.this;
    private Toolbar mToolbar;
    private ImageLoader loader = ImageLoader.getInstance();
    private MyApplication myApplication;


    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.pain_mgmt_list);

        initUI();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.Pain_Mgmt_list));

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Pain_Mgmt_list));

        if(getIntent().getExtras()!=null)
            getPain_Mgmt_Details(getIntent().getIntExtra("category_ID", 0));
    }

    private void initUI()
    {
        mRecyclerView = (RecyclerView)findViewById(R.id.myRecycler);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_action);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getPain_Mgmt_Details(int category_Id)
    {
        Adapter adb = new Adapter(context);
        adb.getPain_Mgmt_Details(category_Id, new Adapter.SynceDataListener<BinForPain_Mgmt>() {
            @Override
            public void onSynced(BinForPain_Mgmt bin) {

                if (bin != null && bin.getResource().size() > 0) {
                    feedsList = bin.getResource();
                    initListeners();
                }
            }
        });
    }

    private void initListeners()
    {
        final ParallaxRecyclerAdapter<BinForPain_Mgmt.Resource> adapter = new ParallaxRecyclerAdapter<BinForPain_Mgmt.Resource>(feedsList) {
            @Override
            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<BinForPain_Mgmt.Resource> adapter, int i) {
                ((CustomViewHolder) viewHolder).txt_Title.setText(adapter.getData().get(i).getName());
                ((CustomViewHolder) viewHolder).txt_Description.setText(adapter.getData().get(i).getDescription());

                ((CustomViewHolder) viewHolder).img_Thumbnail.getLayoutParams().height = Utils.getHeight(context, R.drawable.location_pin);
                ((CustomViewHolder) viewHolder).img_Thumbnail.getLayoutParams().width = Utils.getWidth(context, R.drawable.location_pin);
                loader.displayImage(Utils.makeUrl(context,false,adapter.getData().get(i).getImageUrl()),((CustomViewHolder)viewHolder).img_Thumbnail);
              }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, final ParallaxRecyclerAdapter<BinForPain_Mgmt.Resource> adapter, int i) {
                return new CustomViewHolder(getLayoutInflater().inflate(R.layout.listitem_home, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<BinForPain_Mgmt.Resource> adapter) {
                return feedsList.size();
            }
        };

        adapter.setOnClickEvent(new ParallaxRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(context,Pain_Mgmt_Detail.class);
                intent.putExtra("Name",feedsList.get(position).getName());
                intent.putExtra("Description",feedsList.get(position).getDescription());
                intent.putExtra("Image_Url",Utils.makeUrl(context,false,feedsList.get(position).getImageUrl()));
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
