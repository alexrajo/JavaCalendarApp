package Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.util.*;

public class ApplicationController implements DateSelectionListener, CalendarListener {

    private TimeManager timemanager;
    private Calendar calendar;
    private List<Pane> overlays;
    private List<CalendarElement> selectedElements;

    @FXML
    private CheckBox wholeDayEventCheckBox, repeatingEventCheckBox;

    @FXML
    private Button prevBtn, nextBtn;

    @FXML
    private ListView todoList, eventList;

    @FXML
    private Text calendarTitle, eventPickedDateLabel, exceptionOutputLabel;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private AnchorPane mainPage, overlayContainer;

    @FXML
    private Pane createEventMenu, createTodoMenu;

    @FXML
    private TextField eventTitleInput;

    @FXML
    private DatePicker eventStartDateInput, eventEndDateInput;
    @FXML
    private TextField eventStartTimeInputH, eventStartTimeInputM, eventEndTimeInputH, eventEndTimeInputM;
    @FXML
    private TextField eventOccurrencesInput, eventOccurrenceIntervalInput;

    @FXML
    private TextField todoTitleInput, todoTimeInputH, todoTimeInputM;
    @FXML
    private DatePicker todoDateInput;

    @FXML
    private void initialize() {
        this.timemanager = new TimeManager();
        this.calendar = new Calendar("elements", this);
        this.overlays = Arrays.asList(createEventMenu, createTodoMenu);
        this.hideOverlays();
        loadCalendar();
        loadTodolist();
        this.overlayContainer.setVisible(false);
        this.mainPage.setVisible(true);
        this.selectedElements = new ArrayList<CalendarElement>();

        wholeDayEventCheckBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setEventWholeDay(wholeDayEventCheckBox.isSelected());
            }
        });

        repeatingEventCheckBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setEventRepeating(repeatingEventCheckBox.isSelected());
            }
        });
    }

    @FXML
    private void loadTodolist(){
        this.todoList.getItems().clear();
        for (CalendarElement element: calendar.getCalendarElements()) {
            if (element instanceof Todo){
                this.todoList.getItems().add(ListItemCreator.createTodoListItem(element));
            }
        }
    }

    @FXML
    private void loadEventList(){
        this.eventList.getItems().clear();
        for (CalendarElement element: this.selectedElements) {
            if (element instanceof Event) {
                this.eventList.getItems().add(ListItemCreator.createEventListItem(element));
            }
        }
    }

    @FXML
    private void changeMonthPrev(){
        this.timemanager.changeMonthPrev();
        loadCalendar();
    }

    @FXML
    private void changeMonthNext(){
        this.timemanager.changeMonthNext();
        loadCalendar();
    }

    @FXML
    private void loadCalendar() {
        String newCalendarTitle = String.format("%s %s", this.timemanager.monthToString(), String.valueOf(this.timemanager.getSelectedYear()));
        calendarTitle.setText(newCalendarTitle);
        calendarTitle.setTextAlignment(TextAlignment.CENTER);
        calendarGrid.setAlignment(Pos.CENTER);
        calendarGrid.getChildren().clear();

        for (int i = 0; i < timemanager.getMonthDays()+timemanager.getStartDayAdjustment()-1; i++) {
            if (i+1 >= timemanager.getStartDayAdjustment() && i-timemanager.getStartDayAdjustment()<timemanager.getMonthDays()) {
                LocalDate cellDate = LocalDate.of(timemanager.getSelectedYear(), timemanager.getSelectedMonth(), i- timemanager.getStartDayAdjustment()+2);
                int eventCount = this.calendar.getEventsOnDate(cellDate).size();

                calendarGrid.add(new CalendarCell(cellDate, eventCount, this), i%7, i/7);
            }
        }
    }

    @FXML
    private void openCreateEventMenu() {
        this.setOverlay(createEventMenu);
    }

    @FXML
    private void openCreateTodoMenu() {
        this.setOverlay(createTodoMenu);
    }

    @FXML
    private void createNewEventClicked() {
        String title = eventTitleInput.getText();
        try {
            Event newElement;
            int occurrences = 1;
            int occurrenceInterval = 0;

            if (repeatingEventCheckBox.isSelected()) {
                try {
                    occurrences = Integer.parseInt(eventOccurrencesInput.getText());
                    occurrenceInterval = Integer.parseInt(eventOccurrenceIntervalInput.getText());
                } catch (NumberFormatException e) {
                    updateExcpetionOutput(e);
                }
            }

            if (wholeDayEventCheckBox.isSelected()) {
                newElement = new Event(eventStartDateInput.getValue(), title, occurrences, occurrenceInterval, this.calendar);
            } else {
                LocalDateTime startDateTime = getDateTimeFromInputs(eventStartDateInput, eventStartTimeInputH, eventStartTimeInputM);
                LocalDateTime endDateTime = getDateTimeFromInputs(eventEndDateInput, eventEndTimeInputH, eventEndTimeInputM);

                newElement = new Event(startDateTime, endDateTime, title, occurrences, occurrenceInterval, this.calendar);
            }
            calendar.addCalendarElement(newElement);

        } catch (IllegalArgumentException e) {
            updateExcpetionOutput(e);
        }

        this.hideOverlays();
    }

    private void deleteListItem(ListItem item) {
        try {
            if (item == null) throw new Exception("No item was selected to be deleted!");
            this.selectedElements.remove(item.getElement());
            calendar.removeCalendarElement(item.getElement());
        } catch (Exception e) {
            updateExcpetionOutput(e);
        }
    }

    @FXML
    private void deleteSelectedEventClicked() {
        ListItem selected = (ListItem) eventList.getSelectionModel().getSelectedItem();
        deleteListItem(selected);
    }

    @FXML
    private void deleteSelectedTodoClicked() {
        ListItem selected = (ListItem) todoList.getSelectionModel().getSelectedItem();
        deleteListItem(selected);
    }

    private void setEventWholeDay(boolean wholeDay){
        eventStartTimeInputH.setDisable(wholeDay);
        eventStartTimeInputM.setDisable(wholeDay);
        eventEndDateInput.setDisable(wholeDay);
        eventEndTimeInputH.setDisable(wholeDay);
        eventEndTimeInputM.setDisable(wholeDay);
    }

    private void setEventRepeating(boolean repeating){
        eventOccurrencesInput.setDisable(!repeating);
        eventOccurrenceIntervalInput.setDisable(!repeating);
    }

    @FXML
    private void createNewTodoClicked() {
        String title = todoTitleInput.getText();
        try {
            LocalDateTime dateTime = getDateTimeFromInputs(todoDateInput, todoTimeInputH, todoTimeInputM);
            Todo newElement = new Todo(dateTime, title, this.calendar);

            calendar.addCalendarElement(newElement);
        } catch (IllegalArgumentException e) {
            updateExcpetionOutput(e);
        }

        this.hideOverlays();
    }

    @FXML
    private void hideOverlays() {
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

    private void setOverlay(Pane pane) {
        if (this.overlays.contains(pane)) {
            mainPage.setDisable(true);
            this.overlayContainer.setVisible(true);
        } else return;

        for (Pane p: this.overlays) {
            try {
                p.setVisible(p == pane);
            } catch (NullPointerException e) {
                updateExcpetionOutput(e);
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

        LocalDate date = selectedCell.getDate();
        eventPickedDateLabel.setText(String.format("%d. %s %d",
                date.getDayOfMonth(),
                TimeManager.getMonthName(date.getMonthValue()),
                date.getYear()
        ));

        this.selectedElements = this.calendar.getEventsOnDate(date);
        loadEventList();
    }

    @Override
    public void calendarChanged() {
        loadCalendar();
        loadEventList();
        loadTodolist();
    }

    private void updateExcpetionOutput(Exception e) {
        exceptionOutputLabel.setText(e.toString());
    }
}
