package Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FileManagerTest {

    private FileManager fileManager;

    @BeforeEach
    public void initialize() {
        this.fileManager = new FileManager("fileManagerTestFile");
        this.fileManager.writeToFile(new ArrayList<CalendarElement>());
    }

    @Test
    public void testReadAndWrite() {
        List<CalendarElement> elements = CalendarTest.createTestElements();
        this.fileManager.writeToFile(elements);

        List<CalendarElement> readElements = this.fileManager.readFromFile();
        Assertions.assertEquals(elements.size(), readElements.size());

        for (int i = 0; i < elements.size(); i++) {
            CalendarElement a = elements.get(i);
            CalendarElement b = readElements.get(i);
            Assertions.assertEquals(a.toFileInfo(), b.toFileInfo());
        }
    }

}
