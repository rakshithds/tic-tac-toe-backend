package com.lila.tic_tac_toe.service.impl;

import com.lila.tic_tac_toe.handler.GameWebSocketHandler;
import com.lila.tic_tac_toe.model.Game;
import com.lila.tic_tac_toe.model.StatusEnum;
import com.lila.tic_tac_toe.repository.GameRepository;
import com.lila.tic_tac_toe.service.GameService;
import com.lila.tic_tac_toe.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final PlayerService playerService;

    public GameServiceImpl(GameRepository gameRepository, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
    }

    @Override
    public Game createGame() {
        Game game = new Game();
        return gameRepository.save(game);
    }

    @Override
    public Game getGameById(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }

    @Override
    public Game makeMove(String gameId, String playerId, int row, int col) {
        Game game = getGameById(gameId);

        boolean moveSuccess = game.makeMove(row, col, playerId);
        if (!moveSuccess) {
            throw new RuntimeException("Invalid move");
        }

        if (game.getStatus() == StatusEnum.FINISHED) {
            updatePlayerStatus(game);
        }
        return gameRepository.save(game);
    }

    private void updatePlayerStatus(Game game) {
        String playerXId = game.getPlayerXId();
        String playerOId = game.getPlayerOId();
        String winnerId = game.getWinnerId();
        if ("DRAW".equals(winnerId)) {
            playerService.updatePlayerStats(playerXId, "DRAW");
            playerService.updatePlayerStats(playerOId, "DRAW");
        } else {
            String loserId = winnerId.equals(playerXId) ? playerOId : playerXId;
            playerService.updatePlayerStats(winnerId, "WIN");
            playerService.updatePlayerStats(loserId, "LOSS");
        }

    }
}
