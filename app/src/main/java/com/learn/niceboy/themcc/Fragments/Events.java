package com.learn.niceboy.themcc.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.learn.niceboy.themcc.Adapter.EventsAdapter;
import com.learn.niceboy.themcc.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class Events extends Fragment {

    ProgressBar progressBar;
    ListView mainlistview;
    EventsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, null);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        mainlistview = (ListView) view.findViewById(R.id.listview);

        // To get data from back4app in List view
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    try {
                        adapter = new EventsAdapter(getActivity(), list);
                        mainlistview.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    } catch (Exception ee) {
                        Toast.makeText(getActivity(), "Error:" + ee, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error:" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
