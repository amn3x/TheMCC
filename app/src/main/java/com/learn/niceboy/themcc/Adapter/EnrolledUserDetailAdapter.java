package com.learn.niceboy.themcc.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Filterable;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.learn.niceboy.themcc.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EnrolledUserDetailAdapter extends ArrayAdapter<ParseObject> implements Filterable, BaseSliderView.OnSliderClickListener {

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    ArrayList<ParseObject> arraylist;
    private Context pcontext;
    private List<ParseObject> plist;
    ProgressDialog progressBar;

    public EnrolledUserDetailAdapter(Context context, List<ParseObject> list1) {
        super(context, R.layout.adapter_enrolled_user_details, list1);
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
                if (charText.length() != 0 && obj.getString("FirstName").toLowerCase(Locale.getDefault()).contains(charText)) {
                    plist.add(obj);
                } else if (charText.length() != 0 && obj.getString("LastName").toLowerCase(Locale.getDefault()).contains(charText)) {
                    plist.add(obj);
                } else if (charText.length() != 0 && obj.getString("MobileNo").toLowerCase(Locale.getDefault()).contains(charText)) {
                    plist.add(obj);
                } else if (charText.length() != 0 && obj.getString("RollNo").toLowerCase(Locale.getDefault()).contains(charText)) {
                    plist.add(obj);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(pcontext).inflate(R.layout.adapter_enrolled_user_details, null, true);
            holder = new ViewHolder();

            holder.firstName = (TextView) convertView.findViewById(R.id.firstName);
            holder.lastName = (TextView) convertView.findViewById(R.id.lastName);
            holder.rollNo = (TextView) convertView.findViewById(R.id.rollNo);
            holder.mobileNo = (TextView) convertView.findViewById(R.id.mobileNo);
            holder.member = (TextView) convertView.findViewById(R.id.member);
            holder.year = (TextView) convertView.findViewById(R.id.year);
            holder.course = (TextView) convertView.findViewById(R.id.course);

            holder.paymentSwitch = (Switch) convertView.findViewById(R.id.paymentSwitch);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ParseObject object = plist.get(position);

        final String fname = object.getString("FirstName");
        holder.firstName.setText(fname);

        final String lname = object.getString("LastName");
        holder.lastName.setText(lname);

        final String rollNo = object.getString("RollNo");
        holder.rollNo.setText(rollNo);

        final String member = object.getString("Member");
        holder.member.setText(member);

        final String year = object.getString("Year");
        holder.year.setText(year);

        final String course = object.getString("Course");
        holder.course.setText(course);

        final String mob = object.getString("MobileNo");
        holder.mobileNo.setText(mob);

        final boolean payment = object.getBoolean("Switch");
        holder.paymentSwitch.setChecked(payment);

        holder.paymentSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String objectId = object.getObjectId();
                //Toast.makeText(pcontext, objectId, Toast.LENGTH_SHORT).show();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("EnrolledUser");
                query.whereEqualTo("objectId", objectId);

                query.getInBackground(objectId, new GetCallback<ParseObject>() {
                    public void done(ParseObject saveSwitch, ParseException e) {
                        if (e == null) {
                            // Now let's update it with some new data. In this case, only cheatMode and score
                            // will get sent to the Parse Cloud. playerName hasn't changed.
                            if (payment) {
                                saveSwitch.put("Switch", false);
                                saveSwitch.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        Toast.makeText(pcontext, "Payment Removed!", Toast.LENGTH_SHORT).show();
                                        holder.paymentSwitch.setChecked(false);
                                        notifyDataSetChanged();
                                    }
                                });
                            } else {
                                saveSwitch.put("Switch", true);
                                saveSwitch.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        Toast.makeText(pcontext, "Payment Completed!", Toast.LENGTH_SHORT).show();
                                        holder.paymentSwitch.setChecked(true);
                                        notifyDataSetChanged();
                                    }
                                });
                            }

                        }
                    }
                });
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView firstName, lastName, rollNo, mobileNo, member, year, course;
        Switch paymentSwitch;
    }


}
