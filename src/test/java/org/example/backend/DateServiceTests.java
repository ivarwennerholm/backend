package org.example.backend;

import org.example.backend.Service.DateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateServiceTests {

    private final DateService dateService = new DateService();

    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void areDatesOverlappingTest() throws ParseException {
        Date d1 = dateService.convertStringToDate("2024-07-01");
        Date d2 = dateService.convertStringToDate("2024-07-30");
        Date d3 = dateService.convertStringToDate("2024-07-31");
        Date d4 = dateService.convertStringToDate("2024-08-01");
        Date d5 = dateService.convertStringToDate("2024-08-03");
        Date d6 = dateService.convertStringToDate("2024-08-04");
        Date d7 = dateService.convertStringToDate("2024-08-16");
        List<Date> search = dateService.createDateInterval(d3, d5);
        List<Date> notOverLapping1 = dateService.createDateInterval(d1, d2);
        List<Date> notOverLapping2 = dateService.createDateInterval(d1, d3);
        List<Date> notOverLapping3 = dateService.createDateInterval(d5, d7);
        List<Date> notOverLapping4 = dateService.createDateInterval(d6, d7);
        List<Date> overLapping1 = dateService.createDateInterval(d1, d4);
        List<Date> overLapping2 = dateService.createDateInterval(d4, d7);
        assertTrue(dateService.areDatesOverlapping(overLapping1, search));
        assertTrue(dateService.areDatesOverlapping(overLapping2, search));
        assertTrue(dateService.areDatesOverlapping(search, search));
        Assertions.assertFalse(dateService.areDatesOverlapping(notOverLapping1, search));
        Assertions.assertFalse(dateService.areDatesOverlapping(notOverLapping2, search));
        Assertions.assertFalse(dateService.areDatesOverlapping(notOverLapping3, search));
        Assertions.assertFalse(dateService.areDatesOverlapping(notOverLapping4, search));
    }

    @Test
    public void createDateIntervalTest() throws ParseException {
        Date d1 = dateService.convertStringToDate("2024-07-31");
        Date d2 = dateService.convertStringToDate("2024-08-01");
        Date d3 = dateService.convertStringToDate("2024-08-02");
        Date d4 = dateService.convertStringToDate("2024-08-03");
        List<Date> expected = Arrays.asList(d1, d2, d3, d4);
        List<Date> actual = dateService.createDateInterval(d1, d4);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getNumberOfDaysBetweenTwoDatesTest() throws ParseException {
        Date d1 = dateService.convertStringToDate("2024-07-30");
        Date d2 = dateService.convertStringToDate("2024-07-31");
        Date d3 = dateService.convertStringToDate("2024-08-01");
        long expected1 = 1;
        long expected2 = 2;
        long notExpected = 3;
        long actual1 = dateService.getNumberOfDaysBetweenTwoDates(d1, d2);
        long actual2 = dateService.getNumberOfDaysBetweenTwoDates(d1, d3);
        Assertions.assertEquals(actual1, expected1);
        Assertions.assertNotEquals(actual1, notExpected);
        Assertions.assertEquals(actual2, expected2);
        Assertions.assertNotEquals(actual2, notExpected);
    }

    @Test
    public void convertStringToDateTest() throws ParseException {
        String expected = "2024-06-01";
        String notExpected = "2023-12-13";
        java.sql.Date expectedDate = new java.sql.Date(df.parse(expected).getTime());
        java.sql.Date notExpectedDate = new java.sql.Date(df.parse(notExpected).getTime());
        java.sql.Date actualDate = dateService.convertStringToDate(expected);
        Assertions.assertEquals(expectedDate, actualDate);
        Assertions.assertNotEquals(notExpectedDate, actualDate);
    }

    @Test
    public void isDateWithinAYearFromTodayTest() throws ParseException {
        Date today = new java.sql.Date(df.parse("2024-01-01").getTime());
        Date compare1 = new java.sql.Date(df.parse("2022-12-31").getTime());
        Date compare2 = new java.sql.Date(df.parse("2023-01-01").getTime());
        Date compare3 = new java.sql.Date(df.parse("2023-01-02").getTime());
        boolean actual1 = dateService.isDateWithinAYearFromToday(compare1, today, true);
        boolean actual2 = dateService.isDateWithinAYearFromToday(compare2, today, true);
        boolean actual3 = dateService.isDateWithinAYearFromToday(compare3, today, true);
        Assertions.assertFalse(actual1);
        Assertions.assertFalse(actual2);
        Assertions.assertTrue(actual3);
    }

}
