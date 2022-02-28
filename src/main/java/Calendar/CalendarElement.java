package Calendar;

import java.util.Date;

public abstract class CalendarElement {

    private Date date;
    private String title;
    private String description;
    private boolean recurring;

    //Constructors
    public CalendarElement(Date date, String title, String description, boolean recurring) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.recurring = recurring;
    }

    public CalendarElement(Date date, String title, String description) {
        this(date, title, description, false);
    }

    public CalendarElement(Date date, String title, boolean recurring) {
        this(date, title, null, recurring);
    }

    public CalendarElement(Date date, String title) {
        this(date, title, null, false);
    }

    //Public methods
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }
}
