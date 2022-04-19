package Calendar;
import java.io.File;
import java.util.*;

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
    }

    /* AddCalenderElement tar inn Cal.Elem og valideres før det legges til i
    CalendarElements */
    public void AddCalendarElement(CalendarElement calelem){
        if (ValidCalendarElement(calelem)){
            this.elements.add(calelem);
        }
        else throw new IllegalArgumentException("You can not add multiple instances of the same CalendarElement");
    }

    public List<CalendarElement> getCalelems(){
        List<CalendarElement> a = new ArrayList<CalendarElement>(elements);
        return a;
    }

    public CalendarElement getCalendarElement(int a){
        return elements.get(a);
    }

    /* ValidCalendar Element hindrer at duplikater av samme objekt kan legges til
    flere ganger */
    private boolean ValidCalendarElement(CalendarElement calelem){
        return !this.elements.contains(calelem);
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
