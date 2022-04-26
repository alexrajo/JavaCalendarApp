package Calendar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class CalendarElement {

    //Attributes
    private LocalDateTime dateTime;
    private String title;
    private int occurrences;
    private int occurrenceInterval; //Defined in days between each occurrence
    private List<ElementListener> listeners;
    private boolean listenable = false;

    //Constructors
    public CalendarElement(LocalDateTime dateTime, String title, int occurrences, int occurrenceInterval, ElementListener... listeners) throws IllegalArgumentException {
        this.listeners = List.of(listeners);
        this.setDateTime(dateTime);
        this.setTitle(title);
        this.setOccurrences(occurrences);
        this.setOccurrenceInterval(occurrenceInterval);

        if (this.occurrences < 1) { throw new IllegalArgumentException("An element must occur at least once!"); }

        if (this.occurrences > 1 && this.occurrenceInterval < 1) {
            throw new IllegalArgumentException("Interval between occurrences must be a positive number!");
        }
    }

    public CalendarElement(LocalDateTime dateTime, String title, ElementListener... listeners) {
        this(dateTime, title, 1, 0, listeners);
    }

    //Public methods
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.changed();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.changed();
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

    public abstract String toFileInfo();

    protected void setListenable(boolean listenable) {
        this.listenable = listenable;
    }

    protected void changed() {
        if (!this.listenable) { return; }

        for (ElementListener listener : listeners) {
            listener.elementChanged(this);
        }
    }
}
