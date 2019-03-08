package com.krvang.lindved.whisttracker.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krvang.lindved.whisttracker.R;

public class OverviewFragment extends Fragment {

    private TextView txtLabelPlayerOne;
    private TextView txtLabelPlayerTwo;
    private TextView txtLabelPlayerThree;
    private TextView txtLabelPlayerFour;

    private TextView txtScorePlayerOne;
    private TextView txtScorePlayerTwo;
    private TextView txtScorePlayerThree;
    private TextView txtScorePlayerFour;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        initializeTextVies(view);
        return view;
    }

    private void initializeTextVies(View view) {
        txtLabelPlayerOne = view.findViewById(R.id.txtLabelPlayerOne);
        txtLabelPlayerOne = view.findViewById(R.id.txtLabelPlayerTwo);
        txtLabelPlayerOne = view.findViewById(R.id.txtLabelPlayerThree);
        txtLabelPlayerOne = view.findViewById(R.id.txtLabelPlayerFour);

        txtScorePlayerOne = view.findViewById(R.id.txtScorePlayerOne);
        txtScorePlayerOne = view.findViewById(R.id.txtScorePlayerTwo);
        txtScorePlayerOne = view.findViewById(R.id.txtScorePlayerThree);
        txtScorePlayerOne = view.findViewById(R.id.txtScorePlayerFour);
    }
}
