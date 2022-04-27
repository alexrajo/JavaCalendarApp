package Calendar;

import javafx.scene.text.Text;

public class EventListItem extends Text {

    private Event event;

    public EventListItem(String content, Event event) {
        super(content);
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

}
