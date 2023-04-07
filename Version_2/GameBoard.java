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

    public void addShip(Ships ship){
        for(int[] location : ship.getLocation()){
            int row = location[0];
            int column = location[1];
            if(row < 0 || row >= size || column < 0 || column >= size){
                throw new IllegalArgumentException("Invialid location");
            }
            if(board[row][column] != 0){
                throw new IllegalArgumentException("Occupied location");
            }
            board[row][column] = ships.size() + 1;
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
}
