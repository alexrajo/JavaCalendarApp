package Calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class Event extends CalendarElement{

    public static final int MINUTES_IN_DAY = 1440;
    private int duration; //Defined in minutes
    private int occurrences;
    private int occurrenceInterval; //Defined in days between each occurrence

    public Event(LocalDateTime dateTime, String title, int occurrences, int interval, int duration, List<ElementListener> listeners) {
        super(dateTime, title, listeners);
        this.setDuration(duration);
        this.setOccurrences(occurrences);
        this.setOccurrenceInterval(interval);
        this.setListenable(true);

        if (this.occurrences < 1) { throw new IllegalArgumentException("An element must occur at least once!"); }

        if (this.occurrences > 1 && this.occurrenceInterval < 1) {
            throw new IllegalArgumentException("Interval between occurrences must be a positive number!");
        }
    }

    public Event(LocalDateTime dateTime, String title, int occurrences, int interval, int duration, ElementListener... listeners) {
        this(dateTime, title, occurrences, interval, duration, Arrays.asList(listeners));
    }

    public Event(LocalDateTime startDateTime, LocalDateTime endDateTime, String title, int occurrences, int interval, ElementListener... listeners) {
        this(startDateTime, title, occurrences, interval, 0, listeners);
        this.setListenable(false);
        if (endDateTime.isBefore(startDateTime)) {
            throw new IllegalArgumentException("Start date must be before end date!");
        }

        int duration = (int) startDateTime.until(endDateTime, ChronoUnit.MINUTES);
        this.setDuration(duration);
        this.setListenable(true);
    }

    public Event(LocalDateTime startDateTime, LocalDateTime endDateTime, String title, ElementListener... listeners) {
        this(startDateTime, endDateTime, title, 1, 0, listeners);
    }

    public Event(LocalDateTime dateTime, String title, int duration, ElementListener... listeners) {
        this(dateTime, title, 1, 0, duration, listeners);
    }

    public Event(LocalDate date, String title, int occurrences, int occurrenceInterval, ElementListener... listeners) {
        this(LocalDateTime.of(date, LocalTime.of(0, 0)), title, occurrences, occurrenceInterval, 1, listeners);
        this.setWholeDay();
    }

    public Event(LocalDate date, String title, ElementListener... listeners) {
        this(date, title, 1, 0, listeners);
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

    @Override
    public String toString() {
        return String.format("%s:\n%s",
                this.getTitle(),
                this.isWholeDay() ? "Hele dagen" : TimeManager.minutesToFormattedTime(this.getDuration()) + " fra " + this.getDateTime().toLocalTime().toString());
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        this.changed();
    }

    public boolean isWholeDay() {
        return (this.getDuration() == MINUTES_IN_DAY && this.getDateTime().toLocalTime().equals(LocalTime.MIDNIGHT));
    }

    public void setWholeDay() {
        LocalDateTime currentDateTime = this.getDateTime();

        this.setListenable(false);
        this.setDateTime(currentDateTime.with(LocalTime.MIDNIGHT));
        this.setListenable(true);
        this.setDuration(MINUTES_IN_DAY);
    }

    public int getOccurrences() {
        return this.occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
        this.changed();
    }

    public int getOccurrenceInterval() {
        return this.occurrenceInterval;
    }

    public void setOccurrenceInterval(int occurrenceInterval) {
        this.occurrenceInterval = occurrenceInterval;
        this.changed();
    }
}
