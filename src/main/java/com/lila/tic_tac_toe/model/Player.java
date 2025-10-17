package com.lila.tic_tac_toe.model;

import java.util.UUID;

public class Player {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    private int wins;
    private int losses;
    private int draws;

    // Constructor for registration
    public Player(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }

    public void incrementWins() {
        this.wins++;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public void incrementDraws() {
        this.draws++;
    }

    public int getTotalScore() {
        return (wins * 3) + draws;
    }

}
