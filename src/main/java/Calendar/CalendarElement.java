package Calendar;

import java.time.LocalDateTime;

public abstract class CalendarElement {

    //Attributes
    private LocalDateTime dateTime;
    private String title;
    private int occurrences;
    private int occurrenceInterval; //Defined in seconds between each occurrence

    //Constructors
    public CalendarElement(LocalDateTime dateTime, String title, int occurrences, int occurrenceInterval) throws IllegalArgumentException {
        this.setDateTime(dateTime);
        this.setTitle(title);
        this.setOccurrences(occurrences);
        this.setOccurrenceInterval(occurrenceInterval);

        if (this.occurrences < 1) { throw new IllegalArgumentException("An element must occur at least once!"); }

        if (this.occurrences > 1 && this.occurrenceInterval < 1) {
            throw new IllegalArgumentException("Interval between occurrences must be a positive number!");
        }
    }

    public CalendarElement(LocalDateTime dateTime, String title) {
        this(dateTime, title, 1, 0);
    }

    //Public methods
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOccurrences() {
        return this.occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public int getOccurrenceInterval() {
        return this.occurrenceInterval;
    }

    public void setOccurrenceInterval(int occurrenceInterval) {
        this.occurrenceInterval = occurrenceInterval;
    }
}
