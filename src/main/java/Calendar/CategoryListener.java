package Calendar;

public interface CategoryListener {
    public void visibilityChanged(Category category, boolean visible);
    public void elementAdded(Category category, CalendarElement element);
    public void elementRemoved(Category category, CalendarElement element);
    public void elementChanged(Category category, CalendarElement element);
}
