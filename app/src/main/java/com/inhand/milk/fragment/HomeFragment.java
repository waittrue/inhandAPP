package com.inhand.milk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inhand.milk.R;

/**
 * HomeFragment
 * Desc:综合评分Fragment
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-17
 * Time: 08:30
 */
public class HomeFragment extends MainFragment {

    public HomeFragment() {
        this.titleId = R.string.fragment_title_babyinfo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(
                R.layout.fragment_home,
                container,
                false
        );
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void setListeners() {
        super.setListeners();
    }
}
