package au.com.capitalradiology.listadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import au.com.capitalradiology.R;
import au.com.capitalradiology.model.BinForServiceList;

public class SpinAdapter extends BaseAdapter {

    private ArrayList<BinForServiceList.Resource> objects = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public SpinAdapter(Context context,ArrayList<BinForServiceList.Resource> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public BinForServiceList.Resource getItem(int position) {
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
            viewHolder.txt_ServiceName = (TextView)convertView.findViewById(R.id.spinnerTarget);


            convertView.setTag(viewHolder);
        }
        initializeViews((BinForServiceList.Resource)getItem(position), (Viewholder) convertView.getTag(),position);
        return convertView;
    }

    private void initializeViews(final BinForServiceList.Resource object, Viewholder holder,final int position1)
    {
        holder.txt_ServiceName.setText(object.getServiceName());

        //TODO implement
    }



    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want

    public class Viewholder
    {
        TextView txt_ServiceName;
    }

}