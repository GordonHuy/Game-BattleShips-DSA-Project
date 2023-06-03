package Version_5;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private int[][] board;
    private int size;
    private List<Ship> ships;
    private boolean[][] guesses;

    public GameBoard(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.ships = new ArrayList<>();
        this.guesses = new boolean[size][size];
    }

    public int[][] getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void addShip(Ship ship) {
        int[][] location = ship.getLocation();
        for (int[] coord : location) {
            int row = coord[0];
            int col = coord[1];
            if (board[row][col] != 0) {
                throw new IllegalArgumentException("Invalid ship location");
            }
            board[row][col] = ship.getNumber();
        }
        ships.add(ship);
    }


    public boolean isGameOver() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidLocation(int[][] location) {
        for (int[] coord : location) {
            int row = coord[0];
            int col = coord[1];
            if (row < 0 || row >= size || col < 0 || col >= size || board[row][col] != 0) {
                return false;
            }
        }
        return true;
    }


    public boolean makeGuess(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            displayAlert("Invalid Guess", "Invalid coordinates! Please try again.");
            return false;
        }

        int cellValue = board[row][col];
        if (cellValue != 0) {
            Ship ship = ships.get(cellValue - 1);
            ship.hit();
            board[row][col] = -1; // Mark the cell as hit
            displayAlert("Hit!", "You hit a ship!");

            if (ship.isSunk()) {
                displayAlert("Ship Sunk!", "You sunk the " + ship.getName() + "!");
            }
            return true;
        } else {
            board[row][col] = -1; // Mark the cell as missed
            displayAlert("Miss!", "You missed the target!");
            return false;
        }
    }

    public boolean isCellAlreadyGuessed(int row, int col) {
        return guesses[row][col];
    }

    public void clearBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = 0;
                guesses[i][j] = false;
            }
        }
        ships.clear();
    }

    public boolean areAllShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    private void displayAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
