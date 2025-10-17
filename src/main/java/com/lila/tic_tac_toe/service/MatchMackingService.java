package com.lila.tic_tac_toe.service;

import com.lila.tic_tac_toe.model.Game;

public interface MatchMackingService {

    Game findOrCreateMatch(String playerId);

    void removeFromQueue(String playerId);
}
