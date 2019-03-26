package com.krvang.lindved.whisttracker.be;

public class Player {

    private String mName;
    private int mScore;

    public Player(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public void updateScore(int scoreToAdd){
        mScore += scoreToAdd;
    }
}
