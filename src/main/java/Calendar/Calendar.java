package Calendar;

import java.util.ArrayList;
import java.util.List;

public class Calendar implements ElementListener {

    private List<CalendarElement> elements = new ArrayList<>();
    private ApplicationController controller;
    private FileManager fileManager;

    public Calendar(ApplicationController controller, String targetFileName) {
        this.controller = controller;
        this.fileManager = new FileManager(targetFileName, this);

        this.elements = this.fileManager.readFromFile();
    }

    public Calendar(String targetFileName) {
        this(null, targetFileName);
    }

    public boolean addCalendarElement(CalendarElement calendarElement) {
        if (this.elements.contains(calendarElement)) return false; //Element was not added to calendar

        this.elements.add(calendarElement);
        this.fileManager.writeToFile(this.elements);

        if (this.controller != null) {
            this.controller.loadCalendar();
            this.controller.loadTodolist();
            this.controller.loadEventList();
        }

        return true; //Element was added to calendar
    }

    public boolean removeCalendarElement(CalendarElement calendarElement) {
        boolean wasRemoved = this.elements.remove(calendarElement);
        if (wasRemoved) {
            this.updateFile();
        }
        return wasRemoved;
    }

    public void clear() {
        this.elements = new ArrayList<CalendarElement>();
        this.updateFile();
    }

    public List<CalendarElement> getCalendarElements(){
        return this.elements;
    }

    public CalendarElement getCalendarElement(int a){
        return this.elements.get(a);
    }

    private void updateFile() {
        this.fileManager.writeToFile(this.getCalendarElements());
    }

    public static boolean isLeapYear(int year) {
        return (year-1752)%4 == 0;
    }

    @Override
    public void elementChanged(CalendarElement element) {

        if (this.controller != null) {
            this.controller.loadCalendar();
            if (element instanceof Todo) {
                this.controller.loadTodolist();
            }
        }

        this.updateFile();
    }

}
