package Calendar;

import java.util.ArrayList;
import java.util.List;

public class Calendar implements ElementListener {

    private List<CalendarElement> elements = new ArrayList<>();
    private ApplicationController listener;
    private FileManager fileManager;

    public Calendar(ApplicationController listener, String targetFileName) {
        this.listener = listener;
        this.fileManager = new FileManager(targetFileName);

        this.elements = this.fileManager.readFromFile();
    }

    public void addCalendarElement(CalendarElement calendarElement){
        if (ValidCalendarElement(calendarElement)){
            this.elements.add(calendarElement);
            this.fileManager.writeToFile(this.elements);
        }
        else throw new IllegalArgumentException("You can not add multiple instances of the same CalendarElement");
    }

    public List<CalendarElement> getCalendarElements(){
        return this.elements;
    }

    public CalendarElement getCalendarElement(int a){
        return this.elements.get(a);
    }

    private boolean ValidCalendarElement(CalendarElement calendarElement){
        return !this.elements.contains(calendarElement);
    }

    @Override
    public void elementRemoved(CalendarElement element) {

    }

    @Override
    public void elementChanged(CalendarElement element) {

    }
}
