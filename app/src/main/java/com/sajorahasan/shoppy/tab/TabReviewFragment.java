package com.sajorahasan.shoppy.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sajorahasan.shoppy.R;

public class TabReviewFragment extends Fragment {

    private ListView listview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_review, container, false);

        listview = (ListView) view.findViewById(R.id.listView);


        return view;

    }
}