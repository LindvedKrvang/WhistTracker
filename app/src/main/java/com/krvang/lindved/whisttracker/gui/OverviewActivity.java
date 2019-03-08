package com.krvang.lindved.whisttracker.gui;

import android.support.v4.app.Fragment;

public class OverviewActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new OverviewFragment();
    }
}
