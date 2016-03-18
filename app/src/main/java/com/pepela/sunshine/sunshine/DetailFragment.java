package com.pepela.sunshine.sunshine;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private TextView detailTextView;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        String forecast = intent.getStringExtra(Intent.EXTRA_TEXT);

        detailTextView = (TextView) rootView.findViewById(R.id.detail_forecast_textview);
        detailTextView.setText(forecast);

        return rootView;
    }
}
