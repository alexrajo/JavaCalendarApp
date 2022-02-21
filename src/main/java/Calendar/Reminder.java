package Calendar;

import java.util.Date;

public class Reminder extends CalendarElement{

    public Reminder(Date date, String title, String description, boolean reccuring) {
        super(date, title, description, reccuring);
    }

    public Reminder(Date date, String title, String description) {
        super(date, title, description);
    }

    public Reminder(Date date, String title, boolean reccuring) {
        super(date, title, reccuring);
    }

    public Reminder(Date date, String title) {
        super(date, title);
    }

}
