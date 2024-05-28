package org.example.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    private Date checkinDate;
    private Date checkoutDate;
    private int guestAmt;
    private int extraBedAmt;
    private double totalPrice;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Room room;

    public Booking(Date checkinDate, Date checkoutDate, int guestAmt, int extraBedAmt, double totalPrice, Customer customer, Room room) {
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.guestAmt = guestAmt;
        this.extraBedAmt = extraBedAmt;
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.room = room;
    }

}
