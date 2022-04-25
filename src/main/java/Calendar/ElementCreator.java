package Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;

public class ElementCreator {

    public static Pane gridPane(boolean isToday, int index){
        Pane element = new Pane();
        element.setScaleX(1);
        element.setScaleY(1);
        if (isToday) {
            element.setStyle("-fx-background-color: #87FFAD");
        } else if (index%2==0) {
            element.setStyle("-fx-background-color: #EEEEEE");
        }
        return element;
    }

    public static Text gridInfo(List<String> list){
        Text info = new Text(String.valueOf(list.size())+" events");
        info.setLayoutY(40);
        info.setLayoutX(20);
        return info;
    }

    public static Text gridTitle(int index, int startDayAdjustment){
        String titleVal = index < startDayAdjustment ? "" : String.valueOf(index-startDayAdjustment+1);
        Text title = new Text(titleVal);
        title.setLayoutY(15);
        title.setLayoutX(5);
        return title;
    }

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

}
