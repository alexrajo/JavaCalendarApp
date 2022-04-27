package Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CalendarTest {

    public static final List<LocalDateTime> testDateTimes = Arrays.asList(
            LocalDateTime.of(2022, 4, 27, 10, 30),
            LocalDateTime.of(2022, 6, 30, 17, 45),
            LocalDateTime.of(2023, 9, 5, 9, 45)
    );


    private Calendar calendar;

    @BeforeEach
    public void initialize() {
        this.calendar = new Calendar("calendarTestFile");
        calendar.clear();
        calendar.addCalendarElement(new Todo(testDateTimes.get(0), "Test #1"));
        calendar.addCalendarElement(new Todo(testDateTimes.get(1), "Test #2"));
        calendar.addCalendarElement(new Event(testDateTimes.get(2), "Test #3", 90));
    }

    @Test
    public void testAddElement() {
        Event testEvent = new Event(LocalDateTime.now(), "TestEvent", 120);
        Assertions.assertTrue(calendar.addCalendarElement(testEvent));
        Assertions.assertTrue(calendar.getCalendarElements().contains(testEvent));
        Assertions.assertFalse(calendar.addCalendarElement(testEvent));
    }

    @Test
    public void testRemoveElement() {
        CalendarElement target = calendar.getCalendarElement(0);
        Assertions.assertTrue(calendar.getCalendarElements().contains(target));
        Assertions.assertTrue(calendar.removeCalendarElement(target));
        Assertions.assertFalse(calendar.getCalendarElements().contains(target));
        Assertions.assertFalse(calendar.removeCalendarElement(new Event(LocalDateTime.now(), "Hello", 10)));
    }

    @Test
    public void testClearCalendar() {
        calendar.clear();
        Assertions.assertTrue(calendar.getCalendarElements().isEmpty());
    }

}
