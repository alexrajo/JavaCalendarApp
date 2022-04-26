package Calendar;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.List;

public class CalendarCell extends Pane {

    private LocalDate date;
    private List<DateSelectionListener> selectionListeners;
    private int eventCount;
    private String baseStyle;
    boolean selected;

    private abstract class CellEventHandler<T> implements EventHandler {

        protected CalendarCell cell;

        public CellEventHandler(CalendarCell cell) {
            this.cell = cell;
        }

        public abstract void handle(T event);
    }

    public CalendarCell(LocalDate date, int eventCount, DateSelectionListener... listeners) {
        this.date = date;
        this.eventCount = eventCount;
        this.selectionListeners = List.of(listeners);
        this.selected = false;

        this.setScaleX(1);
        this.setScaleY(1);
        this.getStyleClass().add("cell");
        if (LocalDate.now().equals(date)) { //If this cell represents today
            this.setStyle("-fx-background-color: #87FFAD");
        } else if (date.getDayOfMonth()%2==0) {
            this.setStyle("-fx-background-color: #EEEEEE");
        } else {
            this.setStyle("-fx-background-color: #FFFFFF");
        }

        this.getChildren().add(this.createNewCellTitle());

        if (eventCount > 0) {
            this.getChildren().add(this.createNewCellInfo());
        }

        this.setOnMouseClicked(new CellEventHandler<Event>(this) {
            @Override
            public void handle(Event event) {
                this.cell.select();
            }
        });
        this.setOnMouseEntered(new CellEventHandler<Event>(this) {
            @Override
            public void handle(Event event) {
                this.cell.highlight();
            }
        });
        this.setOnMouseExited(new CellEventHandler<Event>(this) {
            @Override
            public void handle(Event event) {
                this.cell.removeHighlight();
            }
        });

        this.baseStyle = this.getStyle();
    }

    private void highlight() {
        if (!this.selected) {
            this.setStyle(this.baseStyle + "; -fx-background-color: #CCCCCC; -fx-view-order: -2;");
        }
    }

    private void removeHighlight() {
        if (!this.selected) {
            this.setStyle(this.baseStyle);
        }
    }

    private void select() {
        this.selected = true;
        this.setStyle(this.baseStyle + "; -fx-border-width: 3; -fx-view-order: -1;");

        for (DateSelectionListener listener: this.selectionListeners) {
            listener.dateCellSelected(this);
        }
    }

    public void deselect() {
        this.selected = false;
        this.setStyle(this.baseStyle);
    }

    private Text createNewCellTitle(){
        String titleValue = String.valueOf(this.date.getDayOfMonth());
        Text title = new Text(titleValue);
        title.setLayoutY(15);
        title.setLayoutX(5);
        return title;
    }

    private Text createNewCellInfo(){
        Text info = new Text(String.valueOf(this.eventCount)+" events");
        info.setLayoutY(40);
        info.setLayoutX(20);
        return info;
    }

    public LocalDate getDate() {
        return this.date;
    }

}
