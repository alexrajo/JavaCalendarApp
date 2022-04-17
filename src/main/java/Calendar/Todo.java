package Calendar;

import java.time.LocalDateTime;

public class Todo extends CalendarElement {

    private boolean completed;

    public Todo(LocalDateTime dateTime, String title, String description, boolean completed) {
        super(dateTime, title, description);
        this.setCompleted(completed);
    }

    public Todo(LocalDateTime dateTime, String title, boolean completed) {
        super(dateTime, title);
        this.setCompleted(completed);
    }

    public Todo(LocalDateTime dateTime, String title, String description) {
        this(dateTime, title, description, false);
    }

    public Todo(LocalDateTime dateTime, String title) {
        this(dateTime, title, false);
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
