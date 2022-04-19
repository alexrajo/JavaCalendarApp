package Calendar;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ApplicationController implements CategoryListener {

    private int calendarUpdateInterval = 3600; //Seconds
    private TimerTask timerTask;
    private Timer timer;

    private List<Category> categories = Arrays.asList(new Category("Default", this));
    private LocalDate currentDate;
    private LocalDate initialDate;
    private int monthOffset;
    private AnchorPane currentPage;

    @FXML
    public Button prevBtn;

    @FXML
    public Button nextBtn;

    @FXML
    private Text calendarTitle;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private AnchorPane mainPage;

    @FXML
    public void initialize() {
        this.currentDate = LocalDate.now();
        this.initialDate = this.currentDate;
        this.monthOffset = 0;

        this.setCurrentPage(mainPage);
        loadCalendar();

        //Using a timer with a timertask to update our calendar with an interval specified above
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                loadCalendar();
            }
        };
        this.timer = new Timer();
        this.timer.schedule(this.timerTask, this.calendarUpdateInterval*1000);
    }

    @FXML
    public void changeMonthPrev(){
        this.monthOffset--;
        loadCalendar();
    }

    @FXML
    public void changeMonthNext(){
        this.monthOffset++;
        loadCalendar();
    }

    private static boolean isLeapYear(int year) {
        return (year-1752)%4 == 0;
    }

    @FXML
    public void loadCalendar() {
        this.currentDate = LocalDate.now();

        LocalDate selectedDate = this.initialDate.minusMonths(-this.monthOffset);
        int selectedYear = selectedDate.getYear();
        Month selectedMonth = selectedDate.getMonth();
        int monthDays = selectedMonth.length(isLeapYear(selectedYear));
        int currentMonthDay = selectedDate.getDayOfMonth();

        String newCalendarTitle = String.format("%s %s", selectedMonth.toString(), String.valueOf(selectedYear));
        calendarTitle.setText(newCalendarTitle);

        calendarGrid.setAlignment(Pos.CENTER);
        calendarGrid.getChildren().clear();
        for (int i = 0; i < monthDays; i++) {
            boolean isToday = LocalDate.of(selectedYear, selectedMonth, i+1).equals(this.currentDate);
            calendarGrid.add(createCalendarElement(i+1, isToday), i%7, i/7);
        }
    }

    @FXML
    public Pane createCalendarElement(int index, boolean isToday) {
        Pane element = new Pane();
        element.setScaleX(1);
        element.setScaleY(1);

        if (isToday) {
            element.setStyle("-fx-background-color: #87FFAD");
        } else if (index%2==0) {
            element.setStyle("-fx-background-color: #EEEEEE");
        }

        Text title = new Text(String.valueOf(index));
        title.setLayoutY(15);
        title.setLayoutX(5);
        element.getChildren().add(title);

        element.getStyleClass().add("cell");
        return element;
    }

    public void setCurrentPage(AnchorPane page) {
        //Need to loop through and disable other pages
        this.currentPage = page;
    }

    public AnchorPane getCurrentPage(){
        return this.currentPage;
    }

    @Override
    public void visibilityChanged(Category category, boolean visible) {

    }

    @Override
    public void elementAdded(Category category, CalendarElement element) {

    }

    @Override
    public void elementRemoved(Category category, CalendarElement element) {

    }

    @Override
    public void elementChanged(Category category, CalendarElement element) {

    }
}
