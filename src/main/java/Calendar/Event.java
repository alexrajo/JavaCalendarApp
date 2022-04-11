package Calendar;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;

public class Event extends CalendarElement{

    //Defined in seconds after midnight:
    private int startTime;
    private int endTime;
    //--

    public Event(Date date, String title, String description, boolean recurring, int start, int end) {
        super(date, title, description, recurring);
        this.setStartTime(start);
        this.setEndTime(end);
    }

    public Event(Date date, String title, String description, int start, int end) {
        super(date, title, description);
        this.setStartTime(start);
        this.setEndTime(end);
    }

    public Event(Date date, String title, boolean recurring, int start, int end) {
        super(date, title, recurring);
        this.setStartTime(start);
        this.setEndTime(end);
    }

    public Event(Date date, String title, int start, int end) {
        super(date, title);
        this.setStartTime(start);
        this.setEndTime(end);
    }

    public String toString() {
        return getTitle()+" "+getStartTime()+" "+getEndTime();
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public boolean isWholeDay() {
        return (this.getEndTime()-this.getStartTime() == 86400);
    }

    public void setWholeDay() {
        this.setStartTime(0);
        this.setEndTime(86400);
    }
}
