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
    private TimeManager timemanager;
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
        this.timemanager = new TimeManager();
        this.calendar = new Calendar(this, "elements");
        this.overlays = Arrays.asList(createMenu);
        this.hideOverlays();
        loadCalendar();
        loadTodolist();
        this.mainPage.setVisible(true);
    }

    @FXML
    public void loadTodolist(){
        this.todoList.getItems().clear();
        for (CalendarElement element: calendar.getCalendarElements()) {
            if (element.getClass().equals(Todo.class)){
                this.todoList.getItems().add(ElementCreator.createCheckbox(element));
            }
        }
    }

    @FXML
    public void changeMonthPrev(){
        this.timemanager.changeMonthPrev();
        loadCalendar();
    }

    @FXML
    public void changeMonthNext(){
        this.timemanager.changeMonthNext();
        loadCalendar();
    }

    @FXML
    public void loadCalendar() {
        System.out.println(timemanager.getSelectedMonth());
        String newCalendarTitle = String.format("%s %s", this.timemanager.monthToString(), String.valueOf(this.timemanager.getSelectedYear()));
        calendarTitle.setText(newCalendarTitle);
        calendarTitle.setTextAlignment(TextAlignment.CENTER);
        calendarGrid.setAlignment(Pos.CENTER);
        calendarGrid.getChildren().clear();
        for (int i = 1; i < timemanager.getMonthDays()+timemanager.getStartDayAdjustment()-1; i++) {
            List<String> tempList = new ArrayList<String>();
            if (i-timemanager.getStartDayAdjustment() >= -1 && i-timemanager.getStartDayAdjustment()<timemanager.getMonthDays()) {
                for (CalendarElement c: calendar.getCalendarElements()) {
                    if (c.getDateTime().toLocalDate().equals(LocalDate.of(timemanager.getSelectedYear(), timemanager.getSelectedMonth(), i- timemanager.getStartDayAdjustment()+2))) {
                        tempList.add(c.getTitle());
                    }
                }
                calendarGrid.add(createCalendarElement(i+1, timemanager.isToday(i- timemanager.getStartDayAdjustment()+2), timemanager.getStartDayAdjustment(), tempList), i%7, i/7);
            }
        }
    }

    @FXML
    public Pane createCalendarElement(int index, boolean isToday, int startDayAdjustment, List<String> tempList) {
        Pane element = ElementCreator.gridPane(isToday, index);
        element.getChildren().add(ElementCreator.gridTitle(index, startDayAdjustment));
        if (tempList.size() > 0) {
            element.getChildren().add(ElementCreator.gridInfo(tempList));
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
