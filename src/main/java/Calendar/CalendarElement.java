package Calendar;

import java.time.LocalDateTime;
import java.util.List;

public abstract class CalendarElement {

    //Attributes
    private LocalDateTime dateTime;
    private String title;
    private List<ElementListener> listeners;
    private boolean listenable = false;

    //Constructors
    public CalendarElement(LocalDateTime dateTime, String title, ElementListener... listeners) throws IllegalArgumentException {
        this.listeners = List.of(listeners);
        this.setDateTime(dateTime);
        this.setTitle(title);
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
