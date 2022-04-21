package Calendar;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event extends CalendarElement{

    private int duration; //Defined in seconds after midnight:

    public Event(LocalDateTime dateTime, String title, int occurrences, int interval, int duration) {
        super(dateTime, title, occurrences, interval);
        this.setDuration(duration);
    }

    public Event(LocalDateTime dateTime, String title, int duration) {
        super(dateTime, title);
        this.setDuration(duration);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%d,%d,%d",
                this.getClass(),
                this.getDateTime(),
                this.getTitle(),
                this.getOccurrences(),
                this.getOccurrenceInterval(),
                this.getDuration()
                );
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
