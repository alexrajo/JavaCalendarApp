package Calendar;

import javafx.fxml.FXML;

import java.time.LocalDate;
import java.util.HashMap;

public class TimeManager {

    private LocalDate currentDate;
    private LocalDate initialDate;
    private LocalDate selectedDate;
    private int selectedYear;
    private int monthDays;
    private int currentMonthDay;
    private int startDayAdjustment;
    private int selectedMonth;
    private int monthOffset;
    private HashMap<Integer, String> monthString = new HashMap<>();

    public TimeManager(){
        this.currentDate = LocalDate.now();
        this.initialDate = this.currentDate;
        this.selectedDate = this.currentDate;
        this.selectedYear = selectedDate.getYear();
        this.selectedMonth = selectedDate.getMonth().getValue();
        this.monthDays = selectedDate.getMonth().length(Calendar.isLeapYear(selectedYear));
        this.currentMonthDay = selectedDate.getDayOfMonth();
        this.monthOffset = 0;
        setStartDayAdjustment();
        monthString.put(1, "Januar");
        monthString.put(2, "Februar");
        monthString.put(3, "Mars");
        monthString.put(4, "April");
        monthString.put(5, "Mai");
        monthString.put(6, "Juni");
        monthString.put(7, "Juli");
        monthString.put(8, "August");
        monthString.put(9, "September");
        monthString.put(10, "Oktober");
        monthString.put(11, "November");
        monthString.put(12, "Desember");

    }

    public String monthToString() {
       return monthString.get(selectedMonth);
    }

    public void setStartDayAdjustment(){
        this.startDayAdjustment = LocalDate.of(this.selectedYear, this.selectedMonth, 1).getDayOfWeek().getValue();
    }

    public boolean isToday(int day){
        if (day <= this.monthDays) {
            return LocalDate.of(selectedYear, selectedMonth, day).equals(this.currentDate);
        }
        else {
            return false;
        }
    }


    public void changeMonthPrev(){
        this.monthOffset--;
        setSelectedDate(this.initialDate.minusMonths(-this.monthOffset));
    }

    public void changeMonthNext(){
        this.monthOffset++;
        setSelectedDate(this.initialDate.plusMonths(this.monthOffset));
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
        this.selectedYear = selectedDate.getYear();
        this.selectedMonth = selectedDate.getMonth().getValue();
        this.monthDays = selectedDate.getMonth().length(Calendar.isLeapYear(selectedYear));
        this.currentMonthDay = selectedDate.getDayOfMonth();
        this.setStartDayAdjustment();
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public int getMonthDays() {
        return monthDays;
    }

    public void setMonthDays(int monthDays) {
        this.monthDays = monthDays;
    }

    public int getCurrentMonthDay() {
        return currentMonthDay;
    }

    public void setCurrentMonthDay(int currentMonthDay) {
        this.currentMonthDay = currentMonthDay;
    }

    public int getStartDayAdjustment() {
        return startDayAdjustment;
    }

    public void setStartDayAdjustment(int startDayAdjustment) {
        this.startDayAdjustment = startDayAdjustment;
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public int getMonthOffset() {
        return monthOffset;
    }

    public void setMonthOffset(int monthOffset) {
        this.monthOffset = monthOffset;
    }
}
