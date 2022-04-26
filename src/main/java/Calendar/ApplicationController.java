package Calendar;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicationController implements DateSelectionListener {

    private TimeManager timemanager;
    private Calendar calendar;
    private List<Pane> overlays;

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
    private AnchorPane overlayContainer;

    @FXML
    private Pane createEventMenu;
    @FXML
    private Pane createTodoMenu;

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
    private TextField todoTitleInput;
    @FXML
    private DatePicker todoDateInput;
    @FXML
    private TextField todoTimeInputH;
    @FXML
    private TextField todoTimeInputM;

    @FXML
    public void initialize() {
        this.timemanager = new TimeManager();
        this.calendar = new Calendar(this, "elements");
        this.overlays = Arrays.asList(createEventMenu, createTodoMenu);
        this.hideOverlays();
        loadCalendar();
        loadTodolist();
        this.overlayContainer.setVisible(false);
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
        String newCalendarTitle = String.format("%s %s", this.timemanager.monthToString(), String.valueOf(this.timemanager.getSelectedYear()));
        calendarTitle.setText(newCalendarTitle);
        calendarTitle.setTextAlignment(TextAlignment.CENTER);
        calendarGrid.setAlignment(Pos.CENTER);
        calendarGrid.getChildren().clear();
        for (int i = 0; i < timemanager.getMonthDays()+timemanager.getStartDayAdjustment()-1; i++) {
            int eventCount = 0;

            if (i+1 >= timemanager.getStartDayAdjustment() && i-timemanager.getStartDayAdjustment()<timemanager.getMonthDays()) {
                LocalDate cellDate = LocalDate.of(timemanager.getSelectedYear(), timemanager.getSelectedMonth(), i- timemanager.getStartDayAdjustment()+2);
                for (CalendarElement c: calendar.getCalendarElements()) {
                    if (c.getDateTime().toLocalDate().equals(cellDate)) eventCount++;
                }
                calendarGrid.add(new CalendarCell(cellDate, eventCount, this), i%7, i/7);
            }
        }
    }

    @FXML
    public void openCreateEventMenu() {
        this.setOverlay(createEventMenu);
    }

    @FXML
    public void openCreateTodoMenu() {
        this.setOverlay(createTodoMenu);
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
            Event newElement = new Event(startDateTime, title, duration, this.calendar);
            calendar.addCalendarElement(newElement);
        } catch (IllegalArgumentException e) {
            //Notify user of error
            e.printStackTrace();
        }

        this.hideOverlays();
    }

    @FXML
    public void createNewTodoClicked() {
        String title = todoTitleInput.getText();
        try {
            if (title.contains(",")) throw new IllegalArgumentException("Title cannot contain a ','!");

            LocalDateTime dateTime = getDateTimeFromInputs(todoDateInput, todoTimeInputH, todoTimeInputM);
            Todo newElement = new Todo(dateTime, title, this.calendar);

            calendar.addCalendarElement(newElement);
        } catch (IllegalArgumentException e) {
            //Notify user of error
            e.printStackTrace();
        }

        this.hideOverlays();
    }

    @FXML
    public void hideOverlays() {
        this.overlayContainer.setVisible(false);
        mainPage.setDisable(false);
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
    private void setOverlay(Pane pane) {
        if (this.overlays.contains(pane)) {
            mainPage.setDisable(true);
            this.overlayContainer.setVisible(true);
        } else return;

        for (Pane p: this.overlays) {
            try {
                p.setVisible(p == pane);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dateCellSelected(CalendarCell selectedCell) {
        for(Node node : calendarGrid.getChildren()) {
            if (node.getClass().equals(CalendarCell.class)) {
                CalendarCell cell = (CalendarCell) node;
                if (!cell.equals(selectedCell)) {
                    cell.deselect();
                }
            }
        }

        /**
            Filtering out all elements that do not have the desired date,
            returning the elements that are registered at the selected date
         */
        List<CalendarElement> selectedElements = this.calendar.getCalendarElements().stream().filter(
                element -> element.getDateTime().toLocalDate().equals(selectedCell.getDate())
        ).collect(Collectors.toList());
    }
}
