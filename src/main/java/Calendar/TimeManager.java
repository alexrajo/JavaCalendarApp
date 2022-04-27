package Calendar;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    public static final List<String> monthString = Arrays.asList("Januar", "Februar", "Mars", "April", "Mai",
            "Juni", "Juli", "August", "September", "Oktober", "November", "Desember");

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

    }

    public String monthToString() {
       return monthString.get(selectedMonth-1);
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

    public static String minutesToFormattedTime(int minutes) {
        int carryMinutes = minutes%60;
        int hours = minutes/60;
        return String.valueOf(hours)+"t "+String.valueOf(carryMinutes)+"min";
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

    public LocalDate getDateFromIndexAndOffset(int index) {
        return LocalDate.now();
    }
}
