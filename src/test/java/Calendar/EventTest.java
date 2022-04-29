package Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EventTest {

    private Event event;

    @BeforeEach
    public void initialize() {
        this.event = new Event(CalendarTest.testDateTimes.get(0), "TestEvent", 60);
    }

    @Test
    @DisplayName("Test validation of constructor inputs")
    public void testConstructors() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Event(CalendarTest.testDateTimes.get(0), "Test", 0, 1, 90));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Event(CalendarTest.testDateTimes.get(0), "Test", 2, 0, 90));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Event(CalendarTest.testDateTimes.get(0), "Test", -1, 0, 90));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Event(CalendarTest.testDateTimes.get(2), CalendarTest.testDateTimes.get(0), "Test"));
        Assertions.assertDoesNotThrow(() -> new Event(CalendarTest.testDateTimes.get(0), CalendarTest.testDateTimes.get(2), "Test"));
        Assertions.assertDoesNotThrow(() -> new Event(CalendarTest.testDateTimes.get(0), "Test", 3, 7, 60));
    }

    @Test
    @DisplayName("Test if datetime and duration is set properly when setWholeDay() is fired")
    public void testSetWholeDay() {
        event.setWholeDay();
        Assertions.assertTrue(event.isWholeDay());
    }

    @Test
    @DisplayName("Test validation and general functionality in setTitle() method")
    public void testSetTitle() {
        event.setTitle("DifferentTitle");
        Assertions.assertEquals("DifferentTitle", event.getTitle());
        Assertions.assertThrows(IllegalArgumentException.class, () -> event.setTitle("Hello, goodbye!"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> event.setTitle(""));
    }

}
