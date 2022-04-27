package Calendar;

import javafx.scene.control.CheckBox;

public class TodoListItem extends CheckBox implements ListItem {

    private Todo element;

    public TodoListItem(String content, Todo element) {
        super(content);
        this.element = element;
    }

    public Todo getElement() {
        return element;
    }

}
