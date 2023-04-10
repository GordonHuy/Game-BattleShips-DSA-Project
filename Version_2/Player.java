package Version_2;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private GameBoard gameBoard;
    private GameBoard opponentBoard;

    public Player(String name, int size){
        this.name = name;
        this.gameBoard = new GameBoard(size);
        this.opponentBoard = new GameBoard(size);
    }

    public String getName(){
        return name;
    }

    public GameBoard getGameBoard(){
        return gameBoard;
    }

    public GameBoard getOpponentBoard(){
        return opponentBoard;
    }

    public boolean isHit(int row, int column){
        int[][] board = opponentBoard.getBoard();
        if(board[row][column] !=0){
            int shipNumber = board[row][column];
            board[row][column] = -1;
            List<Ships> ships = opponentBoard.getShips();
            for (Ships ship : ships) {
                if (ship.getHits() == shipNumber) {
                    ship.hit();
                    break;
                }
            }
            return true;     
        }
        return false;
    }

    public void placeShips(List<Ships> ships){
        for(Ships ship: ships){
            boolean isValidLocation = false;
            while(!isValidLocation){
                int[][] locations = new int[ship.getSize()][2];
                if(Math.random() < 0.5) { // horizontal placement
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
                try{
                    gameBoard.addShip(ship);
                    isValidLocation = true;
                } catch(IllegalArgumentException e){
                // do nothing, try again
                }
            }
        }
    }

    public int[] getNextMove(){
        int [] move = new int[2];
        int[][] board = opponentBoard.getBoard();
        //Statergy for Computer
        if(name.equals("Computer")){
            int maxHits = -1;
            List<Ships> sunkShips = new ArrayList<>();
            for(Ships ship : opponentBoard.getShips()){
                if(ship.isSunk()){
                    sunkShips.add(ship);
                } else {
                    maxHits = Math.max(maxHits, ship.getNumHits());
                }
            }
            // if all opponent ships have been sunk, make a random move
            if(sunkShips.size() == opponentBoard.getShips().size()){
                move[0] = (int) (Math.random() * gameBoard.getSize());
                move[1] = (int) (Math.random() * gameBoard.getSize());
            } else {
                List<int[]> possibleMoves = new ArrayList<>();
                if (sunkShips.isEmpty()) {
                    //make a random move if no hits so far
                    for (int row = 0; row < board.length; row++){
                        for(int column = 0; column < board[row].length; column++){
                            if(board[row][column] == 0){
                                possibleMoves.add(new int[]{row, column});
                            }
                        }
                    }
                } else {
                    // target the surrounding squares of the ship that has been hit the most
                    for(Ships ship : opponentBoard.getShips()){
                        if(ship.getNumHits() == maxHits){
                            int row = ship.getLocation()[0][0];
                            int column = ship.getLocation()[0][1];
                            if(row > 0 && board[row - 1][column] == 0){
                                possibleMoves.add(new int[]{row - 1, column});
                            }
                        }
                    }
                }
            }
        }
    }
}


