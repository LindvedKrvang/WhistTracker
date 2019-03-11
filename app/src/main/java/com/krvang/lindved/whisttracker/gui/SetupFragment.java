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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.krvang.lindved.whisttracker.R;
import com.krvang.lindved.whisttracker.be.Player;
import com.krvang.lindved.whisttracker.model.PlayerModel;

import java.util.List;

public class SetupFragment extends Fragment {

    public static String ARG_NUMBER_OF_PLAYERS_ID = "com.krvang.lindved.numbers_of_players_id";

    public static SetupFragment newInstance(int numberOfPlayers) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NUMBER_OF_PLAYERS_ID, numberOfPlayers);

        SetupFragment fragment = new SetupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private int mMaxNumberOfPlayers;

    private RecyclerView mRecyclerView;
    private PlayerAdapter mPlayerAdapter;
    private PlayerModel mPlayerModel;

    private ImageView mAddPlayerButton;
    private EditText mPlayerNameTextField;
    private TextView mSubTitle;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMaxNumberOfPlayers = getArguments().getInt(ARG_NUMBER_OF_PLAYERS_ID);
        mPlayerModel = PlayerModel.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        initializeWidgets(view);
        updateUi();

        return view;
    }

    private void initializeWidgets(View view){
        mRecyclerView = view.findViewById(R.id.playerRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddPlayerButton = view.findViewById(R.id.btnAdd);
        mAddPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddPlayerClicked();
            }
        });

        mPlayerNameTextField = view.findViewById(R.id.txtPlayerName);
        mSubTitle = view.findViewById(R.id.txtSubTitle);
    }

    private void onAddPlayerClicked() {
        String name = mPlayerNameTextField.getText().toString();
        mPlayerNameTextField.setText("");

        Player player = new Player(name);
        mPlayerModel.addPlayer(player);

        updateUi();
    }

    private void updateUi() {
        int remainingPlayers = mMaxNumberOfPlayers - mPlayerModel.getPlayers().size();
        mSubTitle.setText(String.format("%s: %s", getString(R.string.subtitle_remaining_players), remainingPlayers));


        if(mPlayerModel.getPlayers().size() >= mMaxNumberOfPlayers) {
            mPlayerNameTextField.setEnabled(false);
            mAddPlayerButton.setVisibility(View.INVISIBLE);
        } else {
            mPlayerNameTextField.setEnabled(true);
            mAddPlayerButton.setVisibility(View.VISIBLE);
        }

        if(mPlayerAdapter == null){
            mPlayerAdapter = new PlayerAdapter(mPlayerModel.getPlayers());
            mRecyclerView.setAdapter(mPlayerAdapter);
        }
        else {
            mPlayerAdapter.setPlayers(mPlayerModel.getPlayers());
            mPlayerAdapter.notifyDataSetChanged();
        }
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
        public void onBindViewHolder(@NonNull final PlayerViewHolder playerViewHolder, int i) {
            TextView playerLabel = playerViewHolder.itemView.findViewById(R.id.txtPlayerLabel);
            playerLabel.setText(R.string.player_label);

            TextView nameLabel = playerViewHolder.itemView.findViewById(R.id.txtPlayerName);
            nameLabel.setText(mPlayers.get(i).getName());

            ImageView deleteButton = playerViewHolder.itemView.findViewById(R.id.btnDelete);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlayerModel.removePlayerAtIndex(playerViewHolder.getAdapterPosition());
                    updateUi();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mPlayers.size();
        }

        public void setPlayers(List<Player> players){
            mPlayers = players;
        }
    }

    private class PlayerViewHolder extends RecyclerView.ViewHolder {

        public PlayerViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.player_list_item, parent, false));
        }
    }
}
