package Calendar;

import java.time.LocalDateTime;

public class Todo extends CalendarElement {

    private boolean completed;

    public Todo(LocalDateTime dateTime, String title, boolean completed) {
        super(dateTime, title);
        this.setCompleted(completed);
    }

    public void toggleCheck(){
        boolean a = completed;
        completed = !a;
        System.out.println(completed);
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
