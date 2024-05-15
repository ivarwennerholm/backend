package org.example.backend.Repository;

import org.example.backend.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // For ascending order without search term
    @Query("SELECT b FROM Booking b WHERE b.customer = :customerId ")
    List<Booking> getAllBookingsForCustomer(@Param("customerId") long customerId);
}
