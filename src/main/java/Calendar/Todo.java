package Calendar;

import java.util.Date;

public class Todo extends CalendarElement{

    public Todo(Date date, String title, String description, boolean reccuring) {
        super(date, title, description, reccuring);
    }

    public Todo(Date date, String title, String description) {
        super(date, title, description);
    }

    public Todo(Date date, String title, boolean reccuring) {
        super(date, title, reccuring);
    }

    public Todo(Date date, String title) {
        super(date, title);
    }

}
