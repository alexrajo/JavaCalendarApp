package Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class EventTest {

    private Event event;

    @BeforeEach
    public void initialize() {
        this.event = new Event(CalendarTest.testDateTimes.get(0), "TestEvent", 60);
    }

    @Test
    public void testSetWholeDay() {
        event.setWholeDay();
        Assertions.assertTrue(event.isWholeDay());
    }

    @Test
    public void setTitle() {
        event.setTitle("DifferentTitle");
        Assertions.assertEquals("DifferentTitle", event.getTitle());
    }

}
