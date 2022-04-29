package Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalendarTest {

    public static final List<LocalDateTime> testDateTimes = Arrays.asList(
            LocalDateTime.of(2022, 4, 27, 10, 30),
            LocalDateTime.of(2022, 6, 30, 17, 45),
            LocalDateTime.of(2023, 9, 5, 9, 45)
    );

    public static List<CalendarElement> createTestElements(ElementListener... listeners) {
        List<CalendarElement> elements = new ArrayList<CalendarElement>();
        elements.add(new Todo(testDateTimes.get(0), "Test #1", listeners));
        elements.add(new Todo(testDateTimes.get(1), "Test #2", listeners));
        elements.add(new Event(testDateTimes.get(2), "Test #3", 90, listeners));
        elements.add(new Event(testDateTimes.get(2), "Test #4", 7, 7, 90, listeners));
        return elements;
    }

    private Calendar calendar;

    @BeforeEach
    public void initialize() {
        this.calendar = new Calendar("calendarTestFile");
        calendar.clear();
        List<CalendarElement> testElements = createTestElements();
        for (CalendarElement element : testElements) {
            calendar.addCalendarElement(element);
        }
    }

    @Test
    @DisplayName("Test validation when adding element to calendar")
    public void testAddElement() {
        Event testEvent = new Event(LocalDateTime.now(), "TestEvent", 120);
        Assertions.assertTrue(calendar.addCalendarElement(testEvent));
        Assertions.assertTrue(calendar.getCalendarElements().contains(testEvent));
        Assertions.assertFalse(calendar.addCalendarElement(testEvent));
    }

    @Test
    @DisplayName("Test validation when removing element from calendar")
    public void testRemoveElement() {
        CalendarElement target = calendar.getCalendarElement(0);
        Assertions.assertTrue(calendar.getCalendarElements().contains(target));
        Assertions.assertTrue(calendar.removeCalendarElement(target));
        Assertions.assertFalse(calendar.getCalendarElements().contains(target));
        Assertions.assertFalse(calendar.removeCalendarElement(new Event(LocalDateTime.now(), "Hello", 10)));
    }

    @Test
    @DisplayName("Test if calendar is cleared when clear() method is called")
    public void testClearCalendar() {
        calendar.clear();
        Assertions.assertTrue(calendar.getCalendarElements().isEmpty());
    }

    @Test
    @DisplayName("Test that the correct events are retrieved on a specified date")
    public void testGetEventsOnDate() {
        Assertions.assertEquals(0, calendar.getEventsOnDate(testDateTimes.get(0).toLocalDate()).size());
        Assertions.assertTrue(calendar.getEventsOnDate(testDateTimes.get(2).toLocalDate()).contains(calendar.getCalendarElement(2)));
        FileManagerTest.compareLists(Arrays.asList(calendar.getCalendarElement(2), calendar.getCalendarElement(3)), calendar.getEventsOnDate(testDateTimes.get(2).toLocalDate()));
        FileManagerTest.compareLists(List.of(calendar.getCalendarElement(3)), calendar.getEventsOnDate(testDateTimes.get(2).toLocalDate().plusDays(7)));
    }

}
