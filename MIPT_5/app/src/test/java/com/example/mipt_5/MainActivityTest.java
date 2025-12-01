package com.example.mipt_5;

import org.junit.Test;
import java.util.Calendar;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MainActivityTest {

    @Test
    public void isMarketOpen_duringWorkingHours_returnsTrue() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        assertTrue(MainActivity.isMarketOpen(cal));
    }

    @Test
    public void isMarketOpen_outsideWorkingHours_returnsFalse() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 8);
        assertFalse(MainActivity.isMarketOpen(cal));
    }

    @Test
    public void isMarketOpen_onWeekend_returnsFalse() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        assertFalse(MainActivity.isMarketOpen(cal));
    }
}
