package org.example.backend;

import org.example.backend.Controller.BookingController;
import org.example.backend.DTO.BookingDto;
import org.example.backend.DTO.CustomerDto;
import org.example.backend.DTO.RoomDto;
import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.example.backend.Service.BookingService;
import org.example.backend.Service.CustomerService;
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
    };
    CustomerService customerService = new CustomerService() {
        @Override
        public Customer customerDtoToCustomer(CustomerDto c) {
            return null;
        }

        @Override
        public CustomerDto customerToCustomerDto(Customer c) {
            return null;
        }

        @Override
        public List<CustomerDto> getAll() {
            return List.of();
        }

        @Override
        public String addCustomer(CustomerDto c) {
            return "";
        }

        @Override
        public String deleteCustomerByName(String name) {
            return "";
        }

        @Override
        public String updateCustomer(Long id, String name, String phone) {
            return "";
        }
    };
    BookingController bookingController = new BookingController(roomService, bookingService, customerService);

    @Test
    public void testTotallyOverlapping() throws ParseException {
        String checkinDate = "2024-01-01";
        String checkoutDate = "2024-01-10";
        List<Date> searchDates = bookingController.createDateInterval(checkinDate, checkoutDate);
        List<Date> bookingDates = searchDates;
        boolean result = bookingController.areDatesOverlapping(searchDates, bookingDates);
        assertTrue(result);
    }


    @Test
    public void testPartiallyOverlapping1() throws ParseException {
        String checkinDateSearch = "2024-01-01";
        String checkoutDateSearch = "2024-01-10";
        String checkinDateBooking = "2024-01-06";
        String checkoutDateBooking = "2024-01-12";
        List<Date> searchDates = bookingController.createDateInterval(checkinDateSearch, checkoutDateSearch);
        List<Date> bookingDates = bookingController.createDateInterval(checkinDateBooking, checkoutDateBooking);
        boolean result = bookingController.areDatesOverlapping(searchDates, bookingDates);
        assertTrue(result);
    }

    @Test
    public void testPartiallyOverlapping2() throws ParseException {
        String checkinDateSearch = "2024-01-06";
        String checkoutDateSearch = "2024-01-12";
        String checkinDateBooking = "2024-01-01";
        String checkoutDateBooking = "2024-01-10";
        List<Date> searchDates = bookingController.createDateInterval(checkinDateSearch, checkoutDateSearch);
        List<Date> bookingDates = bookingController.createDateInterval(checkinDateBooking, checkoutDateBooking);
        boolean result = bookingController.areDatesOverlapping(searchDates, bookingDates);
        assertTrue(result);
    }


    @Test
    public void testSearchCheckinSameDayAsBookingCheckout() throws ParseException {
        String checkinDateSearch = "2024-01-01";
        String checkoutDateSearch = "2024-01-10";
        String checkinDateBooking = "2024-01-10";
        String checkoutDateBooking = "2024-01-12";
        List<Date> searchDates = bookingController.createDateInterval(checkinDateSearch, checkoutDateSearch);
        List<Date> bookingDates = bookingController.createDateInterval(checkinDateBooking, checkoutDateBooking);
        boolean result = bookingController.areDatesOverlapping(searchDates, bookingDates);
        assertFalse(result);
    }

    @Test
    public void testBookingCheckoutSameDayAsSearchCheckin() throws ParseException {
        String checkinDateSearch = "2024-01-12";
        String checkoutDateSearch = "2024-01-14";
        String checkinDateBooking = "2024-01-10";
        String checkoutDateBooking = "2024-01-12";
        List<Date> searchDates = bookingController.createDateInterval(checkinDateSearch, checkoutDateSearch);
        List<Date> bookingDates = bookingController.createDateInterval(checkinDateBooking, checkoutDateBooking);
        boolean result = bookingController.areDatesOverlapping(searchDates, bookingDates);
        assertFalse(result);
    }

}
