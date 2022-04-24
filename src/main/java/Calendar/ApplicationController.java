package Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ApplicationController {

    private int calendarUpdateInterval = 3600; //Seconds
    private TimerTask timerTask;
    private Timer timer;

    private LocalDate currentDate;
    private LocalDate initialDate;
    private int monthOffset;
    private Calendar calendar;
    private List<AnchorPane> overlays;

    @FXML
    private Button prevBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private ListView todoList;

    @FXML
    private Text calendarTitle;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private AnchorPane mainPage;

    @FXML
    private AnchorPane createMenu;

    @FXML
    private TextField eventTitleInput;

    @FXML
    private DatePicker eventStartDateInput;
    @FXML
    private TextField eventStartTimeInputH;
    @FXML
    private TextField eventStartTimeInputM;

    @FXML
    private DatePicker eventEndDateInput;
    @FXML
    private TextField eventEndTimeInputH;
    @FXML
    private TextField eventEndTimeInputM;

    @FXML
    public void initialize() {
        this.currentDate = LocalDate.now();
        this.initialDate = this.currentDate;
        this.monthOffset = 0;
        this.calendar = new Calendar(this, "elements");
        this.overlays = Arrays.asList(createMenu);
        this.hideOverlays();
        loadCalendar();
        this.mainPage.setVisible(true);
        loadTodolist();

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
    public void loadTodolist(){
        this.todoList.getItems().clear();

        for (CalendarElement element: calendar.getCalendarElements()) {
            if (element.getClass().equals(Todo.class)){
                Todo todo = (Todo) element;

                CheckBox checkBox = new CheckBox(element.getTitle());
                checkBox.setId(element.getTitle());
                checkBox.setSelected(todo.isCompleted());

                checkBox.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        todo.setCompleted(checkBox.isSelected());
                    }
                });

                this.todoList.getItems().add(checkBox);
            }
        }
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

    @FXML
    public void loadCalendar() {
        this.currentDate = LocalDate.now();
        LocalDate selectedDate = this.initialDate.minusMonths(-this.monthOffset);
        int selectedYear = selectedDate.getYear();
        Month selectedMonth = selectedDate.getMonth();
        int monthDays = selectedMonth.length(Calendar.isLeapYear(selectedYear));
        int currentMonthDay = selectedDate.getDayOfMonth();
        String newCalendarTitle = String.format("%s %s", selectedMonth.toString(), String.valueOf(selectedYear));
        calendarTitle.setText(newCalendarTitle);
        calendarTitle.setTextAlignment(TextAlignment.CENTER);

        int startDayAdjustment = LocalDate.of(selectedYear, selectedMonth.getValue(), 1).getDayOfWeek().getValue();
        calendarGrid.setAlignment(Pos.CENTER);
        calendarGrid.getChildren().clear();

        for (int i = 0; i < monthDays+startDayAdjustment-1; i++) {
            List<String> tempList = new ArrayList<String>();
            boolean isToday = false;
            if (i-startDayAdjustment >= -1 && i-startDayAdjustment<monthDays) {
                isToday = LocalDate.of(selectedYear, selectedMonth, i-startDayAdjustment+2).equals(this.currentDate);
                for (CalendarElement c: calendar.getCalendarElements()) {
                    if (c.getDateTime().toLocalDate().equals(LocalDate.of(selectedYear, selectedMonth.getValue(), i-startDayAdjustment+2))) {
                        tempList.add(c.getTitle());
                    }
                }
                calendarGrid.add(createCalendarElement(i+1, isToday, startDayAdjustment, tempList), i%7, i/7);
            }
        }
    }

    @FXML
    public Pane createCalendarElement(int index, boolean isToday, int startDayAdjustment, List<String> tempList) {
        Pane element = new Pane();
        element.setScaleX(1);
        element.setScaleY(1);
        if (isToday) {
            element.setStyle("-fx-background-color: #87FFAD");
        } else if (index%2==0) {
            element.setStyle("-fx-background-color: #EEEEEE");
        }
        String titleVal = index < startDayAdjustment ? "" : String.valueOf(index-startDayAdjustment+1);
        //Text title = new Text(String.valueOf(index));
        Text title = new Text(titleVal);
        title.setLayoutY(15);
        title.setLayoutX(5);
        element.getChildren().add(title);
        Text info = new Text(String.valueOf(tempList.size())+" events");
        info.setLayoutY(40);
        info.setLayoutX(20);
        if (tempList.size() > 0) {
            element.getChildren().add(info);
        }
        element.getStyleClass().add("cell");
        return element;
    }

    @FXML
    public void openCreateMenu() {
        this.showOverlay(createMenu);
    }

    @FXML
    public void createNewEventClicked() {
        String title = eventTitleInput.getText();

        try {
            if (title.contains(",")) throw new IllegalArgumentException("Title cannot contain a ','!");

            LocalDateTime startDateTime = getDateTimeFromInputs(eventStartDateInput, eventStartTimeInputH, eventStartTimeInputM);
            LocalDateTime endDateTime = getDateTimeFromInputs(eventEndDateInput, eventEndTimeInputH, eventEndTimeInputM);

            if (endDateTime.isBefore(startDateTime)) throw new IllegalArgumentException("Start date must be before end date!");

            int duration = (int) startDateTime.until(endDateTime, ChronoUnit.MINUTES);
            Event newEvent = new Event(startDateTime, title, duration, this.calendar);
            calendar.createCalendarElement(newEvent);
        } catch (IllegalArgumentException e) {
            //Notify user of error
            e.printStackTrace();
        }

        this.hideOverlays();
    }

    @FXML
    public void hideOverlays() {
        mainPage.setDisable(false);
        for (AnchorPane p: this.overlays) {
            try {
                p.setVisible(false);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


    private static LocalDateTime getDateTimeFromInputs(DatePicker datePicker, TextField hour, TextField minute) throws IllegalArgumentException {
        int h = Integer.parseInt(hour.getText());
        int m = Integer.parseInt(minute.getText());

        if (Double.isNaN(h) || Double.isNaN(m)) {
            throw new IllegalArgumentException("Minutes and hours should be represented with integers only!");
        } else if (h < 0 || m < 0 || h > 24 || m > 59 || (h > 23 && m > 0)) {
            throw new IllegalArgumentException("Invalid time input!");
        }

        return LocalDateTime.of(datePicker.getValue(), LocalTime.of(h, m));
    }

    // Kan eventuelt bruke lambda-uttryk for å stykke opp kode mer slik at man unngår repeterende kode
    private void showOverlay(AnchorPane pane) {
        if (this.overlays.contains(pane)) {
            mainPage.setDisable(true);
        }
        for (AnchorPane p: this.overlays) {
            try {
                p.setVisible(p == pane);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

}
