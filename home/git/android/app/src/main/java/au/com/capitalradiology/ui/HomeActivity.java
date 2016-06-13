// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package au.com.capitalradiology.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.UserSession;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.model.FeedItem;
import au.com.capitalradiology.support_design.BaseDrawerActivity;
import au.com.capitalradiology.ui.location.LocationListActivity;
import au.com.capitalradiology.ui.my_health_record.My_Health_RecordActivity;
import au.com.capitalradiology.ui.pain_mgmt.PainMgmt_Category_ListActivity;
import au.com.capitalradiology.ui.procedure.ProcedureListActivity;
import au.com.capitalradiology.ui.radiologist.RadiologistListActivity;
import au.com.capitalradiology.ui.request_appointment.Request_Appointment_Choose_Location;

public class HomeActivity extends BaseDrawerActivity {
    private List<FeedItem> feedsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Context context = HomeActivity.this;
    private MyApplication myApplication;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.home_screen);
        getSupportActionBar().setTitle(getString(R.string.home_screen_name));

        myApplication = (MyApplication) getApplication();
        Utils.googleAnalytics(myApplication, getString(R.string.dashboard));

        initUI();
        initList();
        initListeners();

    }

    private void initUI() {
        mRecyclerView = (RecyclerView) findViewById(R.id.myRecycler);

    }

    private void initListeners() {
        final ParallaxRecyclerAdapter<FeedItem> adapter = new ParallaxRecyclerAdapter<FeedItem>(feedsList) {
            @Override
            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<FeedItem> adapter, int i) {
                ((ViewHolder) viewHolder).txt_Title.setText(adapter.getData().get(i).getTitle());
                ((ViewHolder) viewHolder).txt_Title1.setText(adapter.getData().get(i).getDescription());

                // ((ViewHolder) viewHolder).txt_Description.setVisibility(View.GONE);
                ((ViewHolder) viewHolder).img_Thumbnail.setImageResource(adapter.getData().get(i).getThumbnail());
                ((ViewHolder) viewHolder).layout.setAlpha((float) adapter.getData().get(i).getAlpha());
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, final ParallaxRecyclerAdapter<FeedItem> adapter, int i) {
                return new ViewHolder(getLayoutInflater().inflate(R.layout.listitem_home, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<FeedItem> adapter) {
                return feedsList.size();
            }
        };

        adapter.setOnClickEvent(new ParallaxRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, int position) {
                switch (position) {
                    case 0:

                        if (UserSession.currentSession().userToken != null) {
                            Intent intent1 = new Intent(context, Request_Appointment_Choose_Location.class);
                            startActivity(intent1);
                        } else {
                            Intent intent = new Intent(context, SignInActivity.class);
                            finish();
                            startActivity(intent);
                        }

                        break;
                    case 1:
                        Intent i2 = new Intent(context, LocationListActivity.class);
                        startActivity(i2);
                        break;
                    case 2:

                        if (UserSession.currentSession().userToken != null) {
                            Intent intent = new Intent(context, My_Health_RecordActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, SignInActivity.class);
                            finish();
                            startActivity(intent);
                        }
                        break;
                    case 3:
                        Intent i3 = new Intent(context, RadiologistListActivity.class);
                        startActivity(i3);
                        break;
                    case 4:
                        Intent i4 = new Intent(context, ProcedureListActivity.class);
                        startActivity(i4);
                        break;
                    case 5:
                        Intent i5 = new Intent(context, PainMgmt_Category_ListActivity.class);
                        startActivity(i5);
                        break;
                }

            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        View header = getLayoutInflater().inflate(R.layout.header_home, mRecyclerView, false);
        adapter.setParallaxHeader(header, mRecyclerView);
        adapter.setData(feedsList);
        mRecyclerView.setAdapter(adapter);
    }

    private void initList() {
        FeedItem feed = new FeedItem();
        feed.setTitle(getString(R.string.home_item_title_1));
        feed.setDescription(getString(R.string.home_item_title_11));
        feed.setThumbnail(R.drawable.icon_appoinment);
        feed.setAlpha(1.0);
        feedsList.add(feed);

        FeedItem feed2 = new FeedItem();
        feed2.setTitle(getString(R.string.home_item_title_2));
        feed2.setDescription(getString(R.string.home_item_title_22));
        feed2.setThumbnail(R.drawable.icon_capital);
        feed2.setAlpha(0.8);
        feedsList.add(feed2);

        FeedItem feed3 = new FeedItem();
        feed3.setTitle(getString(R.string.home_item_title_3));
        feed3.setDescription(getString(R.string.home_item_title_33));
        feed3.setThumbnail(R.drawable.icon_records);
        feed3.setAlpha(0.6);
        feedsList.add(feed3);

        FeedItem feed4 = new FeedItem();
        feed4.setTitle(getString(R.string.home_item_title_4));
        feed4.setDescription(getString(R.string.home_item_title_44));
        feed4.setThumbnail(R.drawable.icon_clinic);
        feed4.setAlpha(0.4);
        feedsList.add(feed4);

        FeedItem feed5 = new FeedItem();
        feed5.setTitle(getString(R.string.home_item_title_5));
        feed5.setDescription(getString(R.string.home_item_title_55));
        feed5.setThumbnail(R.drawable.icon_information);
        feed5.setAlpha(0.3);
        feedsList.add(feed5);

        FeedItem feed6 = new FeedItem();
        feed6.setTitle(getString(R.string.home_item_title_6));
        feed6.setDescription(getString(R.string.home_item_title_66));
        feed6.setThumbnail(R.drawable.icon_pain_managmnet);
        feed6.setAlpha(0.2);
        feedsList.add(feed6);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_Title, txt_Title1;
        // TextView txt_Description;
        ImageView img_Thumbnail;
        LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            this.img_Thumbnail = (ImageView) view.findViewById(R.id.icon);
            this.txt_Title = (TextView) view.findViewById(R.id.name);
            this.txt_Title1 = (TextView) view.findViewById(R.id.name1);
            this.layout = (LinearLayout) view.findViewById(R.id.layout);
            //  this.txt_Description = (TextView) view.findViewById(R.id.txtSecondary);
        }
    }

}
