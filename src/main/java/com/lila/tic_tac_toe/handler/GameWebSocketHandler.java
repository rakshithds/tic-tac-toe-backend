package com.lila.tic_tac_toe.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lila.tic_tac_toe.model.Game;
import com.lila.tic_tac_toe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(GameWebSocketHandler.class);

    private final GameService gameService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, CopyOnWriteArraySet<WebSocketSession>> gameSessions = new ConcurrentHashMap<>();

    public GameWebSocketHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        String gameId = extractGameId(session);
        if (gameId != null) {
            gameSessions.computeIfAbsent(gameId, k -> new CopyOnWriteArraySet<>()).add(session);
        } else {
            logger.warn("Game ID could not be extracted from session URI");
        }
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {
        String gameId = extractGameId(session);
        if (gameId == null) {
            logger.warn("Game ID could not be extracted from session URI");
            return;
        }
        try {
            Map<String, Object> payload = objectMapper.readValue(message.getPayload(), objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
            if ("JOIN".equals(payload.get("type"))) {
                broadcastGameState(gameId);
            }
        } catch (Exception e) {
            logger.error("Error handling WebSocket message", e);
        }
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        String gameId = extractGameId(session);
        if (gameId == null) {
            logger.warn("Game ID could not be extracted from session URI");
            return;
        }
        CopyOnWriteArraySet<WebSocketSession> sessions = gameSessions.get(gameId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                gameSessions.remove(gameId);
            }
        }
    }

    private String extractGameId(WebSocketSession session) {
        if (session.getUri() != null) {
            String path = session.getUri().getPath();
            String[] parts = path.split("/");
            return parts.length > 2 ? parts[parts.length - 1] : null;
        }
        return null;
    }

    public void broadcastGameState(String gameId) {
        try {
            Game game = gameService.getGameById(gameId);
            CopyOnWriteArraySet<WebSocketSession> sessions = gameSessions.get(gameId);
            if (sessions != null) {
                Map<String, Object> response = Map.of(
                    "type", "GAME_STATE",
                    "game", game
                );
                String json = objectMapper.writeValueAsString(response);
                for (WebSocketSession session : sessions) {
                    session.sendMessage(new TextMessage(json));
                }
            }
        } catch (Exception e) {
            logger.error("Error broadcasting game state", e);
        }
    }
}
