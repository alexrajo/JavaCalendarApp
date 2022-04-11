package Calendar;
import java.util.*;
import java.time.LocalDate;
import java.time.Month;

public class Calendar {

    //En kalender skal ha et navn, en samling av kalenderelementer og er enten synlig eller ikke.
    private String name;
    private List<CalendarElement> elements = new ArrayList<>();
    private boolean visible;
    private HashMap<Integer, Integer> month = new HashMap<>();
    private LocalDate currentDate = LocalDate.now();
    private List<Integer> selectedDate;

    //Konstruktøren krever et navn, og settes til synlig av default.
    //Elementene vil kunne legges til i et sted etter kalenderen er opprettet.
    public Calendar(String name) {
        this.name = name;
        this.visible = true;
            month.put(1, 31);
            month.put(2, 28);
            month.put(3, 31);
            month.put(4, 30);
            month.put(5, 31);
            month.put(6, 30);
            month.put(7, 31);
            month.put(8, 31);
            month.put(9, 30);
            month.put(10, 31);
            month.put(11, 30);
            month.put(12, 31);
            this.selectedDate = new ArrayList<Integer>(Arrays.asList(this.getCurrentYear(), this.getCurrentMonth(), this.getCurrentDay()));
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
        this.visible = visible;
    }

    public int getCurrentYear(){
        return currentDate.getYear();
    }
    public int getCurrentDay(){
        return currentDate.getDayOfMonth();
    }
    public int getCurrentMonth(){
        return currentDate.getMonthValue();
    }
    public int getSelectedYear(){
        return this.selectedDate.get(0);
    }
    public int getSelectedMonth(){
        return this.selectedDate.get(1);
    }

    public int getSelectedDay(){
        return this.selectedDate.get(2);
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedDate.set(0, selectedYear);
    }

    public void setSelectedDay(int selectedDay) {
        this.selectedDate.set(2, selectedDay);
    }

    public void setSelectedMonth(int selectedMonth) {
        int a = getSelectedMonth()+selectedMonth;
        if (a > 12) {
            a = 1;
            setSelectedYear(this.getSelectedYear()+1);
        }
        if (a < 1) {
            a = 12;
            setSelectedYear(this.getSelectedYear()-1);
        }
        this.selectedDate.set(1, a);
    }

    public int getDaysInMonth(){
        return month.get(this.getSelectedMonth());
    }

}
