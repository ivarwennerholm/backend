package org.example.backend;

import org.example.backend.Controller.BookingController;
import org.example.backend.DTO.BookingDto;
import org.example.backend.DTO.CustomerDto;
import org.example.backend.DTO.RoomDto;
import org.example.backend.DTO.RoomTypeDto;
import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.example.backend.Service.BookingService;
import org.example.backend.Service.CustomerService;
import org.example.backend.Service.Impl.BookingServiceImpl;
import org.example.backend.Service.RoomService;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AvailableBookingDtoTests {
    RoomService roomService = new RoomService() {
        @Override
        public RoomDto roomToRoomDto(Room r) {
            return null;
        }

        @Override
        public List<RoomDto> getAll() {
            return List.of();
        }

        @Override
        public Room roomDtoToRoom(RoomDto room) {
            return null;
        }
    };

    BookingService bookingService = new BookingService() {
        @Override
        public BookingDto bookingToBookingDto(Booking b) {
            return null;
        }

        @Override
        public Booking bookindDtoToBooking(BookingDto b) {
            return null;
        }

        @Override
        public List<BookingDto> getAll() {
            return List.of();
        }

        @Override
        public void deleteBooking(BookingDto b) {

        }

        @Override
        public void updateBooking(BookingDto b) {

        }

        @Override
        public boolean areDatesOverlapping(List<Date> searchDates, List<Date> bookingDates) {
            return false;
        }

        @Override
        public List<Date> createDateInterval(String checkin, String checkout) {
            return List.of();
        }

        @Override
        public Long getNumberOfDaysBetweenTwoDates(String checkin, String checkout) {
            return 0L;
        }

        @Override
        public int getExtraBedsForBooking(RoomDto room, int guests) {
            return 0;
        }
    };

    //BookingController bookingController = new BookingController(roomService, bookingService);

    @Test
    public void testTotallyOverlapping() throws ParseException {
        String checkinDate = "2024-01-01";
        String checkoutDate = "2024-01-10";
        List<Date> searchDates = bookingService.createDateInterval(checkinDate, checkoutDate);
        List<Date> bookingDates = searchDates;
        boolean result = bookingService.areDatesOverlapping(searchDates, bookingDates);
        assertTrue(result);
    }

    @Test
    public void testPartiallyOverlapping1() throws ParseException {
        String checkinDateSearch = "2024-01-01";
        String checkoutDateSearch = "2024-01-10";
        String checkinDateBooking = "2024-01-06";
        String checkoutDateBooking = "2024-01-12";
        List<Date> searchDates = bookingService.createDateInterval(checkinDateSearch, checkoutDateSearch);
        List<Date> bookingDates = bookingService.createDateInterval(checkinDateBooking, checkoutDateBooking);
        boolean result = bookingService.areDatesOverlapping(searchDates, bookingDates);
        assertTrue(result);
    }

    @Test
    public void testPartiallyOverlapping2() throws ParseException {
        String checkinDateSearch = "2024-01-06";
        String checkoutDateSearch = "2024-01-12";
        String checkinDateBooking = "2024-01-01";
        String checkoutDateBooking = "2024-01-10";
        List<Date> searchDates = bookingService.createDateInterval(checkinDateSearch, checkoutDateSearch);
        List<Date> bookingDates = bookingService.createDateInterval(checkinDateBooking, checkoutDateBooking);
        boolean result = bookingService.areDatesOverlapping(searchDates, bookingDates);
        assertTrue(result);
    }

    @Test
    public void testSearchCheckinSameDayAsBookingCheckout() throws ParseException {
        String checkinDateSearch = "2024-01-01";
        String checkoutDateSearch = "2024-01-10";
        String checkinDateBooking = "2024-01-10";
        String checkoutDateBooking = "2024-01-12";
        List<Date> searchDates = bookingService.createDateInterval(checkinDateSearch, checkoutDateSearch);
        List<Date> bookingDates = bookingService.createDateInterval(checkinDateBooking, checkoutDateBooking);
        boolean result = bookingService.areDatesOverlapping(searchDates, bookingDates);
        assertFalse(result);
    }

    @Test
    public void testBookingCheckoutSameDayAsSearchCheckin() throws ParseException {
        String checkinDateSearch = "2024-01-12";
        String checkoutDateSearch = "2024-01-14";
        String checkinDateBooking = "2024-01-10";
        String checkoutDateBooking = "2024-01-12";
        List<Date> searchDates = bookingService.createDateInterval(checkinDateSearch, checkoutDateSearch);
        List<Date> bookingDates = bookingService.createDateInterval(checkinDateBooking, checkoutDateBooking);
        boolean result = bookingService.areDatesOverlapping(searchDates, bookingDates);
        assertFalse(result);
    }

    @Test
    public void testGetExtraBedsForBooking() {
        RoomDto sing = new RoomDto(1L, 101, new RoomTypeDto(1L, "single", 0, 2, 1500));
        RoomDto doub = new RoomDto(2L, 102, new RoomTypeDto(2L, "double", 1, 3, 2500));
        RoomDto ldoub = new RoomDto(3L, 103, new RoomTypeDto(3L, "large_double", 2, 4, 3000));
        assertEquals(bookingService.getExtraBedsForBooking(sing, 1), 0);
        assertEquals(bookingService.getExtraBedsForBooking(doub, 1), 0);
        assertEquals(bookingService.getExtraBedsForBooking(doub, 2), 0);
        assertEquals(bookingService.getExtraBedsForBooking(doub, 3), 1);
        assertEquals(bookingService.getExtraBedsForBooking(ldoub, 1), 0);
        assertEquals(bookingService.getExtraBedsForBooking(ldoub, 2), 0);
        assertEquals(bookingService.getExtraBedsForBooking(ldoub, 3), 1);
        assertEquals(bookingService.getExtraBedsForBooking(ldoub, 4), 2);
    }

}
