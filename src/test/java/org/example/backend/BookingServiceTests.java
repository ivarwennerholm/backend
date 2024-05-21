package org.example.backend;

import org.example.backend.DTO.BookingDto;
import org.example.backend.DTO.CustomerDto;
import org.example.backend.DTO.RoomDto;
import org.example.backend.DTO.RoomTypeDto;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceTests {

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private RoomTypeRepository roomTypeRepository;
    @Mock
    private BookingRepository bookingRepository;

    private RoomTypeService roomTypeService;
    private RoomService roomService;
    private CustomerService customerService;
    private BlacklistService blacklistService;
    private DiscountService discountService;
    private DateService dateService;

    private BookingService sut;

    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    // Customers, roomtypes, rooms and bookings
    Customer c1;
    Customer c2;
    Customer c3;
    CustomerDto cdto1;
    CustomerDto cdto2;
    CustomerDto cdto3;
    RoomType rt1;
    RoomType rt2;
    RoomType rt3;
    RoomTypeDto rtdto1;
    RoomTypeDto rtdto2;
    RoomTypeDto rtdto3;
    Room r1;
    Room r2;
    Room r3;
    RoomDto rdto1;
    RoomDto rdto2;
    RoomDto rdto3;
    Booking b1;
    Booking b2;
    Booking b3;
    Booking b4;

    @BeforeEach
    public void init() throws ParseException {
        MockitoAnnotations.openMocks(this);

        // Services:
        roomTypeService = new RoomTypeServiceImpl(roomTypeRepository);
        customerService = new CustomerServiceImpl(customerRepository);
        blacklistService = new BlacklistService();
        dateService = new DateService();

        roomService = new RoomServiceImpl(roomRepository, roomTypeRepository, roomTypeService);
        discountService = new DiscountService(bookingRepository, roomRepository, customerRepository);
        sut = new BookingServiceImpl(roomService, customerService, roomRepository, customerRepository, bookingRepository, blacklistService, discountService);

        // Customers
        c1 = new Customer(1L, "Venus", "111-1111111", "venus@pear.com");
        c2 = new Customer(2L, "Alex", "222-2222222", "alex@apple.com");
        c3 = new Customer(3L, "Ivar", "333-3333333", "ivar@orange.com");

        // Customer DTO:s
        cdto1 = customerService.customerToCustomerDto(c1);
        cdto2 = customerService.customerToCustomerDto(c2);
        cdto3 = customerService.customerToCustomerDto(c3);

        // Room Types
        rt1 = new RoomType(1L, "Single", 0, 1, 500);
        rt2 = new RoomType(2L, "Double", 1, 3, 1000);
        rt3 = new RoomType(3L, "Large double", 2, 4, 1500);

        // Room Type DTO:S
        rtdto1 = roomTypeService.roomTypeToRoomTypeDto(rt1);
        rtdto2 = roomTypeService.roomTypeToRoomTypeDto(rt2);
        rtdto3 = roomTypeService.roomTypeToRoomTypeDto(rt3);

        // Rooms
        r1 = new Room(1L, 101, rt1);
        r2 = new Room(2L, 102, rt2);
        r3 = new Room(3L, 103, rt3);

        // Bookings
        b1 = Booking.builder().
                id(1L).
                checkinDate(new java.sql.Date(df.parse("2024-06-01").getTime())).
                checkoutDate(new java.sql.Date(df.parse("2024-06-07").getTime())).
                guestAmt(1).
                extraBedAmt(0).
                totalPrice(7500).
                customer(c1).
                room(r1).build();
        b2 = Booking.builder().
                id(2L).
                checkinDate(new java.sql.Date(df.parse("2024-08-22").getTime())).
                checkoutDate(new java.sql.Date(df.parse("2024-08-23").getTime())).
                guestAmt(3).
                extraBedAmt(1).
                totalPrice(12400).
                customer(c1).
                room(r2).
                build();
        b3 = Booking.builder().
                id(3L).
                checkinDate(new java.sql.Date(df.parse("2024-12-23").getTime())).
                checkoutDate(new java.sql.Date(df.parse("2024-12-25").getTime())).
                guestAmt(4).
                extraBedAmt(2).
                totalPrice(12000).
                customer(c3).
                room(r3).
                build();
        b4 = Booking.builder().
                id(4L).
                checkinDate(new java.sql.Date(df.parse("2024-07-01").getTime())).
                checkoutDate(new java.sql.Date(df.parse("2024-07-07").getTime())).
                guestAmt(1).
                extraBedAmt(0).
                customer(c1).
                room(r1).
                build();

        // MOCK RETURNS:
        // roomTypeRepository
        when(roomTypeRepository.findById(1L)).thenReturn(Optional.of(rt1));
        when(roomTypeRepository.findById(2L)).thenReturn(Optional.of(rt2));
        when(roomTypeRepository.findById(3L)).thenReturn(Optional.of(rt3));
        when(roomTypeRepository.findAll()).thenReturn(Arrays.asList(rt1, rt2, rt3));
        // customerRepository
        when(customerRepository.findById(1L)).thenReturn(Optional.of(c1));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(c2));
        when(customerRepository.findById(3L)).thenReturn(Optional.of(c3));
        when(customerRepository.findAll()).thenReturn(Arrays.asList(c1, c2, c3));
        // roomRepository
        when(roomRepository.findById(1L)).thenReturn(Optional.of(r1));
        when(roomRepository.findById(2L)).thenReturn(Optional.of(r2));
        when(roomRepository.findById(3L)).thenReturn(Optional.of(r3));
        when(roomRepository.findAll()).thenReturn(Arrays.asList(r1, r2, r3));
        // bookingRepository
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(b1));
        when(bookingRepository.findById(2L)).thenReturn(Optional.of(b2));
        when(bookingRepository.findById(3L)).thenReturn(Optional.of(b3));
        when(bookingRepository.findById(4L)).thenReturn(Optional.of(b4));
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(b1, b2, b3, b4));

        // Room DTO:s
        rdto1 = roomService.roomToRoomDto(r1);
        rdto2 = roomService.roomToRoomDto(r2);
        rdto3 = roomService.roomToRoomDto(r3);
    }

    @Test
    public void testIsRoomAvailableOnDates() throws ParseException {
        Date d1 = dateService.convertStringToDate("2024-05-25");
        Date d2 = dateService.convertStringToDate("2024-05-31");
        Date d3 = dateService.convertStringToDate("2024-06-01");
        Date d4 = dateService.convertStringToDate("2024-06-04");
        Date d5 = dateService.convertStringToDate("2024-06-07");
        Date d6 = dateService.convertStringToDate("2024-06-08");
        Date d7 = dateService.convertStringToDate("2024-06-13");
        assertTrue(sut.isRoomAvailableOnDates(rdto1, d1, d2));
        assertTrue(sut.isRoomAvailableOnDates(rdto1, d1, d3));
        Assertions.assertFalse(sut.isRoomAvailableOnDates(rdto1, d1, d4));
        Assertions.assertFalse(sut.isRoomAvailableOnDates(rdto1, d3, d5));
        Assertions.assertFalse(sut.isRoomAvailableOnDates(rdto1, d4, d7));
        assertTrue(sut.isRoomAvailableOnDates(rdto1, d5, d7));
        assertTrue(sut.isRoomAvailableOnDates(rdto1, d6, d7));
    }

    @Test
    public void testGetExtraBedsForBooking() {
        RoomDto singleRoom = rdto1;
        RoomDto doubleRoom = rdto2;
        RoomDto largeDoubleRoom = rdto3;
        int expectedSingle = 0;
        int expectedDouble2Guests = 0;
        int expectedDouble3Guests = 1;
        int expectedLargeDouble2Guests = 0;
        int expectedLargeDouble3Guests = 1;
        int expectedLargeDouble4Guests = 2;
        int notExpected = 4;
        int actualSingle = sut.getExtraBedsForBooking(singleRoom, 1);
        int actualDouble2Guests = sut.getExtraBedsForBooking(doubleRoom, 2);
        int actualDouble3Guests = sut.getExtraBedsForBooking(doubleRoom, 3);
        int actualLargeDouble2Guests = sut.getExtraBedsForBooking(largeDoubleRoom, 2);
        int actualLargeDouble3Guests = sut.getExtraBedsForBooking(largeDoubleRoom, 3);
        int actualLargeDouble4Guests = sut.getExtraBedsForBooking(largeDoubleRoom, 4);
        assertEquals(actualSingle, expectedSingle);
        Assertions.assertNotEquals(actualSingle, notExpected);
        assertEquals(actualDouble2Guests, expectedDouble2Guests);
        Assertions.assertNotEquals(actualDouble2Guests, notExpected);
        assertEquals(actualDouble3Guests, expectedDouble3Guests);
        Assertions.assertNotEquals(actualDouble3Guests, notExpected);
        assertEquals(actualLargeDouble2Guests, expectedLargeDouble2Guests);
        Assertions.assertNotEquals(actualLargeDouble2Guests, notExpected);
        assertEquals(actualLargeDouble3Guests, expectedLargeDouble3Guests);
        Assertions.assertNotEquals(actualLargeDouble3Guests, notExpected);
        assertEquals(actualLargeDouble4Guests, expectedLargeDouble4Guests);
        Assertions.assertNotEquals(actualLargeDouble4Guests, notExpected);
    }

    @Test
    public void deleteBookingByIdTest() {
        assertEquals("delete booking id 1", sut.deleteBookingById(1L));
    }

    @Test
    public void updateBookingDatesTest() throws ParseException {
        assertEquals("booking is updated", sut.updateBookingDates(1L, "2024-06-01", "2024-06-22"));
        Exception ex = assertThrows(RuntimeException.class, () -> sut.updateBookingDates(1L, "2024-07-01", "2024-07-06"));
        Assertions.assertEquals("The room is occupied during this new booking period", ex.getMessage());
    }

    @Test
    public void findBookingByIdTest() throws ParseException {
        BookingDto result = sut.findBookingById(1L);

        assertEquals(1L, (long) result.getId());
        assertEquals("2024-06-01", result.getCheckinDate().toString());
        assertEquals("2024-06-07", result.getCheckoutDate().toString());
        assertEquals(1, result.getGuestAmt());
        assertEquals(0, result.getExtraBedAmt());
        assertEquals(7500, result.getTotalPrice());
        assertEquals(1L, (long) result.getCustomer().getId());
        assertEquals("Venus", result.getCustomer().getName());
        assertEquals("111-1111111", result.getCustomer().getPhone());
        assertEquals("venus@pear.com", result.getCustomer().getEmail());
        assertEquals(1L, result.getRoom().getId());
        assertEquals(101, result.getRoom().getRoomNumber());
        assertEquals(1L, (long) result.getRoom().getRoomType().getId());
        assertEquals("Single", result.getRoom().getRoomType().getType());
        assertEquals(1, result.getRoom().getRoomType().getMaxPerson());
        assertEquals(0, result.getRoom().getRoomType().getMaxExtraBed());
        assertEquals(500, result.getRoom().getRoomType().getPricePerNight());

    }

}
