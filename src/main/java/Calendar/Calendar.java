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

    public void addCalendarElement(CalendarElement calendarElement) throws IllegalArgumentException {
        if (ValidCalendarElement(calendarElement)){
            this.elements.add(calendarElement);
            this.fileManager.writeToFile(this.elements);
            this.controller.loadCalendar();
            this.controller.loadTodolist();
            this.controller.loadEventList();
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

    public static boolean isLeapYear(int year) {
        return (year-1752)%4 == 0;
    }

    static public int getStartingDate(int year, int month, int date){
        String monthList = "033614625035";
        String GeogList = "4206420";
        int yy = year % 100;
        int yearCode = (yy+(yy%4))%7;
        int monthCode = Character.getNumericValue(monthList.charAt(month));
        System.out.println(monthCode);
        int GeogCode = Character.getNumericValue(GeogList.charAt(year / 100 - 17));
        int Leap = isLeapYear(year)&(month == 0 || month == 1) ? -1 : 0;
        return (yearCode+monthCode+GeogCode+date+Leap)%7;
    }

    @Override
    public void elementRemoved(CalendarElement element) {
        this.elements.remove(element);
        this.fileManager.writeToFile(this.elements);
    }

    @Override
    public void elementChanged(CalendarElement element) {
        this.controller.loadCalendar();

        if (element.getClass().equals(Todo.class)) {
            this.controller.loadTodolist();
        }

        this.fileManager.writeToFile(this.elements);
    }

}
