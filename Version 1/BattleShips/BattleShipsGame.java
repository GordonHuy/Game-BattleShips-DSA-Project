import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BattleShipsGame{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Size of the game board: ");
        int gameBoardSize = scanner.nextInt();
        char water = '-';
        char ship = 's';
        char hit = 'X';
        char miss = '0';
        System.out.print("The number of ship(s): ");
        int shipNumber = scanner.nextInt();
    
        char[][] gameBoard = createGameBoard(gameBoardSize, water, ship, shipNumber);
        printGameBoard(gameBoard, water, ship);
        int undetectedShipsNumber = shipNumber;
        boolean userTurn = true;
        while (undetectedShipsNumber > 0 && undetectedShipsNumber < shipNumber * 2) {
            int[] guessCoordinates;
            if (userTurn) {
                guessCoordinates = getUserCoordinates(gameBoardSize);
            } else {
                guessCoordinates = generateAIGuess(gameBoardSize);
                System.out.println("The AI guesses: [" + (guessCoordinates[0]+1) + ", " + (guessCoordinates[1]+1) + "]");
            }
            char locationViewUpdate = evaluateGuessAndGetTheTarget(guessCoordinates, gameBoard, ship, water, hit, miss);
            if (locationViewUpdate == hit) {
                undetectedShipsNumber--;
            }
            gameBoard = updateGameBoard(gameBoard, guessCoordinates, locationViewUpdate);
            printGameBoard(gameBoard, water, ship);
            userTurn = !userTurn;
        }
        if (undetectedShipsNumber == 0) {
            System.out.print("You won!");
        } else {
            System.out.print("The AI player won!");
        }
    }
    
    private static char[][] updateGameBoard(char[][] gameBoard,int[] guessCoordinates,char locationViewUpdate) {
        int row = guessCoordinates[0];
        int column = guessCoordinates[1];
        gameBoard[row][column] = locationViewUpdate;
        return gameBoard;
    }



    private static char evaluateGuessAndGetTheTarget(int[] guessCoordinates, char[][] gameBoard, char ship, char water, char hit, char miss) {
        String message;
        int row = guessCoordinates[0];
        int column = guessCoordinates[1];
        char target = gameBoard[row][column];
        if(target == ship){
            message = "Hit!";
            target = hit;
        }
        else if (target == water){
            message = "Miss!";
            target = miss;
        }
        else {
            message = "Already hit!";
        }
        System.out.println(message);
        return target;
    }



    private static int[] getUserCoordinates(int gameBoardSize) {
        Scanner scanner = new Scanner(System.in);
        int row;
        int column;
        boolean validRow = false;
        boolean validColumn = false;
        do {
            System.out.print("Row: ");
            row = scanner.nextInt();
            if(row >= 1 && row <= gameBoardSize){
                validRow = true;
            }else{
                System.out.println("Invalid row. Please enter a number between 1 and " + gameBoardSize + ".");
            }
        } while (!validRow);
    
        do {
            System.out.print("Column: ");
            column = scanner.nextInt();
            if(column >= 1 && column <= gameBoardSize){
                validColumn = true;
            }else{
                System.out.println("Invalid column. Please enter a number between 1 and " + gameBoardSize + ".");
            }
        } while (!validColumn);
    
        return new int[]{row - 1, column - 1};
    }
    
    private static void printGameBoard(char[][] gameBoard, char water, char ship) {
        int gameBoardSize = gameBoard.length;
        System.out.print("  ");
        for(int i = 0; i < gameBoardSize; i ++){
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for(int row = 0; row < gameBoardSize; row ++){
            System.out.print(row + 1 + " ");
            for(int column = 0; column < gameBoardSize; column ++){
                char position = gameBoard[row][column];
                if(position == ship){
                    System.out.print(water + " ");
                }
                else{
                    System.out.print(position + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static char[][] createGameBoard(int gameBoardSize, char water, char ship, int shipNumber) {
        char[][] gameBoard = new char[gameBoardSize][gameBoardSize];
        for(char[] row : gameBoard){
            Arrays.fill(row, water);
        }
        return shipsPlacement(gameBoard, shipNumber, water, ship);
    }

    private static char[][] shipsPlacement(char[][] gameBoard, int shipNumber, char water, char ship) {
        int placedShips = 0;
        int gameBoardSize = gameBoard.length;
        while(placedShips <  shipNumber){
            int [] location = shipsCoordinatesGenerator(gameBoardSize);
            char possiblePlacements = gameBoard[location[0]][location[1]];
            if (possiblePlacements == water){
                gameBoard[location[0]][location[1]] = ship;
                placedShips ++;
            }
        }
        return gameBoard;
    }


    private static int[] shipsCoordinatesGenerator(int gameBoardSize){
        int [] coordinates = new int [2];
        for (int i = 0; i < coordinates.length; i++){
            coordinates[i] = new Random().nextInt(gameBoardSize);
        }
        return coordinates;
    }

    private static int[] generateAIGuess(int gameBoardSize) {
        Random random = new Random();
        int row = random.nextInt(gameBoardSize);
        int column = random.nextInt(gameBoardSize);
        return new int[]{row, column};
    }
}