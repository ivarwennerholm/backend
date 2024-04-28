package org.example.backend.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {

    private Long id;
    private Date checkinDate;
    private Date checkoutDate;
    private int guestAmt;
    private int extraBedAmt;
    private CustomerDto customer;
    private RoomDto room;

    public List<Date> getListOfAllDatesInBooking() {
        List<Date> allDatesInInBooking = new ArrayList<>();
        Date iterateDate = checkinDate;
        while (!iterateDate.after(checkoutDate)) {
            allDatesInInBooking.add(iterateDate);
            Calendar c = Calendar.getInstance();
            c.setTime(iterateDate);
            c.add(Calendar.DATE, 1);
            iterateDate = new java.sql.Date(c.getTimeInMillis());
        }
        return allDatesInInBooking;
    }

    public Long getNumberOfNightsInBooking() {
        long differenceMillis = this.checkoutDate.getTime() - this.checkinDate.getTime();
        return differenceMillis / (1000 * 60 * 60 * 24);
    }
}
