package org.example.backend.Service;

import org.example.backend.DTO.BookingDto;
import org.example.backend.Model.Booking;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface BookingService {

    public BookingDto bookingToBookingDto (Booking b);

    public Booking bookindDtoToBooking (BookingDto b);

    public List<BookingDto> getAll();

    public void deleteBooking (BookingDto b);

    public void updateBooking (BookingDto b);

    public void deleteBookingById(Long id);

    public BookingDto findBookingById(Long id);

    public void updateBookingDates(Long id, String newCheckIn, String newCheckOut) throws ParseException;
}
