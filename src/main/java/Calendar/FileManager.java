package Calendar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager implements FileInterface {

    private File file;
    private String fileName;
    private FileWriter writer;
    private Scanner scanner;
    public static String filePath = "/resources/Calendar/CalendarStorage/";

    public FileManager(String fileName) {
        this.fileName = fileName;
        this.file = new File(filePath + fileName);

        try {
            boolean createdNewFile = this.file.createNewFile();
            if (createdNewFile) {
                System.out.println("Created new file: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile(List<CalendarElement> entries) {
        String fileContent = "";
        for(CalendarElement entry: entries) {
            fileContent += entry.toString() + System.lineSeparator();
        }
        try {
            this.writer = new FileWriter(this.file);

            this.writer.write(fileContent);
            this.writer.flush();
            this.writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CalendarElement> readFromFile() {
        try {
            List<CalendarElement> elements = new ArrayList<CalendarElement>();

            this.scanner = new Scanner(this.file);
            while (this.scanner.hasNextLine()) {
                String line = this.scanner.nextLine();
                List<String> info = List.of(line.split("\\|"));

                switch (info.get(0)) {
                    case "Event":
                        elements.add(new Event(LocalDateTime.now(), "Test", 3600));
                        break;

                    case "Todo":
                        elements.add(new Todo(LocalDateTime.now(), "Finish file manager"));
                        break;

                    default:
                        break;
                }

            }

            return elements;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<CalendarElement>();
    }
}
