package com.krvang.lindved.whisttracker.gui;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class StartActivity extends SingleFragmentActivity implements HomepageFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new HomepageFragment();
    }

    @Override
    public void onStartPressed(int numberOfPlayers) {
        Intent intent = MainActivity.getIntent(this, numberOfPlayers);
        startActivity(intent);
    }
}
