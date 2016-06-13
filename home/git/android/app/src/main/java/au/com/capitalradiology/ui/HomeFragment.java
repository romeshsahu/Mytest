package au.com.capitalradiology.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import java.util.ArrayList;

import au.com.capitalradiology.MyApplication;
import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.UserSession;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.model.FeedItem;
import au.com.capitalradiology.ui.location.LocationListActivity;
import au.com.capitalradiology.ui.my_health_record.My_Health_RecordActivity;
import au.com.capitalradiology.ui.pain_mgmt.PainMgmt_Category_ListActivity;
import au.com.capitalradiology.ui.procedure.ProcedureListActivity;
import au.com.capitalradiology.ui.radiologist.RadiologistListActivity;
import au.com.capitalradiology.ui.request_appointment.Request_Appointment_Choose_Location;

public class HomeFragment extends Fragment
{
    private ArrayList<FeedItem> feedsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
     private MyApplication myApplication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_screen, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        registerControl();
        myApplication = (MyApplication) getActivity().getApplication();

        Utils.googleAnalytics(myApplication, getString(R.string.dashboard));

        initList();
        initListeners();
    }

    private void registerControl() {

        mRecyclerView = (RecyclerView)getView().findViewById(R.id.myRecycler);
    }

    private void initListeners()
    {
        final ParallaxRecyclerAdapter<FeedItem> adapter = new ParallaxRecyclerAdapter<FeedItem>(feedsList) {
            @Override
            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<FeedItem> adapter, int i) {
                ((ViewHolder) viewHolder).txt_Title.setText(adapter.getData().get(i).getTitle());
                ((ViewHolder) viewHolder).txt_Description.setVisibility(View.GONE);
                ((ViewHolder) viewHolder).img_Thumbnail.setImageResource(adapter.getData().get(i).getThumbnail());
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, final ParallaxRecyclerAdapter<FeedItem> adapter, int i) {
                return new ViewHolder(getActivity().getLayoutInflater().inflate(R.layout.listitem_home, viewGroup, false));
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
                    case 0 :
                        if(UserSession.currentSession().userToken!=null) {
                            Intent intent = new Intent(getActivity(), My_Health_RecordActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(getActivity(), SignInActivity.class);
                            getActivity().finish();
                            startActivity(intent);
                        }
                        break;
                    case  1:
                        if(UserSession.currentSession().userToken!=null) {
                            Intent intent1 = new Intent(getActivity(), Request_Appointment_Choose_Location.class);
                            startActivity(intent1);
                        }
                        else
                        {
                            Intent intent = new Intent(getActivity(), SignInActivity.class);
                            getActivity().finish();
                            startActivity(intent);
                        }
                        break;
                    case 2 :
                        Intent i2 = new Intent(getActivity(),LocationListActivity.class);
                        startActivity(i2);
                        break;
                    case 3 :
                        Intent i3 = new Intent(getActivity(),RadiologistListActivity.class);
                        startActivity(i3);
                        break;
                    case 4 :
                        Intent i4 = new Intent(getActivity(),ProcedureListActivity.class);
                        startActivity(i4);
                        break;
                    case 5 :
                        Intent i5 = new Intent(getActivity(),PainMgmt_Category_ListActivity.class);
                        startActivity(i5);
                        break;
                }
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        View header = getActivity().getLayoutInflater().inflate(R.layout.header_home, mRecyclerView, false);
        adapter.setParallaxHeader(header, mRecyclerView);
        adapter.setData(feedsList);
        mRecyclerView.setAdapter(adapter);
    }

    private void initList()
    {
        FeedItem feed = new FeedItem();
        feed.setTitle(getString(R.string.home_item_title_1));
        feed.setThumbnail(R.drawable.record_icon);
        feedsList.add(feed);

        FeedItem feed2 = new FeedItem();
        feed2.setTitle(getString(R.string.home_item_title_2));
        feed2.setThumbnail(R.drawable.request_appointment_icon);
        feedsList.add(feed2);

        FeedItem feed3 = new FeedItem();
        feed3.setTitle(getString(R.string.home_item_title_3));
        feed3.setThumbnail(R.drawable.location_pin);
        feedsList.add(feed3);

        FeedItem feed4 = new FeedItem();
        feed4.setTitle(getString(R.string.home_item_title_4));
        feed4.setThumbnail(R.drawable.radiologyist_icon);
        feedsList.add(feed4);

        FeedItem feed5 = new FeedItem();
        feed5.setTitle(getString(R.string.home_item_title_5));
        feed5.setThumbnail(R.drawable.procedure_icon);
        feedsList.add(feed5);

        FeedItem feed6 = new FeedItem();
        feed6.setTitle(getString(R.string.home_item_title_6));
        feed6.setThumbnail(R.drawable.pain_mgmt);
        feedsList.add(feed6);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_Title;
        TextView txt_Description;
        ImageView img_Thumbnail;

        public ViewHolder(View view) {
            super(view);
            this.img_Thumbnail = (ImageView) view.findViewById(R.id.imgView);
            this.txt_Title = (TextView) view.findViewById(R.id.txtPrimary);
            this.txt_Description = (TextView) view.findViewById(R.id.txtSecondary);
        }
    }

}
