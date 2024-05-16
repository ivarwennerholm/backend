package org.example.backend;

import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.example.backend.Model.RoomType;
import org.example.backend.Repository.BookingRepository;
import org.example.backend.Repository.CustomerRepository;
import org.example.backend.Repository.RoomRepository;
import org.example.backend.Repository.RoomTypeRepository;
import org.example.backend.Service.BookingService;
import org.example.backend.Service.CustomerService;
import org.example.backend.Service.Impl.*;
import org.example.backend.Service.RoomService;
import org.example.backend.Service.RoomTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class DiscountServiceTests {

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private RoomTypeRepository roomTypeRepository;
    @Mock
    private BookingRepository bookingRepository;

    private DiscountService discountService;
    private BookingService bookingService;
    private CustomerService customerService;
    private BlacklistService blacklistService;
    private RoomService roomService;
    private RoomTypeService roomTypeService;
    private DateService dateService;

    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    // Customers, roomtypes, rooms and bookings
    Customer c1; Customer c2; Customer c3;
    RoomType rt1;
    Room r1;
    Booking b1; Booking b2; Booking b3; Booking b4; Booking b5; Booking b6;

    // ANSI colors for readability
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @BeforeEach
    public void init() throws ParseException {
        // Mock
        MockitoAnnotations.openMocks(this);

        // Services
        roomTypeService = new RoomTypeServiceImpl(roomTypeRepository);
        blacklistService = new BlacklistService();
        dateService = new DateService();
        roomService = new RoomServiceImpl(roomRepository, roomTypeRepository, roomTypeService);
        discountService = new DiscountService(bookingRepository, roomRepository, customerRepository);
        bookingService = new BookingServiceImpl(roomService, customerService, roomRepository, customerRepository, bookingRepository, blacklistService, discountService);

        // Customers, room types & rooms
        c1 = new Customer(1L, "Venus", "111-1111111");
        c2 = new Customer(2L, "Alex", "222-2222222");
        c3 = new Customer(3L, "Ivar", "333-3333333");
        rt1 = new RoomType(1L, "Single", 0, 1, 500);
        r1 = new Room(1L, 101, rt1);

        // Bookings
        // TODO: Add method call for totalPrice
        double totalPrice = 10000;
        b1 = new Booking(1L, new java.sql.Date(df.parse("2024-06-01").getTime()), new java.sql.Date(df.parse("2024-06-07").getTime()), 1, 0, totalPrice, c1, r1);
        b2 = new Booking(2L, new java.sql.Date(df.parse("2024-07-03").getTime()), new java.sql.Date(df.parse("2024-07-07").getTime()), 1, 0, totalPrice, c1, r1);
        b3 = new Booking(3L, new java.sql.Date(df.parse("2024-12-15").getTime()), new java.sql.Date(df.parse("2024-12-16").getTime()), 1, 0, totalPrice, c1, r1);
        b4 = new Booking(1L, new java.sql.Date(df.parse("2024-03-01").getTime()),new java.sql.Date(df.parse("2024-03-20").getTime()), 1, 0, totalPrice, c1, r1);
        b5 = new Booking(2L, new java.sql.Date(df.parse("2024-08-10").getTime()),new java.sql.Date(df.parse("2024-08-19").getTime()), 3, 1, totalPrice, c2, r1);
        b6 = new Booking(3L, new java.sql.Date(df.parse("2024-12-02").getTime()),new java.sql.Date(df.parse("2024-12-12").getTime()), 4, 2, totalPrice, c3, r1);

        // Mock returns
        when(bookingRepository.getAllBookingsForCustomer(c1.getId())).thenReturn(Arrays.asList(b1, b2, b3, b4));
        when(bookingRepository.getAllBookingsForCustomer(c2.getId())).thenReturn(Collections.singletonList(b5));
        when(bookingRepository.getAllBookingsForCustomer(c3.getId())).thenReturn(Collections.singletonList(b6));

    }

    @Test
    public void getTotalPriceIncludingDiscountTest() throws ParseException {
        when(roomRepository.findById(1L)).thenReturn(Optional.ofNullable(r1));
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(c1));
        Date today = new java.sql.Date(df.parse("2025-05-15").getTime());
        Date checkin = new java.sql.Date(df.parse("2024-05-17").getTime());
        Date checkout = new java.sql.Date(df.parse("2024-06-12").getTime());
        double fullPrice = 13000;
        double discount1 = 40;
        double discount2 = 64.8;
        double discount3 = 257.904;
        double totalDiscount = discount1 + discount2 + discount3;
        double expected = fullPrice - totalDiscount;
        double actual = discountService.getTotalPriceWithDiscounts(checkin, checkout, r1.getId(), c1.getId(), today, true);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getDiscountSundayMondayTest() throws ParseException {
        Date checkin = new java.sql.Date(df.parse("2024-05-17").getTime());
        Date checkout = new java.sql.Date(df.parse("2024-06-12").getTime());
        double expected = 40;
        double actual = discountService.getDiscountSundayMonday(checkin, checkout, r1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getDiscountTwoOrMoreNights() throws ParseException {
        Date checkin1 = new java.sql.Date(df.parse("2024-05-17").getTime());
        Date checkout1 = new java.sql.Date(df.parse("2024-05-18").getTime());
        Date checkin2 = new java.sql.Date(df.parse("2024-05-31").getTime());
        Date checkout2 = new java.sql.Date(df.parse("2024-06-01").getTime());
        Date checkin3 = new java.sql.Date(df.parse("2024-05-17").getTime());
        Date checkout3 = new java.sql.Date(df.parse("2024-06-12").getTime());
        Date checkin4 = new java.sql.Date(df.parse("2024-06-17").getTime());
        Date checkout4 = new java.sql.Date(df.parse("2024-06-19").getTime());
        double totalPrice = 1000;
        double expectedYes = 5;
        double expectedNo = 0;
        double actual1 = discountService.getDiscountTwoOrMoreNights(checkin1, checkout1, totalPrice);
        double actual2 = discountService.getDiscountTwoOrMoreNights(checkin2, checkout2, totalPrice);
        double actual3 = discountService.getDiscountTwoOrMoreNights(checkin3, checkout3, totalPrice);
        double actual4 = discountService.getDiscountTwoOrMoreNights(checkin4, checkout4, totalPrice);
        Assertions.assertEquals(expectedNo, actual1);
        Assertions.assertEquals(expectedNo, actual2);
        Assertions.assertEquals(expectedYes, actual3);
        Assertions.assertEquals(expectedYes, actual4);
    }

    @Test
    public void getDiscountForReturningCustomerTest() throws ParseException {
        Date today = new java.sql.Date(df.parse("2025-05-15").getTime());
        double fullPrice = 10000;
        double expected1 = 200;
        double actual1 = discountService.getDiscountReturningCustomer(c3, fullPrice, today, true);
        double expected2 = 0;
        double actual2 = discountService.getDiscountReturningCustomer(c2, fullPrice, today, true);
        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    public void getNumberOfDiscountedNightsTest() throws ParseException {
        Date checkin1 = new java.sql.Date(df.parse("2024-05-14").getTime());
        Date checkout1 = new java.sql.Date(df.parse("2024-05-17").getTime());
        Date checkin2 = new java.sql.Date(df.parse("2024-05-18").getTime());
        Date checkout2 = new java.sql.Date(df.parse("2024-05-26").getTime());
        Date checkin3 = new java.sql.Date(df.parse("2024-05-17").getTime());
        Date checkout3 = new java.sql.Date(df.parse("2024-06-12").getTime());
        Date checkin4 = new java.sql.Date(df.parse("2024-05-19").getTime());
        Date checkout4 = new java.sql.Date(df.parse("2024-05-20").getTime());
        int expected1 = 0;
        int expected2 = 1;
        int expected3 = 4;
        int expected4 = 1;
        int actual1 = discountService.getNumberOfDiscountedNights(checkin1, checkout1);
        int actual2 = discountService.getNumberOfDiscountedNights(checkin2, checkout2);
        int actual3 = discountService.getNumberOfDiscountedNights(checkin3, checkout3);
        int actual4 = discountService.getNumberOfDiscountedNights(checkin4, checkout4);
        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
        Assertions.assertEquals(expected3, actual3);
        Assertions.assertEquals(expected4, actual4);
    }

    @Test
    public void doesCustomerHaveTenOrMoreNightsBookedInTheLastYearTest() throws ParseException {
        Date today = new java.sql.Date(df.parse("2025-05-15").getTime());
        Assertions.assertTrue(discountService.doesCustomerHaveTenOrMoreNightsBookedInTheLastYear(c1, today, true));
        Assertions.assertFalse(discountService.doesCustomerHaveTenOrMoreNightsBookedInTheLastYear(c2, today, true));
        Assertions.assertTrue(discountService.doesCustomerHaveTenOrMoreNightsBookedInTheLastYear(c3, today, true));
    }
}
