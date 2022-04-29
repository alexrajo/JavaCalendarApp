package Calendar;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public abstract class CalendarElement {

    private LocalDateTime dateTime;
    private String title;
    private List<ElementListener> listeners;
    private boolean listenable = false;

    public CalendarElement(LocalDateTime dateTime, String title, List<ElementListener> listeners) throws IllegalArgumentException {
        this.listeners = listeners;
        this.setDateTime(dateTime);
        this.setTitle(title);
    }

    public CalendarElement(LocalDateTime dateTime, String title, ElementListener... listeners) throws IllegalArgumentException {
        this(dateTime, title, Arrays.asList(listeners));
    }

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

    /**
     * @param title the new title of the element (cannot contain a ',' because of resulting issues with data persistence)
     */
    public void setTitle(String title) throws IllegalArgumentException {
        if (title.equals("") || title == null) throw new IllegalArgumentException("You must input a title!");
        if (title.contains(",")) throw new IllegalArgumentException("Title cannot contain a ','!");

        this.title = title;
        this.changed();
    }

    public abstract String toFileInfo();

    protected void setListenable(boolean listenable) {
        this.listenable = listenable;
    }

    protected void changed() {
        if (!this.listenable) { return; }

        for (ElementListener listener : listeners) {
            if (listener == null) { continue; }
            listener.elementChanged(this);
        }
    }
}
