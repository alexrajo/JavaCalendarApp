package Calendar;

import java.time.LocalDateTime;

public class Todo extends CalendarElement {

    private boolean completed;

    public Todo(LocalDateTime dateTime, String title, boolean completed, ElementListener... listeners) {
        super(dateTime, title, listeners);
        this.setCompleted(completed);
        this.setListenable(true);
    }

    public Todo(LocalDateTime dateTime, String title, ElementListener... listeners) {
        this(dateTime, title, false, listeners);
    }

    @Override
    public String toFileInfo() {
        return String.format("%s,%s,%s,%s",
                this.getClass().toString().substring(15),
                this.getDateTime(),
                this.getTitle(),
                this.isCompleted() ? "1" : "0"
        );
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        this.changed();
    }

}
