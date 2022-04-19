package Calendar;

import java.util.List;

public interface FileInterface {

    public void writeToFile(List<CalendarElement> entries);
    public List<CalendarElement> readFromFile();

}
