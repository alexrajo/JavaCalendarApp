package Calendar;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.time.Month;

public class ApplicationController {
    @FXML
    public Button prevBtn;

    @FXML
    public Button nextBtn;

    private Calendar calendar = new Calendar("hei");

    @FXML
    private Text calTitle;

    @FXML
    private GridPane calendarGrid;

    @FXML
    public void initialize() {
        calendar.setSelectedMonth(0);
        loadCalendar();
    }

    @FXML
    public void changeMonthPrev(){
        calendar.setSelectedMonth(-1);
        calTitle.setText(calendar.getSelectedYear()+ " - " + Month.of(calendar.getSelectedMonth()));
        loadCalendar();
    }

    @FXML
    public void changeMonthNext(){
        calendar.setSelectedMonth(1);
        calTitle.setText(calendar.getSelectedYear()+ " - " + Month.of(calendar.getSelectedMonth()));
        loadCalendar();
    }

    @FXML
    public void loadCalendar() {
        int days = calendar.getDaysInMonth();
        calendarGrid.setAlignment(Pos.CENTER);
        calendarGrid.getChildren().clear();
        for (int i = 0; i < days; i++) {
            calendarGrid.add(createCalendarElement(i+1), i%7, i/7);
        }
    }

    @FXML
    public Pane createCalendarElement(int i) {
        Pane element = new Pane();
        element.setScaleX(1);
        element.setScaleY(1);

        if (i%2==0) {
            element.setStyle("-fx-background-color: #EEEEEE");
        }

        Text title = new Text(String.valueOf(i));
        title.setLayoutY(15);
        title.setLayoutX(5);
        element.getChildren().add(title);

        element.getStyleClass().add("cell");
        return element;
    }
}
