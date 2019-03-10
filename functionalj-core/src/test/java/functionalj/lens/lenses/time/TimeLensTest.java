package functionalj.lens.lenses.time;

import static functionalj.lens.lenses.java.time.LocalDateLens.theLocalDate;
import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import lombok.val;

public class TimeLensTest {

    @Test
    public void testLocalDate() {
        val localDate1 = LocalDate.of(2019, 3, 3);
        val localDate2 = theLocalDate.day.changeTo(5).apply(localDate1);
        assertEquals("2019-03-03", localDate1.toString());
        assertEquals("2019-03-05", localDate2.toString());
        
        val localDate3 = theLocalDate.month.toMay.apply(localDate2);
        assertEquals("2019-05-05", localDate3.toString());
        
        val localDateTime1 = theLocalDate.atTime(6, 0).apply(localDate3);
        assertEquals("2019-05-05T06:00", localDateTime1.toString());
        
        val period1 = theLocalDate.periodFrom(localDate1).apply(localDate3);
        assertEquals("P2M2D", period1.toString());
    }

}
