package com.lila.tic_tac_toe.service;

import com.lila.tic_tac_toe.model.Game;

public interface GameService {

    Game createGame();

    Game getGameById(String gameId);

    Game makeMove(String gameId, String playerId, int row, int col);

}
