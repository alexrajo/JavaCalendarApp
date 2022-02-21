package Calendar;

import java.util.Date;

public abstract class CalendarElement {

    private Date date;
    private String title;
    private String description;
    private boolean reccuring;

    public CalendarElement(Date date, String title, String description, boolean reccuring) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.reccuring = reccuring;
    }
    public CalendarElement(Date date, String title, String description) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.reccuring = false;
    }
    public CalendarElement(Date date, String title, boolean reccuring) {
        this.date = date;
        this.title = title;
        this.reccuring = reccuring;
    }
    public CalendarElement(Date date, String title) {
        this.date = date;
        this.title = title;
        this.reccuring = false;
    }

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

    public boolean isReccuring() {
        return reccuring;
    }

    public void setReccuring(boolean reccuring) {
        this.reccuring = reccuring;
    }
}
