package com.learn.niceboy.themcc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.learn.niceboy.themcc.R;
import com.learn.niceboy.themcc.SubEvents;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Aadarsh on 6/18/2017.
 */

public class EventDescriptionAdapter extends ArrayAdapter<ParseObject> implements BaseSliderView.OnSliderClickListener {

    ArrayList<ParseObject> arraylist;

    private Context pcontext;
    private List<ParseObject> plist;

    public EventDescriptionAdapter(Context context, List<ParseObject> list1) {
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
            convertView = LayoutInflater.from(pcontext).inflate(R.layout.adapter_event_description, null, true);
            holder = new ViewHolder();

            holder.event_name = (TextView) convertView.findViewById(R.id.event_name);
            holder.event_description = (TextView) convertView.findViewById(R.id.event_description);
            holder.event_date = (TextView) convertView.findViewById(R.id.date);
            holder.org_name = (TextView) convertView.findViewById(R.id.user_name);
            holder.org_number = (TextView) convertView.findViewById(R.id.user_number);

            holder.sliderShow = (SliderLayout) convertView.findViewById(R.id.slider);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject object = plist.get(position);

        final String event_name = object.getString("Sub_Event_Name");
        holder.event_name.setText(event_name);

        final String event_date = object.getString("Event_Date");
        holder.event_date.setText(event_date);

        final String event_desc = object.getString("Event_Description");
        holder.event_description.setText(event_desc);

        final String orgName = object.getString("OrganizerName");
        holder.org_name.setText(orgName);

        final String orgNumber = object.getString("OrganizerNumber");
        holder.org_number.setText(orgNumber);

        holder.org_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + orgNumber));
                pcontext.startActivity(intent);
            }
        });

        String image_url = object.getParseFile("Event_Image_1").getUrl();
        String image_url1 = object.getParseFile("Event_Image_2").getUrl();
        String image_url2 = object.getParseFile("Event_Image_3").getUrl();

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("1", image_url);
        url_maps.put("2", image_url1);
        url_maps.put("3", image_url2);

        for (String images : url_maps.keySet()) {

            DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());
            defaultSliderView.image(url_maps.get(images));
            holder.sliderShow.addSlider(defaultSliderView);
        }
        holder.sliderShow.setDuration(4000);

        return convertView;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    public static class ViewHolder {
        TextView event_name, event_description, event_date, org_name, org_number;
        SliderLayout sliderShow;
    }
}
