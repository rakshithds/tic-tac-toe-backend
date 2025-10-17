package com.lila.tic_tac_toe.repository;

import com.lila.tic_tac_toe.model.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PlayerRepository {

    private final Map<String, Player> players = new ConcurrentHashMap<>();

    public Player save(Player player) {
        players.put(player.getId(), player);
        return player;
    }

    public Optional<Player> findById(String id) {
        return Optional.ofNullable(players.get(id));
    }

    public List<Player> findAll() {
        return new ArrayList<>(players.values());
    }

    public List<Player> findTopPlayers(int limit) {
        return players.values().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getTotalScore(), p1.getTotalScore()))
                .limit(limit)
                .toList();
    }
}