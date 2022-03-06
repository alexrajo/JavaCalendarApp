package Calendar;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApplicationController {
    private Calendar calendar;

    @FXML
    private GridPane CalGrid;

    @FXML
    public void initialize() {
        for (int i = 7; i < 37; i++) {
            CalGrid.add(createCalElem(i-6), i % 7, i / 7);
        }
    }

    @FXML
    public Text createCalElem(int i) {
        Text text = new Text(String.valueOf(i));
        text.setX(50);
        text.setY(50);
        return text;
    }
}
