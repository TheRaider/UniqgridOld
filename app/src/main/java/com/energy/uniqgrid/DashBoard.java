package com.energy.uniqgrid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vinee_000 on 03-11-2017.
 */

public class DashBoard extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View customView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");

        setHasOptionsMenu(true);

        return  customView;

    }
}
