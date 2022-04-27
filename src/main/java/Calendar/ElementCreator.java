package Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;

public class ElementCreator {

    public static CheckBox createTodoCheckbox(CalendarElement element){
        Todo todo = (Todo) element;
        CheckBox checkBox = new CheckBox(element.toString());
        checkBox.setId(element.getTitle());
        checkBox.setSelected(todo.isCompleted());
        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                todo.setCompleted(checkBox.isSelected());
            }
        });
        return checkBox;
    }

    public static Text createEventListItem(CalendarElement element){
        Event event = (Event) element;
        EventListItem item = new EventListItem(event.toString(), event);
        return item;
    }

}
