package com.lila.tic_tac_toe.service.impl;

import com.lila.tic_tac_toe.model.Game;
import com.lila.tic_tac_toe.service.MatchMackingService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MatchMakingServiceImpl implements MatchMackingService {

    private final ConcurrentLinkedQueue<String> waitingPlayers = new ConcurrentLinkedQueue<>();
    private final Map<String, Game> waitingGames = new ConcurrentHashMap<>();

    @Override
    public synchronized Game findOrCreateMatch(String playerId) {
        String opponentId = waitingPlayers.poll();

        if (opponentId == null) {
            Game game = new Game();
            game.setPlayerXId(playerId);
            waitingPlayers.offer(playerId);
            waitingGames.put(playerId, game);
            return game;
        }

        Game existingGame = waitingGames.remove(opponentId);
        if (existingGame == null) {
            existingGame = new Game();
            existingGame.setPlayerXId(opponentId);
        }

        existingGame.setPlayerOId(playerId);
        existingGame.startGame();

        return existingGame;
    }

    @Override
    public void removeFromQueue(String playerId) {
        waitingPlayers.remove(playerId);
        waitingGames.remove(playerId);
    }
}
