package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DateService {

    public boolean areDatesOverlapping(List<Date> searchDates, List<Date> bookingDates) {
        boolean output = false;
        if (searchDates.get(0).equals(bookingDates.get(bookingDates.size() - 1)) || searchDates.get(searchDates.size() - 1).equals(bookingDates.get(0)))
            return output;
        for (Date date : searchDates) {
            if (bookingDates.contains(date))
                output = true;
        }
        return output;
    }

    public List<Date> createDateInterval(Date checkin, Date checkout) {
        List<Date> interval = new ArrayList<>();
        Date iterateDate = checkin;
        while (!iterateDate.after(checkout)) {
            interval.add(iterateDate);
            Calendar c = Calendar.getInstance();
            c.setTime(iterateDate);
            c.add(Calendar.DATE, 1);
            iterateDate = new java.sql.Date(c.getTimeInMillis());
        }
        return interval;
    }

    public long getNumberOfDaysBetweenTwoDates(Date checkin, Date checkout) {
        long differenceMillis = checkout.getTime() - checkin.getTime();
        return differenceMillis / (1000 * 60 * 60 * 24);
    }

    public Date convertStringToDate(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return new java.sql.Date(df.parse(date).getTime());
    }

    public boolean isDateWithinAYearFromToday(Date date, Date dateForTesting, boolean test) {
        Date today;
        if (!test) {
            LocalDate localDateToday = LocalDate.now();
            today = java.sql.Date.valueOf(localDateToday);
        } else {
            today = dateForTesting;
        }
        Calendar aYearAgo = Calendar.getInstance();
        aYearAgo.setTime(today);
        aYearAgo.add(Calendar.YEAR, -1);
        Calendar dateToCompare = Calendar.getInstance();
        dateToCompare.setTime(date);
        return aYearAgo.before(dateToCompare);
    }

}
