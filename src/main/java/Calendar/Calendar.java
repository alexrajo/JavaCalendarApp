package Calendar;
import java.util.Collection;

public class Calendar {

    //En kalender skal ha et navn, en samling av kalenderelementer og er enten synlig eller ikke.
    private String name;
    private Collection<CalendarElement> elements;
    private boolean visible;

    //Konstruktøren krever et navn, og settes til synlig av default.
    //Elementene vil kunne legges til i et sted etter kalenderen er opprettet.
    public Calendar(String name) {
        this.name = name;
        this.visible = true;
    }

    /* AddCalenderElement tar inn Cal.Elem og valideres før det legges til i
    CalendarElements */
    public void AddCalendarElement(CalendarElement calelem){
        if (ValidCalendarElement(calelem)){
            this.elements.add(calelem);
        }
        else throw new IllegalArgumentException("You can not add multiple instances of the same CalendarElement");
    }

    /* ValidCalendar Element hindrer at duplikater av samme objekt kan legges til
    flere ganger */
    private boolean ValidCalendarElement(CalendarElement calelem){
        return this.elements.contains(calelem);
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
        this.visible = visible;
    }

}
