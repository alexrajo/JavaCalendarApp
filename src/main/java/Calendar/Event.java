package Calendar;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event extends CalendarElement{

    private static final int MINUTES_IN_DAY = 1440;
    private int duration; //Defined in minutes

    public Event(LocalDateTime dateTime, String title, int occurrences, int interval, int duration, ElementListener... listeners) {
        super(dateTime, title, occurrences, interval, listeners);
        this.setDuration(duration);
        this.setListenable(true);
    }

    public Event(LocalDateTime dateTime, String title, int duration, ElementListener... listeners) {
        super(dateTime, title, listeners);
        this.setDuration(duration);
        this.setListenable(true);
    }

    @Override
    public String toFileInfo() {
        return String.format("%s,%s,%s,%d,%d,%d",
                this.getClass().toString().substring(15),
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
        this.changed();
    }

    public boolean isWholeDay() {
        return (this.getDuration() == MINUTES_IN_DAY);
    }

    public void setWholeDay() {
        LocalDateTime currentDateTime = this.getDateTime();

        this.setListenable(false);
        this.setDateTime(currentDateTime.with(LocalTime.MIDNIGHT));
        this.setListenable(true);
        this.setDuration(MINUTES_IN_DAY);
    }
}
