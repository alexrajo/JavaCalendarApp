package Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CalendarTest {

    private Calendar calendar;

    @BeforeEach
    public void initialize() {
        this.calendar = new Calendar(new ApplicationController(), "testFile");
    }

    @Test
    public void testAddElement() {
        Event testEvent = new Event(LocalDateTime.now(), "TestEvent", 120);
        calendar.addCalendarElement(testEvent);
        Assertions.assertTrue(calendar.getCalendarElements().contains(testEvent));
        Assertions.assertThrows(IllegalArgumentException.class, () -> calendar.addCalendarElement(testEvent));
    }

}
