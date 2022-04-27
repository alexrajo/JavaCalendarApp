package Calendar;

import javafx.scene.text.Text;

public class EventListItem extends Text implements ListItem {

    private Event element;

    public EventListItem(String content, Event element) {
        super(content);
        this.element = element;
    }

    @Override
    public Event getElement() {
        return element;
    }

}
