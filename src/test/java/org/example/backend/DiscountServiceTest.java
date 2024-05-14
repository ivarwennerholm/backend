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

public class DiscountServiceTest {

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


    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    // Customers, roomtypes, rooms and bookings
    Customer c1;
    RoomType rt1;
    Room r1;
    Booking b1;

    // ANSI colors for readability
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @BeforeEach
    public void init() throws ParseException {
        // Services
        roomTypeService = new RoomTypeServiceImpl(roomTypeRepository);
        blacklistService = new BlacklistService();
        roomService = new RoomServiceImpl(roomRepository, roomTypeRepository, roomTypeService);
        bookingService = new BookingServiceImpl(roomService, customerService, roomRepository, customerRepository, bookingRepository, blacklistService);
        discountService = new DiscountService(bookingService);

        // Customers
        c1 = new Customer(1L, "Venus", "111-1111111");

        // Room Types
        rt1 = new RoomType(1L, "Single", 0, 1, 500);

        // Rooms
        r1 = new Room(1L, 101, rt1);

        // Bookings
        // TODO: Add method call for totalPrice
        double totalPrice = 10000;
        b1 = new Booking(1L, new java.sql.Date(df.parse("2024-06-01").getTime()),
                new java.sql.Date(df.parse("2024-06-07").getTime()), 1, 0, totalPrice, c1, r1);
    }

    @Test
    public void getTotalPriceIncludingDiscountTest() throws ParseException {
        Date checkin = new java.sql.Date(df.parse("2024-05-17").getTime());
        Date checkout = new java.sql.Date(df.parse("2024-06-12").getTime());
        double fullPrice = 13000;
        double discount1 = 40;
        double discount2 = 64.8;
        double discount3 = 0;
        double totalDiscount = discount1 + discount2 + discount3;
        double expected = fullPrice - totalDiscount;
        double actual = discountService.getTotalPriceWithDiscounts(checkin, checkout, r1, c1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getDiscountSundayMondayTest() throws ParseException {
        Date checkin = new java.sql.Date(df.parse("2024-05-17").getTime());
        Date checkout = new java.sql.Date(df.parse("2024-06-12").getTime());
        System.out.println(bookingService.getNumberOfDaysBetweenTwoDates(checkin, checkout));
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
}
