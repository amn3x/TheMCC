package com.learn.niceboy.themcc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.learn.niceboy.themcc.R;
import com.learn.niceboy.themcc.SubEvents;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aadarsh on 6/11/2017.
 */

public class EventsAdapter extends ArrayAdapter<ParseObject> {

    ArrayList<ParseObject> arraylist;

    private Context pcontext;
    private List<ParseObject> plist;

    public EventsAdapter(Context context, List<ParseObject> list1) {
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
            convertView = LayoutInflater.from(pcontext).inflate(R.layout.adapter_events, null, true);
            holder = new ViewHolder();

            //holder.event_name = (TextView) convertView.findViewById(R.id.name);
            holder.event_image = (ImageView) convertView.findViewById(R.id.image);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject object = plist.get(position);

        final String name = object.getString("Event_Name");
        //holder.event_name.setText(name);

        Picasso.with(getContext().getApplicationContext()).load(object.getParseFile("Event_Image").getUrl()).noFade().into(holder.event_image);

        holder.event_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(pcontext, SubEvents.class);
                intent.putExtra("Event_Name", name);
                pcontext.startActivity(intent);
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        //TextView event_name;
        ImageView event_image;
    }
}
