package com.pepela.sunshine.sunshine;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private TextView detailTextView;
    private String LOG_TAG = DetailFragment.class.getSimpleName();
    private String hashTag = "#SunshineApp";
    private String mForecast;


    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        mForecast = intent.getStringExtra(Intent.EXTRA_TEXT);

        detailTextView = (TextView) rootView.findViewById(R.id.detail_forecast_textview);
        detailTextView.setText(mForecast);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_detail_fragment, menu);

        MenuItem mItem = menu.findItem(R.id.action_share);

        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(mItem);

        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent());
        } else {
            Log.e(LOG_TAG, "share action provider is null");
        }
    }

    private Intent shareIntent() {
        Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        myShareIntent.setType("text/plain");
        myShareIntent.putExtra(Intent.EXTRA_TEXT, mForecast + hashTag);
        return myShareIntent;
    }
}
