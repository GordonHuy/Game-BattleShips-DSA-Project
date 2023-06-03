package Version_2;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private int[][] board;
    private int size;
    private List<Ships> ships;

    public GameBoard(int size){
        this.size = size;
        this.board = new int[size][size];
        this.ships = new ArrayList<>();
    }

    public int[][] getBoard(){
        return board;
    }

    public int getSize(){
        return size;
    }

    public List<Ships> getShips(){
        return ships;
    }

    public void addShip(Ships ship) {
        int[][] locations = ship.getLocation();
        if (!isValidLocation(locations)) {
            throw new IllegalArgumentException("Invalid location");
        }
        for (int[] loc : locations) {
            int row = loc[0];
            int col = loc[1];
            board[row][col] = ships.size();
        }
        ships.add(ship);
    }
    
    
    public boolean isGameOver(){
        for(Ships ship : ships){
            if(!ship.isSunk()){
                return false;
            }
        }
        return true;
    }

    public boolean isValidLocation(int[][] locations) {
        for (int[] loc : locations) {
            int row = loc[0];
            int col = loc[1];
            if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
                return false;
            }
            if (board[row][col] != 0) {
                return false;
            }
        }
        return true;
    }
    
}
