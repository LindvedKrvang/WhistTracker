package com.krvang.lindved.whisttracker.model;

import com.krvang.lindved.whisttracker.be.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerModel {

    private static PlayerModel INSTANCE;

    public static PlayerModel getInstance(){
        if (INSTANCE == null)
            INSTANCE = new PlayerModel();
        return INSTANCE;
    }

    private List<Player> mPlayers;

    private PlayerModel(){
        mPlayers = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return mPlayers;
    }

    public int getAmountOfPlayers() {
        return mPlayers.size();
    }

    public Player getPlayerByIndex(int index) {
        return mPlayers.get(index);
    }

    public void addPlayer(Player player) {
        mPlayers.add(player);
    }

    public void removePlayerAtIndex(int index) {
        mPlayers.remove(index);
    }

    public void updatePlayerName(int index, String name) {
        mPlayers.get(index).setName(name);
    }
}
