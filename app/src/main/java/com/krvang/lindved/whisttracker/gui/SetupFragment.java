package com.krvang.lindved.whisttracker.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krvang.lindved.whisttracker.R;
import com.krvang.lindved.whisttracker.be.Player;

import java.util.Arrays;
import java.util.List;

public class SetupFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PlayerAdapter mPlayerAdapter;

    private List<Player> mPlayers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayers = Arrays.asList(
                new Player("Player One"),
                new Player("Player Two"),
                new Player("Player Three"),
                new Player("Player Four")
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        initializeRecyclerView(view);
        updateUi();
        return view;
    }

    private void initializeRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.playerRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void updateUi() {
        mPlayerAdapter = new PlayerAdapter(mPlayers);
        mRecyclerView.setAdapter(mPlayerAdapter);
    }

    private class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder> {

        private List<Player> mPlayers;

        public PlayerAdapter(List<Player> players) {
            mPlayers = players;
        }

        @NonNull
        @Override
        public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new PlayerViewHolder(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull PlayerViewHolder playerViewHolder, int i) {
            TextView playerLabel = playerViewHolder.itemView.findViewById(R.id.txtPlayerLabel);
            playerLabel.setText(R.string.player_label);

            TextView nameLabel = playerViewHolder.itemView.findViewById(R.id.txtPlayerName);
            nameLabel.setText(mPlayers.get(i).getName());
        }

        @Override
        public int getItemCount() {
            return mPlayers.size();
        }
    }

    private class PlayerViewHolder extends RecyclerView.ViewHolder {

        public PlayerViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.player_list_item, parent, false));
        }
    }
}
