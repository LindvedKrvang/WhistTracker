package com.krvang.lindved.whisttracker.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.krvang.lindved.whisttracker.R;
import com.krvang.lindved.whisttracker.be.Player;
import com.krvang.lindved.whisttracker.model.PlayerModel;

import java.util.List;
import java.util.Locale;

public class OverviewFragment extends Fragment {


    public static OverviewFragment newInstance() {

        Bundle args = new Bundle();

        OverviewFragment fragment = new OverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private PlayerAdapter mPlayerAdapter;

    private PlayerModel mPlayerModel;

    private MenuItem btnCalculate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayerModel = PlayerModel.getInstance();

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        initializeRecyclerView(view);
        updateUi();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_overview, menu);

        btnCalculate = menu.findItem(R.id.btnCalculate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnCalculate:
                // TODO RKL: Go to CalculateFragment
                Toast.makeText(getActivity(), "Hello World", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void initializeRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.playerRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void updateUi() {

        if (mPlayerAdapter == null) {
            mPlayerAdapter = new PlayerAdapter(mPlayerModel.getPlayers());
            mRecyclerView.setAdapter(mPlayerAdapter);
        }
    }

    private class PlayerViewHolder extends RecyclerView.ViewHolder {

        PlayerViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.overview_player_list_item, parent, false));
        }
    }

    private class PlayerAdapter extends RecyclerView.Adapter {

        private List<Player> players;

        PlayerAdapter(List<Player> players) {
            this.players = players;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new PlayerViewHolder(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            TextView playerNameTextView = viewHolder.itemView.findViewById(R.id.txtPlayerName);
            playerNameTextView.setText(players.get(i).getName());

            TextView playerScoreTextView = viewHolder.itemView.findViewById(R.id.txtScore);
            playerScoreTextView.setText(String.format(Locale.getDefault(),"%d", players.get(i).getScore()));
        }

        @Override
        public int getItemCount() {
            return players.size();
        }
    }
}
