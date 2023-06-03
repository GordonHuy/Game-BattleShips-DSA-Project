package Version_5;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends StackPane {
    private static final double CELL_SIZE = 30.0; // Change the size as desired
    private int row;
    private int col;
    private boolean hit;
    private boolean shipPresent;
    private Rectangle cellRectangle;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.hit = false;
        this.shipPresent = false;

        cellRectangle = new Rectangle(CELL_SIZE, CELL_SIZE);
        cellRectangle.setStroke(Color.BLACK);
        cellRectangle.setFill(Color.LIGHTGRAY);

        getChildren().add(cellRectangle);
        setPrefSize(CELL_SIZE, CELL_SIZE);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isShipPresent() {
        return shipPresent;
    }

    public void setShipPresent(boolean shipPresent) {
        this.shipPresent = shipPresent;
    }

    public void showHit() {
        hit = true;
        cellRectangle.setFill(Color.RED);
    }

    public void showMiss() {
        hit = true;
        cellRectangle.setFill(Color.BLUE);
    }
}
