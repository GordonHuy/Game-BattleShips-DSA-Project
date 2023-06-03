package Version_5;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BattleShip extends Application {

    private GameBoard playerBoard;
    private GameBoard opponentBoard;

    private GridPane playerBoardPane;
    private GridPane opponentBoardPane;

    private Label playerLabel;
    private Label opponentLabel;
    private Label messageLabel;

    private TextField rowInput;
    private TextField colInput;
    private Button fireButton;
    private Button resetButton;

    private int boardSize = 10;
    private int numShips = 5;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        playerBoard = new GameBoard(boardSize);
        opponentBoard = new GameBoard(boardSize);

        createPlayerBoardPane();
        createOpponentBoardPane();

        playerLabel = new Label("Player Board");
        opponentLabel = new Label("Opponent Board");
        messageLabel = new Label();

        rowInput = new TextField();
        colInput = new TextField();
        fireButton = new Button("Fire");
        resetButton = new Button("Reset");

        fireButton.setOnAction(e -> handleFireButton());
        resetButton.setOnAction(e -> handleResetButton());

        HBox inputBox = new HBox(10, new Label("Row:"), rowInput, new Label("Column:"), colInput, fireButton, resetButton);
        inputBox.setAlignment(Pos.CENTER);

        VBox gamePane = new VBox(10, playerLabel, playerBoardPane, opponentLabel, opponentBoardPane, messageLabel, inputBox);
        gamePane.setAlignment(Pos.CENTER);
        gamePane.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane(gamePane);
        borderPane.setPadding(new Insets(10));

        Scene scene = new Scene(borderPane);
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.show();

        initializeGame();
    }

    private void initializeGame() {
        int gridSize = 10;
        playerBoard = new GameBoard(gridSize);
        opponentBoard = new GameBoard(gridSize);

        for (int i = 0; i < numShips; i++) {
            int size = 3; // Size of each ship
            int[][] location = generateShipLocation(size);
            Ship ship = new Ship("Ship " + (i + 1), size, location);
            playerBoard.addShip(ship);
        }
    }

    private void createPlayerBoardPane() {
        playerBoardPane = new GridPane();
        playerBoardPane.setGridLinesVisible(true);
        playerBoardPane.setPrefSize(300, 300);

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Button button = new Button();
                button.setMinSize(30, 30);
                int finalRow = row;
                int finalCol = col;
                button.setOnAction(e -> handlePlayerBoardClick(finalRow, finalCol));
                playerBoardPane.add(button, col, row);
            }
        }
    }

    private void createOpponentBoardPane() {
        opponentBoardPane = new GridPane();
        opponentBoardPane.setGridLinesVisible(true);
        opponentBoardPane.setPrefSize(300, 300);

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Button button = new Button();
                button.setMinSize(30, 30);
                int finalRow = row;
                int finalCol = col;
                button.setOnAction(e -> handleOpponentBoardClick(finalRow, finalCol));
                opponentBoardPane.add(button, col, row);
            }
        }
    }

    private int[][] generateShipLocation(int size) {
        int[][] location = new int[size][2];
        int row = (int) (Math.random() * boardSize);
        int col = (int) (Math.random() * boardSize);

        boolean horizontal = Math.random() < 0.5;
        for (int i = 0; i < size; i++) {
            location[i][0] = row;
            location[i][1] = col;

            if (horizontal) {
                col++;
            } else {
                row++;
            }
        }
        return location;
    }

    private void handleFireButton() {
        int row = Integer.parseInt(rowInput.getText());
        int col = Integer.parseInt(colInput.getText());

        boolean hit = opponentBoard.makeGuess(row, col);
        if (hit) {
            Button button = getButton(opponentBoardPane, row, col);
            button.setStyle("-fx-background-color: red;");
        } else {
            Button button = getButton(opponentBoardPane, row, col);
            button.setStyle("-fx-background-color: blue;");
        }

        if (opponentBoard.isGameOver()) {
            displayAlert("Game Over", "You Win!");
            disableGame();
        } else {
            playOpponentTurn();
        }
    }

    private void handleResetButton() {
        playerBoard.clearBoard();
        opponentBoard.clearBoard();
        clearBoard(playerBoardPane);
        clearBoard(opponentBoardPane);
        messageLabel.setText("");
        enableGame();
        initializeGame();
    }

    private void handlePlayerBoardClick(int row, int col) {
        if (playerBoard.isCellAlreadyGuessed(row, col)) {
            displayAlert("Invalid Guess", "You have already guessed this cell!");
            return;
        }

        boolean hit = playerBoard.makeGuess(row, col);
        if (hit) {
            Button button = getButton(playerBoardPane, row, col);
            button.setStyle("-fx-background-color: red;");
        } else {
            Button button = getButton(playerBoardPane, row, col);
            button.setStyle("-fx-background-color: blue;");
        }

        if (playerBoard.areAllShipsSunk()) {
            displayAlert("Game Over", "You Lose!");
            disableGame();
        }
    }

    private void handleOpponentBoardClick(int row, int col) {
        displayAlert("Invalid Guess", "It's not your turn yet!");
    }

    private void playOpponentTurn() {
        int row = (int) (Math.random() * boardSize);
        int col = (int) (Math.random() * boardSize);

        while (playerBoard.isCellAlreadyGuessed(row, col)) {
            row = (int) (Math.random() * boardSize);
            col = (int) (Math.random() * boardSize);
        }

        boolean hit = playerBoard.makeGuess(row, col);
        if (hit) {
            Button button = getButton(playerBoardPane, row, col);
            button.setStyle("-fx-background-color: red;");
        } else {
            Button button = getButton(playerBoardPane, row, col);
            button.setStyle("-fx-background-color: blue;");
        }

        if (playerBoard.areAllShipsSunk()) {
            displayAlert("Game Over", "You Lose!");
            disableGame();
        }
    }

    private void displayAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void disableGame() {
        fireButton.setDisable(true);
        resetButton.setDisable(false);
    }

    private void enableGame() {
        fireButton.setDisable(false);
        resetButton.setDisable(true);
    }

    private Button getButton(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Button) node;
            }
        }
        return null;
    }

    private void clearBoard(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            ((Button) node).setStyle("");
        }
    }
}
