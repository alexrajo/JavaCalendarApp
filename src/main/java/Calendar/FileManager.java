package Calendar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager implements FileInterface {

    private static final String separator = File.separator;
    public static String filePath = System.getProperty("user.dir")
            + separator + "src"
            + separator + "main"
            + separator + "resources"
            + separator + "Calendar"
            + separator + "storage";

    private final File file;
    private String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
        this.file = new File(filePath + File.separator + fileName + ".txt");

        try {
            boolean createdNewFile = this.file.createNewFile();
            if (createdNewFile) {
                System.out.println("Created new file: " + fileName + " at " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile(List<CalendarElement> entries) {
        StringBuilder fileContent = new StringBuilder();
        for(CalendarElement entry: entries) {
            fileContent.append(entry.toString()).append(System.lineSeparator());
        }
        try {
            FileWriter writer = new FileWriter(this.file);

            writer.write(fileContent.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CalendarElement> readFromFile() {
        try {
            List<CalendarElement> elements = new ArrayList<CalendarElement>();

            Scanner scanner = new Scanner(this.file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> info = List.of(line.split("\\|"));

                switch (info.get(0)) {
                    case "Event" -> {
                        elements.add(new Event(LocalDateTime.now(), "Test", 3600));
                    }

                    case "Todo" -> {
                        elements.add(new Todo(LocalDateTime.now(), "Finish file manager"));
                    }
                }
            }

            return elements;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<CalendarElement>();
    }

    public String getFileName() {
        return this.fileName;
    }

    public void renameFile(String newFileName) {
        this.fileName = newFileName;
    }
}
