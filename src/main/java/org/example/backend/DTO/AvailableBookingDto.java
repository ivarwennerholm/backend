package org.example.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableBookingDto {
    private RoomDto room;
    private CustomerDto customer;
    private Date checkinDate;
    private Date checkoutDate;
    private int guestAmt;
    private int extraBedAmt;

    public AvailableBookingDto(RoomDto room, Date checkinDate, Date checkoutDate, int guestAmt, int extraBedAmt) {
        this.room = room;
        this.customer = null;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.guestAmt = guestAmt;
        this.extraBedAmt = extraBedAmt;
    }

}
