package Calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public abstract class CalendarElement {

    //Attributes
    private LocalDateTime dateTime;
    private String title;
    private String description;
    private int occurrences;
    private int occurrenceInterval; //Defined in seconds between each occurrence

    //Constructors
    public CalendarElement(LocalDateTime dateTime, String title, String description, int occurrences, int occurrenceInterval) throws IllegalArgumentException {
        this.setDateTime(dateTime);
        this.setTitle(title);
        this.setDescription(description);

        if (this.occurrences < 1) { throw new IllegalArgumentException("An element must occur at least once!"); }
        this.setOccurrences(occurrences);

        if (this.occurrences > 1 && this.occurrenceInterval < 1) {
            throw new IllegalArgumentException("Interval between occurrences must be a positive number!");
        }
        this.setOccurrenceInterval(occurrenceInterval);
    }

    public CalendarElement(LocalDateTime dateTime, String title, String description) {
        this(dateTime, title, description, 1, 0);
    }

    public CalendarElement(LocalDateTime dateTime, String title, int occurrences, int occurrenceInterval) {
        this(dateTime, title, null, occurrences, occurrenceInterval);
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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
