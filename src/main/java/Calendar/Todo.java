package Calendar;

import java.util.Date;

public class Todo extends Event {

    public Todo(Date date, String title, String description, boolean recurring, int start, int end) {
        super(date, title, description, recurring, start, end);
    }

    public Todo(Date date, String title, String description, int start, int end) {
        super(date, title, description, start, end);
    }

    public Todo(Date date, String title, boolean recurring, int start, int end) {
        super(date, title, recurring, start, end);
    }

    public Todo(Date date, String title, int start, int end) {
        super(date, title, start, end);
    }

}
