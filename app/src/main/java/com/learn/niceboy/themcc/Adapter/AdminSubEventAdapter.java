package com.learn.niceboy.themcc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.learn.niceboy.themcc.R;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aadarsh on 7/9/2017.
 */

public class AdminSubEventAdapter extends ArrayAdapter<ParseObject> {

    ArrayList<ParseObject> arraylist;

    private Context pcontext;
    private List<ParseObject> plist;

    public AdminSubEventAdapter(Context context, List<ParseObject> list1) {
        super(context, R.layout.adapter_admin_events, list1);
        pcontext = context;
        plist = list1;
        arraylist = new ArrayList<ParseObject>();
        arraylist.addAll(plist);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(pcontext).inflate(R.layout.adapter_admin_events, null, true);
            holder = new ViewHolder();

            holder.event_name = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject object = plist.get(position);

        final String name = object.getString("Sub_Event_Name");
        holder.event_name.setText(name);

        return convertView;
    }

    public static class ViewHolder {
        TextView event_name;
    }
}
