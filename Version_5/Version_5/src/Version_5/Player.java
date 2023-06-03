package Version_5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private String name;
    private GameBoard gameBoard;
    private GameBoard opponentBoard;
    private List<Ship> ships;
    private Ship selectedShip;
    private Ship lastPlacedShip;

    public Player(String name, int size) {
        this.name = name;
        this.gameBoard = new GameBoard(size);
        this.opponentBoard = new GameBoard(size);
        this.ships = new ArrayList<>();
        this.selectedShip = null;
    }

    public Ship getLastPlacedShip() {
        return lastPlacedShip;
    }

    public String getName() {
        return name;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public GameBoard getOpponentBoard() {
        return opponentBoard;
    }

    public boolean isHit(int row, int column) {
        int[][] board = opponentBoard.getBoard();
        if (board[row][column] != 0) {
            int shipNumber = board[row][column];
            board[row][column] = -1;
            for (Ship ship : ships) {
                if (ship.getHits() == shipNumber) {
                    ship.hit();
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public void placeShips(List<Ship> ships) {
        placeShipsRandomly(ships, gameBoard);
    }

    public void placeShipsRandomly(List<Ship> ships, GameBoard gameBoard) {
        for (Ship ship : ships) {
            boolean isValidLocation = false;
            while (!isValidLocation) {
                int[][] locations = new int[ship.getSize()][2];
                if (Math.random() < 0.5) { // horizontal placement
                    int row = (int) (Math.random() * gameBoard.getSize());
                    int column = (int) (Math.random() * (gameBoard.getSize() - ship.getSize() + 1));
                    for (int i = 0; i < ship.getSize(); i++) {
                        locations[i][0] = row;
                        locations[i][1] = column + i;
                    }
                } else { // vertical placement
                    int row = (int) (Math.random() * (gameBoard.getSize() - ship.getSize() + 1));
                    int column = (int) (Math.random() * gameBoard.getSize());
                    for (int i = 0; i < ship.getSize(); i++) {
                        locations[i][0] = row + i;
                        locations[i][1] = column;
                    }
                }
                ship.setLocation(locations);
                try {
                    gameBoard.addShip(ship);
                    isValidLocation = true;
                } catch (IllegalArgumentException e) {
                    // do nothing, try again
                }

                lastPlacedShip = ship;
            }
        }
    }

    public boolean hasPlacedAllShips() {
        return ships.size() == gameBoard.getShips().size();
    }

    public Ship getSelectedShip() {
        return selectedShip;
    }

    public void setSelectedShip(Ship selectedShip) {
        this.selectedShip = selectedShip;
    }

    public void placeShips(Ship selectedShip, int row, int col) {
        int[][] locations = new int[selectedShip.getSize()][2];
        if (selectedShip.isHorizontal()) {
            for (int i = 0; i < selectedShip.getSize(); i++) {
                locations[i][0] = row;
                locations[i][1] = col + i;
            }
        } else {
            for (int i = 0; i < selectedShip.getSize(); i++) {
                locations[i][0] = row + i;
                locations[i][1] = col;
            }
        }

        if (gameBoard.isValidLocation(locations)) {
            selectedShip.setLocation(locations);
            gameBoard.addShip(selectedShip);
        } else {
            // Handle invalid ship placement
            System.out.println("Invalid ship placement!");
        }
    }

    public int[] makeGuess() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the row number (0-9): ");
        int row = scanner.nextInt();

        System.out.print("Enter the column number (0-9): ");
        int column = scanner.nextInt();

        return new int[]{row, column};
    }

}
