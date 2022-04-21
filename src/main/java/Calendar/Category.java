package Calendar;


import java.util.ArrayList;
import java.util.List;

public class Category {

    //En kalender skal ha et navn, en samling av kalenderelementer og er enten synlig eller ikke.
    private String name;
    private List<CalendarElement> elements = new ArrayList<>();
    private boolean visible;
    private ApplicationController listener;
    private FileManager fileManager;

    //Konstruktøren krever et navn, og settes til synlig av default.
    //Elementene vil kunne legges til i et sted etter kalenderen er opprettet.
    public Category(String name, ApplicationController listener) {
        this.name = name;
        this.listener = listener;
        this.fileManager = new FileManager(name);

        this.elements = this.fileManager.readFromFile();
    }

    /* AddCalenderElement tar inn Cal.Elem og valideres før det legges til i
    CalendarElements */
    public void addCalendarElement(CalendarElement calendarElement){
        if (ValidCalendarElement(calendarElement)){
            this.elements.add(calendarElement);
        }
        else throw new IllegalArgumentException("You can not add multiple instances of the same CalendarElement");
    }

    public List<CalendarElement> getcalendarElements(){
        return this.elements;
    }

    public CalendarElement getCalendarElement(int a){
        return elements.get(a);
    }

    /* ValidCalendar Element hindrer at duplikater av samme objekt kan legges til
    flere ganger */
    private boolean ValidCalendarElement(CalendarElement calendarElement){
        return !this.elements.contains(calendarElement);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        if (this.visible != visible) {
            this.visible = visible;
            this.listener.visibilityChanged(this, visible);
        }
    }

}
