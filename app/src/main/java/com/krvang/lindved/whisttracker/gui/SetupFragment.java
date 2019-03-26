package com.krvang.lindved.whisttracker.gui;

import android.content.Context;
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

    public interface Callbacks {
        void goToOverview();
    }

    private final boolean SHOW = true;
    private final boolean HIDE = false;

    private interface ButtonCommand {
        void buttonClicked();
    }

    private Callbacks mCallbacks;
    private ButtonCommand mButtonCommand;

    private int mMaxNumberOfPlayers;

    private RecyclerView mRecyclerView;
    private PlayerAdapter mPlayerAdapter;
    private PlayerModel mPlayerModel;

    private ImageView mAddPlayerButton;
    private EditText mPlayerNameTextField;
    private TextView mSubTitle;

    private MenuItem mStart;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMaxNumberOfPlayers = getArguments().getInt(ARG_NUMBER_OF_PLAYERS_ID);
        mPlayerModel = PlayerModel.getInstance();
        mButtonCommand = new AddPlayerCommand();

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        initializeWidgets(view);
        updateUi();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_setup, menu);

        mStart = menu.findItem(R.id.btnStart);
        mStart.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnStart:
                setHasOptionsMenu(false);
                mCallbacks.goToOverview();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    private void initializeWidgets(View view){
        mRecyclerView = view.findViewById(R.id.playerRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddPlayerButton = view.findViewById(R.id.btnAdd);
        mAddPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonCommand.buttonClicked();
            }
        });

        mPlayerNameTextField = view.findViewById(R.id.txtPlayerName);
        mSubTitle = view.findViewById(R.id.txtSubTitle);
    }

    private void addNewPlayer() {
        String name = mPlayerNameTextField.getText().toString();
        mPlayerNameTextField.setText("");

        Player player = new Player(name);
        mPlayerModel.addPlayer(player);

        updateUi();
    }

    private void updateUi() {
        int remainingPlayers = mMaxNumberOfPlayers - mPlayerModel.getAmountOfPlayers();
        mSubTitle.setText(String.format("%s: %s", getString(R.string.subtitle_remaining_players), remainingPlayers));

        showHideEditField(mPlayerModel.getAmountOfPlayers() >= mMaxNumberOfPlayers ? HIDE : SHOW);

        if(mPlayerAdapter == null){
            mPlayerAdapter = new PlayerAdapter(mPlayerModel.getPlayers());
            mRecyclerView.setAdapter(mPlayerAdapter);
        }
        else {
            mPlayerAdapter.setPlayers(mPlayerModel.getPlayers());
            mPlayerAdapter.notifyDataSetChanged();
        }
    }

    private void showHideEditField(boolean show) {
        if(show) {
            mPlayerNameTextField.setEnabled(true);
            mPlayerNameTextField.setVisibility(View.VISIBLE);
            mAddPlayerButton.setVisibility(View.VISIBLE);
        } else {
            mPlayerNameTextField.setEnabled(false);
            mPlayerNameTextField.setVisibility(View.GONE);
            mAddPlayerButton.setVisibility(View.GONE);
        }
        showHideMenu(!show);
    }

    private void showHideMenu(boolean show) {
        if (mStart == null) return;
        mStart.setEnabled(show);
    }

    private void setupEditPlayer(int playerIndex) {
        showHideEditField(SHOW);
        mAddPlayerButton.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_edit));
        Player player = mPlayerModel.getPlayerByIndex(playerIndex);
        mPlayerNameTextField.setText(player.getName());
        mPlayerNameTextField.requestFocus();
        mButtonCommand = new EditPlayerCommand(playerIndex);
    }

    private void editPlayer(int playerIndex) {
        String name = mPlayerNameTextField.getText().toString();
        mPlayerModel.updatePlayerName(playerIndex, name);
        mPlayerNameTextField.setText("");
        mAddPlayerButton.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_add));
        updateUi();
        mButtonCommand = new AddPlayerCommand();
    }

    private class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder> {

        private List<Player> players;

        PlayerAdapter(List<Player> players) {
            this.players = players;
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
            nameLabel.setText(players.get(i).getName());

            ImageView deleteButton = playerViewHolder.itemView.findViewById(R.id.btnDelete);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlayerModel.removePlayerAtIndex(playerViewHolder.getAdapterPosition());
                    updateUi();
                }
            });

            ImageView editButton = playerViewHolder.itemView.findViewById(R.id.btnEdit);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int playerIndex = playerViewHolder.getAdapterPosition();
                    setupEditPlayer(playerIndex);
                }
            });
        }

        @Override
        public int getItemCount() {
            return players.size();
        }

        private void setPlayers(List<Player> players){
            this.players = players;
        }
    }

    private class PlayerViewHolder extends RecyclerView.ViewHolder {

        PlayerViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.setup_player_list_item, parent, false));
        }
    }

    private class AddPlayerCommand implements ButtonCommand {

        @Override
        public void buttonClicked() {
            addNewPlayer();
        }
    }

    private class EditPlayerCommand implements ButtonCommand {

        private int playerIndex;

        EditPlayerCommand(int playerIndex){
            this.playerIndex = playerIndex;
        }

        @Override
        public void buttonClicked() {
            editPlayer(playerIndex);
        }
    }
}
