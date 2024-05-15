package org.example.backend.Repository;

import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.customer = :customerId ")
    List<Booking> getAllBookingsForCustomer(@Param("customerId") long customerId);

    @Query("SELECT b FROM Booking b ORDER BY b.id DESC LIMIT 1")
    public Booking getLastBooking();
}
