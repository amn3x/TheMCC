package com.learn.niceboy.themcc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.learn.niceboy.themcc.R;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpcomingEventsAdapter extends ArrayAdapter<ParseObject> {

    ArrayList<ParseObject> arraylist;

    private Context pcontext;
    private List<ParseObject> plist;

    public UpcomingEventsAdapter(Context context, List<ParseObject> list1) {
        super(context, R.layout.adapter_events, list1);
        pcontext = context;
        plist = list1;
        arraylist = new ArrayList<ParseObject>();
        arraylist.addAll(plist);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(pcontext).inflate(R.layout.adapter_upcoming_events, null, true);
            holder = new ViewHolder();

            holder.event_name = (TextView) convertView.findViewById(R.id.name);
            holder.event_date = (TextView) convertView.findViewById(R.id.date);
            holder.event_desc = (TextView) convertView.findViewById(R.id.desc);
            holder.sliderShow = (SliderLayout) convertView.findViewById(R.id.thumbnail);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject object = plist.get(position);

        final String event_name = object.getString("Event_Name");
        holder.event_name.setText(event_name);

        final String event_date = object.getString("Events_Date");
        holder.event_date.setText(event_date);

        final String event_desc = object.getString("Event_Desc");
        holder.event_desc.setText(event_desc);



        String image_url = object.getParseFile("Img1").getUrl();
        String image_url1 = object.getParseFile("Img2").getUrl();

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("1", image_url);
        url_maps.put("2", image_url1);

        for (String images : url_maps.keySet()) {

            DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());
            defaultSliderView.image(url_maps.get(images));
            holder.sliderShow.addSlider(defaultSliderView);
        }
        holder.sliderShow.setDuration(3000);

        return convertView;
    }

    public static class ViewHolder {
        TextView event_name, event_desc, event_date;
        SliderLayout sliderShow;
    }
}

