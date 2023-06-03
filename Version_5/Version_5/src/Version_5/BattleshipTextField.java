package Version_5;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BattleshipTextField extends TextField {

    public BattleshipTextField() {
        super();
        initialize();
    }

    public BattleshipTextField(String text) {
        super(text);
        initialize();
    }

    private void initialize() {
        setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        setPrefHeight(30);
        setAlignment(Pos.CENTER);
    }
}
