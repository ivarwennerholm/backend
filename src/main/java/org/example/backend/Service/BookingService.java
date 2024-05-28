package org.example.backend.Service;

import org.example.backend.DTO.BookingDto;
import org.example.backend.Model.Booking;

import java.sql.Date;
import java.util.List;
import java.text.ParseException;
import java.util.Optional;

import org.example.backend.DTO.RoomDto;
import org.example.backend.Model.Customer;

public interface BookingService {

    BookingDto bookingToBookingDto (Booking b);

    Booking bookindDtoToBooking (BookingDto b);

    List<BookingDto> getAll();

    void deleteBooking (BookingDto b);

    void updateBooking (BookingDto b);

    String deleteBookingById(Long id);

    BookingDto findBookingById(Long id);

    String updateBookingDates(Long id, String newCheckIn, String newCheckOut) throws ParseException;

    void createAndAddBookingToDatabase (Date checkin, Date checkout, int guests, int extraBeds, long roomId, String name, String phone, String email) throws Exception;

    int getExtraBedsForBooking(RoomDto room, int guests);

    boolean isRoomAvailableOnDates(RoomDto room, java.sql.Date checkin, java.sql.Date checkout);

    Booking getLastBooking();

}
