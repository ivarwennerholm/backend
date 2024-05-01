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
public class BookingDto {

    private Long id;
    private Date checkinDate;
    private Date checkoutDate;
    private int guestAmt;
    private int extraBedAmt;
    private CustomerDto customer;
    private RoomDto room;

}