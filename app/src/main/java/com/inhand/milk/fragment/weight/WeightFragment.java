package com.inhand.milk.fragment.weight;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.inhand.milk.R;
import com.inhand.milk.fragment.TitleFragment;

/**
 * Created by Administrator on 2015/6/6.
 */
public class WeightFragment extends TitleFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_weight,null,false);
        initViews(mView);
        return mView;
    }
    private void initViews(View view){
        WeightTab weightTab = (WeightTab)view.findViewById(R.id.weight_tabs);
        weightTab.setTabNum(10);
        weightTab.initTabs();
        initLine(view);

    }
    private void initLine(View view){
        ImageView imageView = new ImageView(this.getActivity());
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.weight_fragment_line_container);
        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);

        int lineWidth = wm.getDefaultDisplay().getWidth() /4;
        imageView.setLayoutParams(new LinearLayout.LayoutParams(lineWidth, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setBackgroundColor(Color.RED);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(imageView);

    }
}
