package com.learn.niceboy.themcc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.learn.niceboy.themcc.EventDescription;
import com.learn.niceboy.themcc.R;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SubEventsAdapter extends ArrayAdapter<ParseObject> implements Filterable, BaseSliderView.OnSliderClickListener {

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    ArrayList<ParseObject> arraylist;

    private Context pcontext;
    private List<ParseObject> plist;

    public SubEventsAdapter(Context context, List<ParseObject> list1) {
        super(context, R.layout.adapter_events, list1);
        pcontext = context;
        plist = list1;
        arraylist = new ArrayList<ParseObject>();
        arraylist.addAll(plist);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        plist.clear();
        if (charText.length() == 0) {
            plist.addAll(arraylist);
        } else {
            for (ParseObject obj : arraylist) {
                if (charText.length() != 0 && obj.getString("Sub_Event_Name").toLowerCase(Locale.getDefault()).contains(charText)) {
                    plist.add(obj);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(pcontext).inflate(R.layout.adapter_sub_events, null, true);
            holder = new ViewHolder();

            holder.event_name = (TextView) convertView.findViewById(R.id.name);
            holder.event_date = (TextView) convertView.findViewById(R.id.date);
            holder.event_image = (ImageView) convertView.findViewById(R.id.image);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject object = plist.get(position);

        final String event_name = object.getString("Event_Name");

        final String sub_event_name = object.getString("Sub_Event_Name");
        holder.event_name.setText(sub_event_name);

        final String date = object.getString("Event_Date");
        holder.event_date.setText(date);

        final boolean registration = object.getBoolean("Registration");

        Picasso.with(getContext().getApplicationContext()).load(object.getParseFile("Image").getUrl()).noFade().into(holder.event_image);

        holder.event_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(pcontext, EventDescription.class);
                intent.putExtra("Event_Name", event_name);
                intent.putExtra("Sub_Event_Name", sub_event_name);
                intent.putExtra("Registration", registration);
                pcontext.startActivity(intent);
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView event_name, event_date;
        ImageView event_image;
    }
}
