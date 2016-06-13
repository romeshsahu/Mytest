package au.com.capitalradiology.listadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.model.Location;
import au.com.capitalradiology.ui.location.LocationDetail;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.CustomViewHolder>{
    private List<Location.Resource> feedItemList;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private ImageLoader loader = ImageLoader.getInstance();

    public LocationAdapter(Context context, List<Location.Resource> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_location, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Location.Resource feedItem = feedItemList.get(i);

       customViewHolder.txt_Title.setText(feedItem.getLocation());
       customViewHolder.txt_Description.setText(feedItem.getServiceNames());
       customViewHolder.txt_Description.setVisibility(View.VISIBLE);
     //customViewHolder.txt_Description.setMaxLines(Integer.MAX_VALUE);

        customViewHolder.img_Thumbnail.getLayoutParams().height = 90;
        customViewHolder.img_Thumbnail.getLayoutParams().width = 190;
       loader.displayImage(Utils.makeLocationUrl(mContext,feedItem.getImageThumbUrl()),customViewHolder.img_Thumbnail,Default());

        customViewHolder.rel_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LocationDetail.class);
                intent.putExtra("LocationName", feedItem.getLocation());
                intent.putExtra("Image_Url", feedItem.getImageMainUrl());
                intent.putExtra("Time", feedItem.getOpeningTimes());
                intent.putExtra("Fax", feedItem.getFax());
                intent.putExtra("Phone", feedItem.getPhone());
                intent.putExtra("Address", feedItem.getFullAddress());
                intent.putExtra("Services", feedItem.getServiceNames());
                intent.putExtra("Location_ID",feedItem.getLocationId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    protected class CustomViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_Title;
        TextView txt_Description;
        ImageView img_Thumbnail;
        LinearLayout rel_Main;

        public CustomViewHolder(View view) {
            super(view);
            this.img_Thumbnail = (ImageView) view.findViewById(R.id.imgView);
            this.txt_Title = (TextView) view.findViewById(R.id.txtPrimary);
            this.txt_Description = (TextView) view.findViewById(R.id.txtSecondary);
            this.rel_Main = (LinearLayout) view.findViewById(R.id.rel_main);
        }
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