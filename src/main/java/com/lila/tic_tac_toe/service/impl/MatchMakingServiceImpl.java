package com.lila.tic_tac_toe.service.impl;

import com.lila.tic_tac_toe.model.Game;
import com.lila.tic_tac_toe.service.MatchMackingService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MatchMakingServiceImpl implements MatchMackingService {

    private final ConcurrentLinkedQueue<Object> waitingPlayers = new ConcurrentLinkedQueue<>();

    @Override
    public Game findOrCreateMatch(String playerId) {
        String opponentId = (String) waitingPlayers.poll();

        if (opponentId == null) {
            waitingPlayers.offer(playerId);
            Game game = new Game();
            game.setPlayerXId(playerId);
            return game;
        }

        Game game = new Game();
        game.setPlayerXId(opponentId);
        game.setPlayerOId(playerId);
        game.startGame();

        return game;
    }

    @Override
    public void removeFromQueue(String playerId) {
        waitingPlayers.remove(playerId);
    }
}
