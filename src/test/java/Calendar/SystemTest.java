package Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class SystemTest {

    private Calendar calendar;

    @BeforeEach
    public void initialize() {
        this.calendar = new Calendar("systemTestFile");
        calendar.clear();
        List<CalendarElement> testElements = CalendarTest.createTestElements(calendar);
        for (CalendarElement element : testElements) {
            calendar.addCalendarElement(element);
        }
    }

    @Test
    @DisplayName("Test if updates in a CalendarElement propagates to the file using the observer-, observed-principle")
    public void testElementUpdatePropagation() {
        Todo testTodo = (Todo) calendar.getCalendarElement(0);
        testTodo.setTitle("Updated title");
        testTodo.setDateTime(LocalDateTime.now());
        testTodo.setCompleted(true);

        Event testEvent = (Event) calendar.getCalendarElement(2);
        testEvent.setDuration(50);
        testEvent.setDateTime(LocalDateTime.now());

        FileManager secondaryFileManager = new FileManager("systemTestFile");
        List<CalendarElement> readElements = secondaryFileManager.readFromFile();
        FileManagerTest.compareLists(calendar.getCalendarElements(), readElements);

    }

}
