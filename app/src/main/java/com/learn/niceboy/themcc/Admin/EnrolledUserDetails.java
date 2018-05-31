package com.learn.niceboy.themcc.Admin;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.learn.niceboy.themcc.Adapter.EnrolledUserDetailAdapter;
import com.learn.niceboy.themcc.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class EnrolledUserDetails extends AppCompatActivity {

    ProgressBar progressBar;
    ListView mainlistview;
    EnrolledUserDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolled_user_details);

        Intent mIntent = getIntent();
        final String event_name = mIntent.getStringExtra("Event_Name");
        final String sub_event_name = mIntent.getStringExtra("Sub_Event_Name");

        getSupportActionBar().setTitle(sub_event_name);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        mainlistview = (ListView) findViewById(R.id.listview);

        // To get data from back4app in List view
        ParseQuery<ParseObject> query = ParseQuery.getQuery("EnrolledUser");
        query.whereEqualTo("EventName", event_name);
        query.whereEqualTo("SubEventName", sub_event_name);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    try {
                        adapter = new EnrolledUserDetailAdapter(EnrolledUserDetails.this, list);
                        mainlistview.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    } catch (Exception ee) {
                        Toast.makeText(EnrolledUserDetails.this, "Error:" + ee, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EnrolledUserDetails.this, "Error:" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    adapter.filter(newText.toString().trim());
                    mainlistview.invalidate();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Please wait detail is loading...", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
        return true;
    }
}
