package au.com.capitalradiology.listadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.Utils;
import au.com.capitalradiology.model.BinForAvailable_Time;

public class Available_Time_Adapter extends BaseAdapter {

    private ArrayList<BinForAvailable_Time.Resource> objects = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public Available_Time_Adapter(Context context, ArrayList<BinForAvailable_Time.Resource> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public BinForAvailable_Time.Resource getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_spinner, null);
            Viewholder viewHolder = new Viewholder();
            viewHolder.txt_SlotName = (TextView)convertView.findViewById(R.id.spinnerTarget);


            convertView.setTag(viewHolder);
        }
        initializeViews((BinForAvailable_Time.Resource)getItem(position), (Viewholder) convertView.getTag(),position);
        return convertView;
    }

    private void initializeViews(final BinForAvailable_Time.Resource object, Viewholder holder,final int position1)
    {
        holder.txt_SlotName.setText(date_Time(object.getStartDatetime())+" - "+date_Time(object.getEndDatetime()));

    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want

    public class Viewholder
    {
        TextView txt_SlotName;
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
            return  sdfs.format(dt).replace("am","AM").replace("pm","PM");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}