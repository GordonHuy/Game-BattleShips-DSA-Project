package Version_2;

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

}
