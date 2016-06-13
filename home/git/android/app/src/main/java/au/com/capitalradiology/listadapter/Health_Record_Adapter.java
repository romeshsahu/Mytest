package au.com.capitalradiology.listadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.model.BinForRecords;
import au.com.capitalradiology.ui.WebViewActivity;


public class Health_Record_Adapter extends RecyclerView.Adapter<Health_Record_Adapter.CustomViewHolder>{
    private List<BinForRecords.Result> feedItemList;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private ImageLoader loader = ImageLoader.getInstance();

    public Health_Record_Adapter(Context context, List<BinForRecords.Result> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_health_record, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final BinForRecords.Result feedItem = feedItemList.get(i);

        if (feedItem.getRecordType().equalsIgnoreCase("record"))
        {
            customViewHolder.txt_Schedule.setVisibility(View.GONE);
            customViewHolder.img_arrow.setVisibility(View.VISIBLE);
        }
        else
        {
            customViewHolder.txt_Schedule.setVisibility(View.VISIBLE);
            customViewHolder.img_arrow.setVisibility(View.GONE);
        }
        if(feedItem.getStartDatetime()!=null && feedItem.getEndDatetime()!=null) {
            customViewHolder.txt_Title.setText(date_Time(feedItem.getStartDatetime()) + " - " + enddate_Time(feedItem.getEndDatetime()));
        }
        customViewHolder.txt_Description.setText(feedItem.getLocation());
        customViewHolder.img_Thumbnail.getLayoutParams().height = Utils.getHeight(mContext, R.drawable.location_pin);
        customViewHolder.img_Thumbnail.getLayoutParams().width = Utils.getWidth(mContext, R.drawable.location_pin);
        loader.displayImage(Utils.makeUrl(mContext,false,feedItem.getImageUrl()),customViewHolder.img_Thumbnail);

        customViewHolder.rel_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (feedItem.getRecordType().equalsIgnoreCase("record")) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("MRN",feedItem.getMrn());
                    intent.putExtra("ACC",feedItem.getAcc());
                    mContext.startActivity(intent);
                }
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
        TextView txt_Schedule;
        ImageView img_Thumbnail;
        ImageView img_arrow;
        RelativeLayout rel_Main;

        public CustomViewHolder(View view) {
            super(view);
            this.img_Thumbnail = (ImageView) view.findViewById(R.id.imgView);
            this.img_arrow = (ImageView) view.findViewById(R.id.img_arrow);
            this.txt_Title = (TextView) view.findViewById(R.id.txtPrimary);
            this.txt_Description = (TextView) view.findViewById(R.id.txtSecondary);
            this.txt_Schedule = (TextView)view.findViewById(R.id.txt_Schedule);
            this.rel_Main = (RelativeLayout)view.findViewById(R.id.rel_main);
        }
    }


    private String date_Time(String startTime)
    {
        StringTokenizer tk = new StringTokenizer(startTime);
        String date = tk.nextToken();
        String time = tk.nextToken();

        Utils.LogE("=========================>>>>time is : "+time);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        Date dt;
        try {
            dt = sdf.parse(time);
            Utils.LogE("Time Display: " + sdfs.format(dt)); // <-- I got result here
            return  date + " at " +sdfs.format(dt).replace("am","AM").replace("pm","PM");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private String enddate_Time(String startTime)
    {
        StringTokenizer tk = new StringTokenizer(startTime);
        String date = tk.nextToken();
        String time = tk.nextToken();

        Utils.LogE("=========================>>>>time is : "+time);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        Date dt;
        try {
            dt = sdf.parse(time);
            Utils.LogE("Time Display: " + sdfs.format(dt)); // <-- I got result here
            return  sdfs.format(dt).replace("am","AM").replace("pm","PM");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}