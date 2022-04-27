package Calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Calendar implements ElementListener {

    private List<CalendarElement> elements;
    private List<CalendarListener> listeners;
    private FileManager fileManager;

    public Calendar(String targetFileName, CalendarListener... listeners) {
        this.listeners = Arrays.asList(listeners);
        this.fileManager = new FileManager(targetFileName, this);

        this.elements = this.fileManager.readFromFile();
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
            this.notifyOfCalendarChange();
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

    public List<CalendarElement> getEventsOnDate(LocalDate date) {

        /**
         Filtering out all elements that do not have the desired date,
         returning the elements that are registered at the selected date
         */

        return this.getCalendarElements().stream().filter(
                element -> element.getDateTime().toLocalDate().equals(date)).filter(
                element -> element.getClass().equals(Event.class)).collect(Collectors.toList());
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
