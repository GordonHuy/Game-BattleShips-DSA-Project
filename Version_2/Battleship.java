package Version_2;

import java.util.Scanner;

public class Battleship {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Battleship!");

        // initialize the game board and add ships
        GameBoard gameBoard = new GameBoard(20);
        gameBoard.addShip(new Ships("Destroyer", 3, new int[][]{{0, 0}, {0, 1}, {0, 2}}));
        gameBoard.addShip(new Ships("Cruiser", 4, new int[][]{{2, 2}, {2, 3}, {2, 4}, {2, 5}}));
        gameBoard.addShip(new Ships("Carrier", 5, new int[][]{{5, 3}, {6, 3}, {7, 3}, {8, 3}, {9, 3}}));
        
        int[][] board = gameBoard.getBoard();
        while (!gameBoard.isGameOver()) {
            // ask the player for their move
            System.out.print("Enter row (0-7): ");
            int row = scanner.nextInt();
            System.out.print("Enter column (0-7): ");
            int column = scanner.nextInt();
            int[] move = {row, column};

            // update the game board based on the player's move
            if (board[move[0]][move[1]] == 0) {
                System.out.println("Miss!");
                board[move[0]][move[1]] = -1;
            } else {
                int shipNumber = board[move[0]][move[1]] - 1;
                Ships ship = gameBoard.getShips().get(shipNumber);
                ship.hit();
                board[move[0]][move[1]] = -2;
                if (ship.isSunk()) {
                    System.out.println("You sunk my " + ship.getType() + "!");
                } else {
                    System.out.println("Hit!");
                }
            }

            // print the current state of the game board
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == 0) {
                        System.out.print("_ ");
                    } else if (board[i][j] == -1) {
                        System.out.print("O ");
                    } else if (board[i][j] == -2) {
                        System.out.print("X ");
                    } else {
                        System.out.print("_ ");
                    }
                }
                System.out.println();
            }
        }

        System.out.println("Congratulations! You won!");
    }
}
