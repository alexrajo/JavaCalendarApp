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
    private CheckBox wholeDayEventCheckBox;

    @FXML
    private Button prevBtn, nextBtn;

    @FXML
    private ListView todoList, eventList;

    @FXML
    private Text calendarTitle, eventPickedDateLabel;

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
    private TextField eventStartTimeInputH, eventStartTimeInputM;
    @FXML
    private TextField eventEndTimeInputH, eventEndTimeInputM;

    @FXML
    private TextField todoTitleInput, todoTimeInputH, todoTimeInputM;
    @FXML
    private DatePicker todoDateInput;

    @FXML
    public void initialize() {
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
    }

    @FXML
    public void loadTodolist(){
        this.todoList.getItems().clear();
        for (CalendarElement element: calendar.getCalendarElements()) {
            if (element instanceof Todo){
                this.todoList.getItems().add(ElementCreator.createTodoListItem(element));
            }
        }
    }

    @FXML
    public void loadEventList(){
        this.eventList.getItems().clear();
        for (CalendarElement element: this.selectedElements) {
            if (element instanceof Event) {
                this.eventList.getItems().add(ElementCreator.createEventListItem(element));
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
                    if (c instanceof Event && c.getDateTime().toLocalDate().equals(cellDate)) eventCount++;
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
            Event newElement;

            if (wholeDayEventCheckBox.isSelected()) {
                newElement = new Event(eventStartDateInput.getValue(), title, this.calendar);
            } else {
                LocalDateTime startDateTime = getDateTimeFromInputs(eventStartDateInput, eventStartTimeInputH, eventStartTimeInputM);
                LocalDateTime endDateTime = getDateTimeFromInputs(eventEndDateInput, eventEndTimeInputH, eventEndTimeInputM);

                newElement = new Event(startDateTime, endDateTime, title, this.calendar);
            }
            calendar.addCalendarElement(newElement);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        this.hideOverlays();
    }

    public void deleteListItem(ListItem item) {
        if (item != null) {
            this.selectedElements.remove(item.getElement());
            calendar.removeCalendarElement(item.getElement());
        }
    }

    @FXML
    public void deleteSelectedEventClicked() {
        ListItem selected = (ListItem) eventList.getSelectionModel().getSelectedItem();
        deleteListItem(selected);
    }

    @FXML
    public void deleteSelectedTodoClicked() {
        ListItem selected = (ListItem) todoList.getSelectionModel().getSelectedItem();
        deleteListItem(selected);
    }

    public void setEventWholeDay(boolean wholeDay){
        eventStartTimeInputH.setDisable(wholeDay);
        eventStartTimeInputM.setDisable(wholeDay);
        eventEndDateInput.setDisable(wholeDay);
        eventEndTimeInputH.setDisable(wholeDay);
        eventEndTimeInputM.setDisable(wholeDay);
    }

    @FXML
    public void createNewTodoClicked() {
        String title = todoTitleInput.getText();
        try {
            LocalDateTime dateTime = getDateTimeFromInputs(todoDateInput, todoTimeInputH, todoTimeInputM);
            Todo newElement = new Todo(dateTime, title, this.calendar);

            calendar.addCalendarElement(newElement);
        } catch (IllegalArgumentException e) {
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

    // Kan eventuelt bruke funksjonelle grensesnitt for å stykke opp kode mer slik at man unngår repeterende kode
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

        LocalDate date = selectedCell.getDate();
        eventPickedDateLabel.setText(String.format("%d. %s %d",
                date.getDayOfMonth(),
                TimeManager.monthString.get(date.getMonthValue()-1),
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
}
