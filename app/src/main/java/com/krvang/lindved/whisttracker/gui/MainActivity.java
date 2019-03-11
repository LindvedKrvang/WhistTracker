package com.krvang.lindved.whisttracker.gui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.krvang.lindved.whisttracker.R;

public class MainActivity extends SingleFragmentActivity implements HomepageFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new HomepageFragment();
    }

    @Override
    public void onStartPressed(int numberOfPlayers) {
        Fragment fragment = SetupFragment.newInstance(numberOfPlayers);
        switchFragment(fragment);
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
