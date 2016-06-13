// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui.radiologist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import java.util.ArrayList;

import au.com.capitalradiology.BaseActivity;
import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Adapter;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.model.BinForRadiologist;

public class RadiologistListActivity extends BaseActivity
{
    private ArrayList<BinForRadiologist.Resource> feedsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
     private Context context = RadiologistListActivity.this;
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
        getSupportActionBar().setTitle(getString(R.string.Radiologist_name));

        myApplication = (MyApplication)getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.Radiologist_name));


        getRadiologistList();

    }

    private void initUI()
    {
        mRecyclerView = (RecyclerView)findViewById(R.id.myRecycler);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_action);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void getRadiologistList()
    {
        Adapter adb = new Adapter(context);
        adb.getRadiologist_List(new Adapter.SynceDataListener<BinForRadiologist>() {
            @Override
            public void onSynced(BinForRadiologist bin) {
                if(bin!=null && bin.getResource().size()>0)
                {
                    feedsList = bin.getResource();
                    initListeners();
                }
            }
        });
    }

    private void initListeners()
    {
        final ParallaxRecyclerAdapter<BinForRadiologist.Resource> adapter = new ParallaxRecyclerAdapter<BinForRadiologist.Resource>(feedsList) {
            @Override
            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<BinForRadiologist.Resource> adapter, int i) {
                ((CustomViewHolder) viewHolder).txt_Title.setText(adapter.getData().get(i).getFirstName()+" "+adapter.getData().get(i).getLastName());
                ((CustomViewHolder) viewHolder).txt_Education.setText(adapter.getData().get(i).getEducation());
                ((CustomViewHolder) viewHolder).txt_Speciality.setText(adapter.getData().get(i).getSpeciality());
                loader.displayImage(Utils.makeUrl(context,false,adapter.getData().get(i).getProfileImageUrl()),((CustomViewHolder) viewHolder).img_Thumbnail,options());
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, final ParallaxRecyclerAdapter<BinForRadiologist.Resource> adapter, int i) {
                return new CustomViewHolder(getLayoutInflater().inflate(R.layout.listitem_radiology, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<BinForRadiologist.Resource> adapter) {
                return feedsList.size();
            }
        };


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        View header = getLayoutInflater().inflate(R.layout.header_home, mRecyclerView, false);
        adapter.setParallaxHeader(header, mRecyclerView);
        adapter.setData(feedsList);
        mRecyclerView.setAdapter(adapter);
    }

    protected class CustomViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_Title;
        TextView txt_Education;
        TextView txt_Speciality;
        TextView txt_Description;
        ImageView img_Thumbnail;
        RelativeLayout rel_main;

        public CustomViewHolder(View view) {
            super(view);
            this.img_Thumbnail = (ImageView) view.findViewById(R.id.imgView);
            this.txt_Title = (TextView) view.findViewById(R.id.txtPrimary);
            this.txt_Education = (TextView) view.findViewById(R.id.txtSecondary);
            this.txt_Speciality = (TextView) view.findViewById(R.id.txt_Speciality);
            this.txt_Description = (TextView) view.findViewById(R.id.txtDescription);
            this.rel_main  = (RelativeLayout)view.findViewById(R.id.rel_main);
        }
    }


    private DisplayImageOptions options()
    {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.profile_placeholder)
                .showImageOnFail(R.drawable.profile_placeholder)
                .showStubImage(R.drawable.profile_placeholder)
                        //rounded corner bitmap
                .displayer(new RoundedBitmapDisplayer(360))
                .delayBeforeLoading(1000)
                .resetViewBeforeLoading(false)  // default
                .cacheInMemory(true) // default
                .cacheOnDisc(true) // default
                .build();
        return options;
    }

}
