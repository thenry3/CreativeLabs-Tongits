package com.example.tongits;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class fragScreen3 extends Fragment {

    private TextView description;

    public fragScreen3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen3, container, false);

        description = view.findViewById(R.id.formatDesc1);
        description.setText(Html.fromHtml(getString(R.string.desc1)));

        return view;
    }
}