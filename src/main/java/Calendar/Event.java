package Calendar;

import java.util.Date;

public class Event extends CalendarElement{

    private Date startTime;
    private Date endTime;
    private boolean wholeDay;

    public Event(Date date, String title, String description, boolean reccuring, boolean wholeDay) {
        super(date, title, description, reccuring);
        if (wholeDay) {
            //Sette starttid til 00:00 og slutttid 24:00
        }
    }

    public Event(Date date, String title, String description, boolean wholeDay) {
        super(date, title, description);
        if (this.wholeDay) {
            //Sette starttid til 00:00 og slutttid 24:00
        }
    }

    public Event(Date date, String title, boolean reccuring, boolean wholeDay) {
        super(date, title, reccuring);
        if (wholeDay) {
            //Sette starttid til 00:00 og slutttid 24:00
        }
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isWholeDay() {
        return wholeDay;
    }

    public void setWholeDay(boolean wholeDay) {
        this.wholeDay = wholeDay;
    }

    public Event(Date date, String title) {
        super(date, title);
        if (wholeDay) {
            //Sette starttid til 00:00 og slutttid 24:00
        }
    }
}
