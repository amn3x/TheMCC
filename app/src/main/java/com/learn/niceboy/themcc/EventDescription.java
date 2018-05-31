package com.learn.niceboy.themcc;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.learn.niceboy.themcc.Adapter.EventDescriptionAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class EventDescription extends AppCompatActivity {

    ProgressBar progressBar;
    ListView mainlistview;
    EventDescriptionAdapter adapter;
    private FloatingActionButton fab;

    public static Activity eventDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);

        eventDesc = this;

        Intent mIntent = getIntent();
        final String event_name = mIntent.getStringExtra("Event_Name");
        final String sub_event_name = mIntent.getStringExtra("Sub_Event_Name");
        final boolean registration = mIntent.getBooleanExtra("Registration", true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        mainlistview = (ListView) findViewById(R.id.listview);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDescription.this, EnrollActivity.class);
                intent.putExtra("Event_Name", event_name);
                intent.putExtra("Sub_Event_Name", sub_event_name);
                startActivity(intent);
            }
        });

        fab.setVisibility(View.GONE);

        // To get data from back4app in List view
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event_Description");
        query.whereEqualTo("Event_Name", event_name);
        query.whereEqualTo("Sub_Event_Name", sub_event_name);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    try {
                        adapter = new EventDescriptionAdapter(EventDescription.this, list);
                        mainlistview.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);

                        if (registration) {
                            fab.setVisibility(View.VISIBLE);
                        } else {
                            fab.setVisibility(View.GONE);
                        }

                    } catch (Exception ee) {
                        Toast.makeText(EventDescription.this, "Error:" + ee, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EventDescription.this, "Error:" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
