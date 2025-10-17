package com.lila.tic_tac_toe.controller;

import com.lila.tic_tac_toe.handler.GameWebSocketHandler;
import com.lila.tic_tac_toe.model.Game;
import com.lila.tic_tac_toe.model.MoveRequest;
import com.lila.tic_tac_toe.repository.GameRepository;
import com.lila.tic_tac_toe.service.GameService;
import com.lila.tic_tac_toe.service.MatchMackingService;
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
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameControllerApi {

    @Autowired
    MatchMackingService matchmakingService;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameService gameService;

    @Autowired
    GameWebSocketHandler gameWebSocketHandler;

    @PostMapping("/matchmaking")
    public Map<String, Object> findMatch(@RequestBody Map<String, Object> request) {
        String playerId = (String) request.get("playerId");
        Game game = matchmakingService.findOrCreateMatch(playerId);

        gameRepository.save(game);

        return Map.of(
                "gameId", game.getId(),
                "status", game.getStatus().toString()
        );
    }

    @PostMapping("/{gameId}/move")
    public Game makeMove(@PathVariable String gameId, @RequestBody MoveRequest moveRequest) {
        Game game = gameService.makeMove(gameId, moveRequest.getPlayerId(), moveRequest.getRow(), moveRequest.getCol());
        gameWebSocketHandler.broadcastGameState(gameId);
        return game;
    }

    @GetMapping("/{gameId}")
    public Game getGame(@PathVariable String gameId) {
        return gameService.getGameById(gameId);
    }
}
