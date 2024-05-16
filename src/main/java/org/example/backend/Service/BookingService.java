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

    public BookingDto bookingToBookingDto (Booking b);

    public Booking bookindDtoToBooking (BookingDto b);

    public List<BookingDto> getAll();

    public void deleteBooking (BookingDto b);

    public void updateBooking (BookingDto b);

    public String deleteBookingById(Long id);

    public BookingDto findBookingById(Long id);

    public String updateBookingDates(Long id, String newCheckIn, String newCheckOut) throws ParseException;

    public void createAndAddBookingToDatabase (Date checkin, Date checkout, int guests, int extraBeds, long roomId, String name, String phone, String email) throws Exception;

    public int getExtraBedsForBooking(RoomDto room, int guests);

    public boolean isRoomAvailableOnDates(RoomDto room, java.sql.Date checkin, java.sql.Date checkout);

    public Booking getLastBooking();

}
