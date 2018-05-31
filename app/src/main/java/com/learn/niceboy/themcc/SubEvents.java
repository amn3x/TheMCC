package com.learn.niceboy.themcc;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.learn.niceboy.themcc.Adapter.EventsAdapter;
import com.learn.niceboy.themcc.Adapter.SubEventsAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class SubEvents extends AppCompatActivity {

    ProgressBar progressBar;
    ListView mainlistview;
    SubEventsAdapter adapter;
    Spinner spinner;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_events);

        Intent mIntent = getIntent();
        final String event_name = mIntent.getStringExtra("Event_Name");

        getSupportActionBar().setTitle(event_name);

        text = (TextView) findViewById(R.id.emptyText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        spinner = (Spinner) findViewById(R.id.spin);

        mainlistview = (ListView) findViewById(R.id.listview);

        // Array of choices
        String categories[] = {"All", "Games", "Days", "Arts", "Sports", "Literary", "Flagship", "Finance", "DJ Night", "Other"};

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, categories);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String spinnerText = spinner.getSelectedItem().toString();

                //Toast.makeText(getApplicationContext(), spinnerText, Toast.LENGTH_LONG).show();

                if (spinnerText.equals("All")) {

                    progressBar.setVisibility(View.VISIBLE);
                    // To get data from back4app in List view
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("SubEvents");
                    query.whereEqualTo("AllCategory", spinnerText);
                    query.whereEqualTo("Event_Name", event_name);
                    query.addDescendingOrder("createdAt");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if (e == null) {
                                try {
                                    adapter = new SubEventsAdapter(SubEvents.this, list);
                                    mainlistview.setAdapter(adapter);
                                    progressBar.setVisibility(View.GONE);

                                    if (adapter.areAllItemsEnabled()) {
                                        if (adapter.isEmpty()) {
                                            text.setVisibility(View.VISIBLE);
                                            mainlistview.setEmptyView(text);
                                        }
                                    }

                                } catch (Exception ee) {
                                    Toast.makeText(SubEvents.this, "Error:" + ee, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SubEvents.this, "Error:" + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    // To get data from back4app in List view
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("SubEvents");
                    query.whereEqualTo("Category", spinnerText);
                    query.whereEqualTo("Event_Name", event_name);
                    query.addAscendingOrder("createdAt");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if (e == null) {
                                try {
                                    adapter = new SubEventsAdapter(SubEvents.this, list);
                                    mainlistview.setAdapter(adapter);
                                    progressBar.setVisibility(View.GONE);

                                    if (adapter.areAllItemsEnabled()) {
                                        if (adapter.isEmpty()) {
                                            text.setVisibility(View.VISIBLE);
                                            mainlistview.setEmptyView(text);
                                        }
                                    }
                                } catch (Exception ee) {
                                    Toast.makeText(SubEvents.this, "Error:" + ee, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SubEvents.this, "Error:" + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
