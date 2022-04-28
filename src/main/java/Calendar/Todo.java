package Calendar;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Todo extends CalendarElement {

    private boolean completed;

    public Todo(LocalDateTime dateTime, String title, boolean completed, List<ElementListener> listeners) {
        super(dateTime, title, listeners);
        this.setCompleted(completed);
        this.setListenable(true);
    }

    public Todo(LocalDateTime dateTime, String title, boolean completed, ElementListener... listeners) {
        this(dateTime, title, completed, Arrays.asList(listeners));
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

    @Override
    public String toString() {
        return String.format("%s (%s)",
                this.getTitle(),
                this.getDateTime().toString().replace('T', ' '));
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        this.changed();
    }

}
