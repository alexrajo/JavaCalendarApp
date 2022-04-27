package Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class EventTest {

    private Event event;

    @BeforeEach
    public void initialize() {
        this.event = new Event(LocalDateTime.of(2022, 4, 27, 10, 30), "TestEvent", 60);
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
