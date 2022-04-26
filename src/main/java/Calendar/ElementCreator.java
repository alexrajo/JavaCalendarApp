package Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;

public class ElementCreator {

    public static CheckBox createCheckbox(CalendarElement element){
        Todo todo = (Todo) element;
        CheckBox checkBox = new CheckBox(element.getTitle());
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

    public static Text createListItem(CalendarElement element){
        Event todo = (Event) element;
        return new Text(element.toString());
    }

}
