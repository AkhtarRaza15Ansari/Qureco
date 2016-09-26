package com.sriyaan.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sriyaan.qureco.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PointsRedeemed extends Fragment {


    public PointsRedeemed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_points_redeemed, container, false);

        return rootview;
    }

}
