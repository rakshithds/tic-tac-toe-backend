package com.lila.tic_tac_toe.model;

import java.util.UUID;

public class Game {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerXId() {
        return playerXId;
    }

    public void setPlayerXId(String playerXId) {
        this.playerXId = playerXId;
    }

    public String getPlayerOId() {
        return playerOId;
    }

    public void setPlayerOId(String playerOId) {
        this.playerOId = playerOId;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }

    private String id;
    private String playerXId;
    private String playerOId;
    private String[][] board;
    private String currentPlayerId;
    private StatusEnum status;
    private String winnerId;

    public Game() {
        this.id = UUID.randomUUID().toString();
        this.board = new String[3][3];
        this.status = StatusEnum.WAITING;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
    }

    public void startGame() {
        this.currentPlayerId = playerXId;
        this.status = StatusEnum.IN_PROGRESS;
    }

    public boolean makeMove(int row, int col, String playerId) {
        if (status != StatusEnum.IN_PROGRESS ||
                !currentPlayerId.equals(playerId) ||
                row < 0 || row > 2 || col < 0 || col > 2 ||
                !board[row][col].isEmpty()) {
            return false;
        }

        String symbol = playerId.equals(playerXId) ? "X" : "O";
        board[row][col] = symbol;

        if (checkWinner(symbol)) {
            this.winnerId = playerId;
            this.status = StatusEnum.FINISHED;
        } else if (isBoardFull()) {
            this.winnerId = "DRAW";
            this.status = StatusEnum.FINISHED;
        } else {
            switchPlayer();
        }

        return true;
    }

    private void switchPlayer() {
        currentPlayerId = currentPlayerId.equals(playerXId) ? playerOId : playerXId;
    }

    private boolean checkWinner(String symbol) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(symbol) && board[i][1].equals(symbol) && board[i][2].equals(symbol)) {
                return true;
            }
            if (board[0][i].equals(symbol) && board[1][i].equals(symbol) && board[2][i].equals(symbol)) {
                return true;
            }
        }

        if (board[0][0].equals(symbol) && board[1][1].equals(symbol) && board[2][2].equals(symbol)) {
            return true;
        }
        if (board[0][2].equals(symbol) && board[1][1].equals(symbol) && board[2][0].equals(symbol)) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
