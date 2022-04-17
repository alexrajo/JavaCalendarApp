package Calendar;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event extends CalendarElement{

    private int duration; //Defined in seconds after midnight:

    public Event(LocalDateTime dateTime, String title, String description, int occurrences, int interval, int duration) {
        super(dateTime, title, description, occurrences, interval);
        this.setDuration(duration);
    }

    public Event(LocalDateTime dateTime, String title, String description, int duration) {
        super(dateTime, title, description);
        this.setDuration(duration);
    }

    public Event(LocalDateTime dateTime, String title, int occurrences, int interval, int duration) {
        super(dateTime, title, occurrences, interval);
        this.setDuration(duration);
    }

    public Event(LocalDateTime dateTime, String title, int duration) {
        super(dateTime, title);
        this.setDuration(duration);
    }

    public String toString() {
        return this.getTitle() + ": lasts for " + String.valueOf(this.getDuration()) + " seconds";
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isWholeDay() {
        return (this.getDuration() == 86400);
    }

    public void setWholeDay() {
        LocalDateTime currentDateTime = this.getDateTime();

        this.setDateTime(currentDateTime.with(LocalTime.MIDNIGHT));
        this.setDuration(86400);
    }
}
