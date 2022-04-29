package Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    public static void compareLists(List<CalendarElement> list1, List<CalendarElement> list2) {
        Assertions.assertEquals(list1.size(), list2.size());
        for (int i = 0; i < list1.size(); i++) {
            CalendarElement a = list1.get(i);
            CalendarElement b = list2.get(i);
            Assertions.assertEquals(a.toFileInfo(), b.toFileInfo());
        }
    }

    @Test
    @DisplayName("Test that information integrity is intact when reading from and writing to file")
    public void testReadAndWrite() {
        List<CalendarElement> elements = CalendarTest.createTestElements();
        this.fileManager.writeToFile(elements);

        List<CalendarElement> readElements = this.fileManager.readFromFile();
        compareLists(elements, readElements);
    }

    @Test
    @DisplayName("Test that data written to file is persistent")
    public void testPersistence() {
        List<CalendarElement> elements = CalendarTest.createTestElements();
        this.fileManager.writeToFile(elements);

        FileManager secondaryFileManager = new FileManager(this.fileManager.getFileName());
        List<CalendarElement> readElements = secondaryFileManager.readFromFile();
        compareLists(elements, readElements);
    }

}
