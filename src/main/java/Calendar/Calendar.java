package Calendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calendar implements ElementListener {

    private List<CalendarElement> elements;
    private List<CalendarListener> listeners;
    private FileManager fileManager;

    public Calendar(ApplicationController controller, String targetFileName, CalendarListener... listeners) {
        this.listeners = Arrays.asList(listeners);
        this.fileManager = new FileManager(targetFileName, this);

        this.elements = this.fileManager.readFromFile();
    }

    public Calendar(String targetFileName, CalendarListener... listeners) {
        this(null, targetFileName, listeners);
    }

    public boolean addCalendarElement(CalendarElement calendarElement) {
        if (this.elements.contains(calendarElement)) return false; //Element was not added to calendar

        this.elements.add(calendarElement);
        this.fileManager.writeToFile(this.elements);
        this.notifyOfCalendarChange();

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

    private void notifyOfCalendarChange() {
        for (CalendarListener listener : this.listeners) {
            listener.calendarChanged();
        }
    }

    @Override
    public void elementChanged(CalendarElement element) {
        this.notifyOfCalendarChange();
        this.updateFile();
    }

}
