package com.lila.tic_tac_toe.controller;

import com.lila.tic_tac_toe.model.Player;
import com.lila.tic_tac_toe.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
public class PlayerControllerApi {

    @Autowired
    PlayerService playerService;

    @PostMapping("/register")
    public Player registerPlayer(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        return playerService.registerPlayer(name);
    }

    @GetMapping("/{playerId}")
    public Player getPlayer(@PathVariable String playerId) {
        return playerService.getPlayerById(playerId);
    }
}
