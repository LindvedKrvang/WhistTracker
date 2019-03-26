package com.krvang.lindved.whisttracker.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.krvang.lindved.whisttracker.R;
import com.krvang.lindved.whisttracker.model.PlayerModel;

public class MainActivity extends SingleFragmentActivity implements SetupFragment.Callbacks {

    private static final String EXTRA_NUMBER_OF_PLAYERS = "com.krvang.lindved.numberOfPlayers";

    @Override
    protected Fragment createFragment() {
        int numberOfPlayers = (int) getIntent().getSerializableExtra(EXTRA_NUMBER_OF_PLAYERS);
        return SetupFragment.newInstance(numberOfPlayers);
    }

    public static Intent getIntent(Context context, int numberOfPlayers) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_NUMBER_OF_PLAYERS, numberOfPlayers);
        return intent;
    }

    @Override
    public void goToOverview() {
        Fragment overviewFragment = OverviewFragment.newInstance();
        switchFragments(overviewFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayerModel.getInstance().clearModel();
    }
}
