package org.example.backend;

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
import org.example.backend.Utils.BlacklistURLProvider;
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

import static org.mockito.Mockito.*;

public class BookingService2Tests {

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

    @BeforeEach
    public void init() throws ParseException {
        MockitoAnnotations.openMocks(this);

        // Services:
        roomTypeService = new RoomTypeServiceImpl(roomTypeRepository);
        customerService = new CustomerServiceImpl(customerRepository);
        blacklistService = new BlacklistService(new BlacklistURLProvider());
        dateService = new DateService();

        roomService = new RoomServiceImpl(roomRepository, roomTypeRepository, roomTypeService);
        sut = new BookingServiceImpl(roomService, customerService, roomRepository, customerRepository, bookingRepository, blacklistService, discountService);
        discountService = new DiscountService(bookingRepository, roomRepository, customerRepository);

        // Customers
        c1 = new Customer(1L, "Venus", "111-1111111");
        c2 = new Customer(2L, "Alex", "222-2222222");
        c3 = new Customer(3L, "Ivar", "333-3333333");

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
        b1 = new Booking(1L, new java.sql.Date(df.parse("2024-06-01").getTime()),
                new java.sql.Date(df.parse("2024-06-07").getTime()), 1, 0, 7500, c1, r1);
        b2 = new Booking(2L, new java.sql.Date(df.parse("2024-08-22").getTime()),
                new java.sql.Date(df.parse("2024-08-23").getTime()), 3, 1, 12400, c2, r2);
        b3 = new Booking(3L, new java.sql.Date(df.parse("2024-12-23").getTime()),
                new java.sql.Date(df.parse("2024-12-25").getTime()), 4, 2, 12000, c3, r3);

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
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(b1, b2, b3));

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
        Assertions.assertTrue(sut.isRoomAvailableOnDates(rdto1, d1, d2));
        Assertions.assertTrue(sut.isRoomAvailableOnDates(rdto1, d1, d3));
        Assertions.assertFalse(sut.isRoomAvailableOnDates(rdto1, d1, d4));
        Assertions.assertFalse(sut.isRoomAvailableOnDates(rdto1, d3, d5));
        Assertions.assertFalse(sut.isRoomAvailableOnDates(rdto1, d4, d7));
        Assertions.assertTrue(sut.isRoomAvailableOnDates(rdto1, d5, d7));
        Assertions.assertTrue(sut.isRoomAvailableOnDates(rdto1, d6, d7));
    }

    /*
    @Test // TODO: Not working
    public void testCreateAndAddBookingToDatabase() throws ParseException {
        // Setup
        String checkinStr = "2024-06-01";
        String checkoutStr = "2024-06-07";
        Date checkin = new java.sql.Date(df.parse(checkinStr).getTime());
        Date checkout = new java.sql.Date(df.parse(checkoutStr).getTime());
        int guests = 1;
        int extraBeds = 0;
        Room mockRoom = r1;
        long roomId = mockRoom.getId();
        Customer mockCustomer = c1;
        String customerName = mockCustomer.getName();
        String customerPhone = mockCustomer.getPhone();

        // Mock necessary behavior
        // when(customerService.getCustomerByNameAndPhone(customerName, customerPhone)).thenReturn(mockCustomer);
        // when(roomRepository.findById(roomId)).thenReturn(Optional.of(mockRoom));

        // Execution
        // bookingService.createAndAddBookingToDatabase(checkin, checkout, guests, extraBeds, roomId, customerName, customerPhone);

        // Verification
        // verify(customerService).getCustomerByNameAndPhone(customerName, customerPhone);
        // verify(roomRepository).findById(roomId);
        // verify(bookingRepository).save(any(Booking.class)); // Ensure that the save method is called with a Booking object

        // Date checkin = new java.sql.Date(df.parse("2024-06-01").getTime());
        // Date checkout = new java.sql.Date(df.parse("2024-06-07").getTime());
        // int guestAmt = 1;
        // int extraBedAmt = 0;
        // Room room = r1;
        // Long roomId = room.getId();
        // Customer customer = c1;
        // String name = customer.getName();
        // String phone = customer.getPhone();
        // Booking booking = new Booking(checkin, checkout, guestAmt, extraBedAmt, customer, room);
    }
    */

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
        Assertions.assertEquals(actualSingle, expectedSingle);
        Assertions.assertNotEquals(actualSingle, notExpected);
        Assertions.assertEquals(actualDouble2Guests, expectedDouble2Guests);
        Assertions.assertNotEquals(actualDouble2Guests, notExpected);
        Assertions.assertEquals(actualDouble3Guests, expectedDouble3Guests);
        Assertions.assertNotEquals(actualDouble3Guests, notExpected);
        Assertions.assertEquals(actualLargeDouble2Guests, expectedLargeDouble2Guests);
        Assertions.assertNotEquals(actualLargeDouble2Guests, notExpected);
        Assertions.assertEquals(actualLargeDouble3Guests, expectedLargeDouble3Guests);
        Assertions.assertNotEquals(actualLargeDouble3Guests, notExpected);
        Assertions.assertEquals(actualLargeDouble4Guests, expectedLargeDouble4Guests);
        Assertions.assertNotEquals(actualLargeDouble4Guests, notExpected);
    }

}
