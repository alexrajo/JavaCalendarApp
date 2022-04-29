package Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;

public class ListItemCreator {

    public static TodoListItem createTodoListItem(CalendarElement element){
        Todo todo = (Todo) element;
        TodoListItem checkBox = new TodoListItem(element.toString(), todo);
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
