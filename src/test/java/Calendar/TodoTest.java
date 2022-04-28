package Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TodoTest {

    private Todo todo;

    @BeforeEach
    public void initialize() {
        this.todo = new Todo(CalendarTest.testDateTimes.get(0), "Test");
    }

    @Test
    public void testSetCompleted() {
        this.todo.setCompleted(true);
        Assertions.assertTrue(this.todo.isCompleted());
    }

}
