package com.lila.tic_tac_toe.service.impl;

import com.lila.tic_tac_toe.exception.PlayerNotFoundException;
import com.lila.tic_tac_toe.model.Player;
import com.lila.tic_tac_toe.repository.PlayerRepository;
import com.lila.tic_tac_toe.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player registerPlayer(String name) {
        Player player = new Player(name);
        return playerRepository.save(player);
    }

    @Override
    public Player getPlayerById(String playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found" + playerId ));
    }

    @Override
    public void updatePlayerStats(String playerId, String result) {
        Player player = getPlayerById(playerId);

        switch (result) {
            case "WIN" -> player.incrementWins();
            case "LOSS" -> player.incrementLosses();
            case "DRAW" -> player.incrementDraws();
        }

        playerRepository.save(player);
    }
}
