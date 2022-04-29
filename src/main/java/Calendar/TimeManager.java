package Calendar;

import java.time.LocalDate;
import java.util.Arrays;
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
    private static final List<String> monthStrings = Arrays.asList("Januar", "Februar", "Mars", "April", "Mai",
            "Juni", "Juli", "August", "September", "Oktober", "November", "Desember");

    public TimeManager(LocalDate date){
        this.currentDate = date;
        this.initialDate = this.currentDate;
        this.selectedDate = this.currentDate;
        this.selectedYear = selectedDate.getYear();
        this.selectedMonth = selectedDate.getMonth().getValue();
        this.monthDays = selectedDate.getMonth().length(isLeapYear(selectedYear));
        this.currentMonthDay = selectedDate.getDayOfMonth();
        this.monthOffset = 0;
        setStartDayAdjustment();
    }

    public TimeManager() {
        this(LocalDate.now());
    }

    public String monthToString() {
       return monthStrings.get(selectedMonth-1);
    }

    private void setStartDayAdjustment(){
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
        setSelectedDate(this.getInitialDate().plusMonths(this.monthOffset));
    }

    public void changeMonthNext(){
        this.monthOffset++;
        setSelectedDate(this.getInitialDate().plusMonths(this.monthOffset));
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
        this.monthDays = selectedDate.getMonth().length(isLeapYear(selectedYear));
        this.currentMonthDay = selectedDate.getDayOfMonth();
        this.setStartDayAdjustment();
    }

    /**
     * @param minutes the amount of minutes you want to format
     * @return The amount of time input in days, hours & minutes. The final return String excludes day, hour and minute values that are zero.
     */
    public static String minutesToFormattedTime(int minutes) {
        int carryMinutes = minutes%60;
        int hours = minutes/60;
        int days = hours/24;
        int carryHours = hours-days*24;

        String daysText = days > 0 ? String.valueOf(days)+"d " : "";
        String hoursText = carryHours > 0 ? String.valueOf(carryHours)+"t " : "";
        String minutesText = carryMinutes > 0 ? String.valueOf(carryMinutes)+"min " : "";
        return String.format("%s%s%s", daysText, hoursText, minutesText);
    }

    public static boolean isLeapYear(int year) {
        return (year-1752)%4 == 0;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.setMonthOffset(this.getMonthOffset()+(selectedYear-this.getSelectedYear())*12);
        this.setSelectedDate(this.getSelectedDate().withYear(selectedYear));
    }

    public int getMonthDays() {
        return monthDays;
    }

    private void setMonthDays(int monthDays) {
        this.monthDays = monthDays;
    }

    public int getCurrentMonthDay() {
        return currentMonthDay;
    }

    public void setCurrentMonthDay(int currentMonthDay) {
        this.currentMonthDay = currentMonthDay;
    }

    /**
     * @return the amount of days further the first day of the current month should appear in the week
     */
    public int getStartDayAdjustment() {
        return startDayAdjustment;
    }

    public void setStartDayAdjustment(int startDayAdjustment) {
        this.startDayAdjustment = startDayAdjustment;
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public static String getMonthName(int monthNumber) {
        return monthStrings.get(monthNumber-1);
    }

    public void setSelectedMonth(int selectedMonth) throws IllegalArgumentException {
        if (selectedMonth < 1 || selectedMonth > 12) throw new IllegalArgumentException("Selected month must be between 1-12");

        this.setMonthOffset(this.getMonthOffset()+(selectedMonth-this.getSelectedMonth()));
        this.setSelectedDate(this.getSelectedDate().withMonth(selectedMonth));
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
