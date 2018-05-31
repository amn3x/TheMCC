package com.learn.niceboy.themcc.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.learn.niceboy.themcc.Adapter.AdminEventsAdapter;
import com.learn.niceboy.themcc.R;
import com.learn.niceboy.themcc.SubEvents;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class AdminEvents extends AppCompatActivity {

    ProgressBar progressBar;
    ListView mainlistview;
    AdminEventsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_events);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        mainlistview = (ListView) findViewById(R.id.listview);

        getSupportActionBar().setTitle("Events");

        // To get data from back4app in List view
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    try {
                        adapter = new AdminEventsAdapter(getApplicationContext(), list);
                        mainlistview.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);

                        mainlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                ParseObject item = adapter.getItem(i);
                                String name = item.getString("Event_Name");

                                Intent intent = new Intent(getApplicationContext(), AdminSubEvents.class);
                                intent.putExtra("Event_Name", name);
                                startActivity(intent);
                            }
                        });
                    } catch (Exception ee) {
                        Toast.makeText(getApplicationContext(), "Error:" + ee, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error:" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
