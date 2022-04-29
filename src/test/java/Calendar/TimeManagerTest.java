package Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeManagerTest {

    private TimeManager timeManager;

    @BeforeEach
    public void initialize() {
        this.timeManager = new TimeManager(CalendarTest.testDateTimes.get(0).toLocalDate());
    }

    @Test
    public void testConstructor() {
        Assertions.assertEquals(5, timeManager.getStartDayAdjustment());
    }

    @Test
    public void testSetSelectedDate() {
        timeManager.setSelectedDate(CalendarTest.testDateTimes.get(1).toLocalDate());
        Assertions.assertEquals(CalendarTest.testDateTimes.get(1).toLocalDate(), timeManager.getSelectedDate());
        Assertions.assertEquals(3, timeManager.getStartDayAdjustment());
    }

    @Test
    public void testChangeMonth() {
        timeManager.changeMonthNext();
        Assertions.assertEquals(5, timeManager.getSelectedMonth());
        timeManager.changeMonthPrev();
        Assertions.assertEquals(4, timeManager.getSelectedMonth());
        timeManager.setSelectedMonth(12);
        Assertions.assertEquals(12, timeManager.getSelectedMonth());
        timeManager.changeMonthNext();
        Assertions.assertEquals(1, timeManager.getSelectedMonth());
        Assertions.assertEquals(2023, timeManager.getSelectedYear());
        Assertions.assertThrows(IllegalArgumentException.class, () -> timeManager.setSelectedMonth(0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> timeManager.setSelectedMonth(13));
    }

}
