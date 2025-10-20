package com.lila.tic_tac_toe.controller;

import com.lila.tic_tac_toe.model.Player;
import com.lila.tic_tac_toe.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderBoardControllerApi {

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping
    public List<Player> getLeaderBoard() {
        return playerRepository.findTopPlayers(10);
    }

}
