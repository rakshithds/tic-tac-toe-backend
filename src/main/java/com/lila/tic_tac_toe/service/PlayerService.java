package com.lila.tic_tac_toe.service;

import com.lila.tic_tac_toe.model.Player;

public interface PlayerService {

    Player registerPlayer(String name);

    Player getPlayerById(String playerId);

    void updatePlayerStats(String playerId, String result);
}
