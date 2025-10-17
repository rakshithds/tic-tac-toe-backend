package com.lila.tic_tac_toe.repository;

import com.lila.tic_tac_toe.model.Game;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class GameRepository {
    private final Map<String, Game> games = new ConcurrentHashMap<>();

    public Game save(Game game) {
        games.put(game.getId(), game);
        return game;
    }

    public Optional<Game> findById(String id) {
        return Optional.ofNullable(games.get(id));
    }

    public void delete(String id) {
        games.remove(id);
    }
}
