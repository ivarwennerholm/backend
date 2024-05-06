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
import org.example.backend.Service.Impl.BookingServiceImpl;
import org.example.backend.Service.Impl.CustomerServiceImpl;
import org.example.backend.Service.Impl.RoomServiceImpl;
import org.example.backend.Service.Impl.RoomTypeServiceImpl;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class BookingServiceTest2 {

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
    private BookingService bookingService;

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
    BookingDto bdto1;
    BookingDto bdto2;
    BookingDto bdto3;

    // ANSI colors for readability
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @BeforeEach
    public void init() throws ParseException {
        MockitoAnnotations.openMocks(this);

        // Services:
        roomTypeService = new RoomTypeServiceImpl(roomTypeRepository);
        customerService = new CustomerServiceImpl(customerRepository);
        roomService = new RoomServiceImpl(roomRepository, roomTypeRepository, roomTypeService);
        bookingService = new BookingServiceImpl(roomService, customerService, roomRepository, customerRepository, bookingRepository);

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
                new java.sql.Date(df.parse("2024-06-07").getTime()), 1, 0, c1, r1);
        b2 = new Booking(2L, new java.sql.Date(df.parse("2024-08-22").getTime()),
                new java.sql.Date(df.parse("2024-08-23").getTime()), 3, 1, c2, r2);
        b3 = new Booking(3L, new java.sql.Date(df.parse("2024-12-23").getTime()),
                new java.sql.Date(df.parse("2024-12-25").getTime()), 4, 2, c3, r3);

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

        // Booking DTO:S
        bdto1 = bookingService.bookingToBookingDto(b1);
        bdto2 = bookingService.bookingToBookingDto(b2);
        bdto3 = bookingService.bookingToBookingDto(b3);
    }

    @Test // WORKING 👍
    public void contextLoads() throws Exception {
        assertThat(roomRepository).isNotNull();
        assertThat(customerRepository).isNotNull();
        assertThat(roomTypeRepository).isNotNull();
        assertThat(bookingRepository).isNotNull();
        assertThat(roomTypeService).isNotNull();
        assertThat(roomService).isNotNull();
        assertThat(customerService).isNotNull();
        assertThat(bookingService).isNotNull();
    }

    @Test // WORKING 👍
    public void testIsRoomAvailableOnDates() throws ParseException {
        Date d1 = bookingService.convertStringToDate("2024-05-25");
        Date d2 = bookingService.convertStringToDate("2024-05-31");
        Date d3 = bookingService.convertStringToDate("2024-06-01");
        Date d4 = bookingService.convertStringToDate("2024-06-04");
        Date d5 = bookingService.convertStringToDate("2024-06-07");
        Date d6 = bookingService.convertStringToDate("2024-06-08");
        Date d7 = bookingService.convertStringToDate("2024-06-13");
        Assertions.assertTrue(bookingService.isRoomAvailableOnDates(rdto1, d1, d2));
        Assertions.assertTrue(bookingService.isRoomAvailableOnDates(rdto1, d1, d3));
        Assertions.assertFalse(bookingService.isRoomAvailableOnDates(rdto1, d1, d4));
        Assertions.assertFalse(bookingService.isRoomAvailableOnDates(rdto1, d3, d5));
        Assertions.assertFalse(bookingService.isRoomAvailableOnDates(rdto1, d4, d7));
        Assertions.assertTrue(bookingService.isRoomAvailableOnDates(rdto1, d5, d7));
        Assertions.assertTrue(bookingService.isRoomAvailableOnDates(rdto1, d6, d7));
    }

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



        /*
        Date checkin = new java.sql.Date(df.parse("2024-06-01").getTime());
        Date checkout = new java.sql.Date(df.parse("2024-06-07").getTime());
        int guestAmt = 1;
        int extraBedAmt = 0;
        Room room = r1;
        Long roomId = room.getId();
        Customer customer = c1;
        String name = customer.getName();
        String phone = customer.getPhone();
        Booking booking = new Booking(checkin, checkout, guestAmt, extraBedAmt, customer, room);
        */
    }

    @Test // WORKING 👍
    public void testAreDatesOverlapping() throws ParseException {
        Date d1 = bookingService.convertStringToDate("2024-07-01");
        Date d2 = bookingService.convertStringToDate("2024-07-30");
        Date d3 = bookingService.convertStringToDate("2024-07-31");
        Date d4 = bookingService.convertStringToDate("2024-08-01");
        Date d5 = bookingService.convertStringToDate("2024-08-03");
        Date d6 = bookingService.convertStringToDate("2024-08-04");
        Date d7 = bookingService.convertStringToDate("2024-08-16");
        List<Date> search = bookingService.createDateInterval(d3, d5);
        List<Date> notOverLapping1 = bookingService.createDateInterval(d1, d2);
        List<Date> notOverLapping2 = bookingService.createDateInterval(d1, d3);
        List<Date> notOverLapping3 = bookingService.createDateInterval(d5, d7);
        List<Date> notOverLapping4 = bookingService.createDateInterval(d6, d7);
        List<Date> overLapping1 = bookingService.createDateInterval(d1, d4);
        List<Date> overLapping2 = bookingService.createDateInterval(d4, d7);
        assertTrue(bookingService.areDatesOverlapping(overLapping1, search));
        assertTrue(bookingService.areDatesOverlapping(overLapping2, search));
        assertTrue(bookingService.areDatesOverlapping(search, search));
        Assertions.assertFalse(bookingService.areDatesOverlapping(notOverLapping1, search));
        Assertions.assertFalse(bookingService.areDatesOverlapping(notOverLapping2, search));
        Assertions.assertFalse(bookingService.areDatesOverlapping(notOverLapping3, search));
        Assertions.assertFalse(bookingService.areDatesOverlapping(notOverLapping4, search));
    }

    @Test // WORKING 👍
    public void testCreateDateInterval() throws ParseException {
        Date d1 = bookingService.convertStringToDate("2024-07-31");
        Date d2 = bookingService.convertStringToDate("2024-08-01");
        Date d3 = bookingService.convertStringToDate("2024-08-02");
        Date d4 = bookingService.convertStringToDate("2024-08-03");
        List<Date> expected = Arrays.asList(d1, d2, d3, d4);
        List<Date> actual = bookingService.createDateInterval(d1, d4);
        Assertions.assertEquals(expected, actual);
    }

    @Test // WORKING 👍
    public void testGetNumberOfDaysBetweenTwoDates() throws ParseException {
        Date d1 = bookingService.convertStringToDate("2024-07-30");
        Date d2 = bookingService.convertStringToDate("2024-07-31");
        Date d3 = bookingService.convertStringToDate("2024-08-01");
        long expected1 = 1;
        long expected2 = 2;
        long notExpected = 3;
        long actual1 = bookingService.getNumberOfDaysBetweenTwoDates(d1, d2);
        long actual2 = bookingService.getNumberOfDaysBetweenTwoDates(d1, d3);
        Assertions.assertEquals(actual1, expected1);
        Assertions.assertNotEquals(actual1, notExpected);
        Assertions.assertEquals(actual2, expected2);
        Assertions.assertNotEquals(actual2, notExpected);
    }

    @Test // WORKING 👍
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
        int actualSingle = bookingService.getExtraBedsForBooking(singleRoom, 1);
        int actualDouble2Guests = bookingService.getExtraBedsForBooking(doubleRoom, 2);
        int actualDouble3Guests = bookingService.getExtraBedsForBooking(doubleRoom, 3);
        int actualLargeDouble2Guests = bookingService.getExtraBedsForBooking(largeDoubleRoom, 2);
        int actualLargeDouble3Guests = bookingService.getExtraBedsForBooking(largeDoubleRoom, 3);
        int actualLargeDouble4Guests = bookingService.getExtraBedsForBooking(largeDoubleRoom, 4);
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

    @Test // WORKING 👍
    public void testConvertStringToDate() throws ParseException {
        String expected = "2024-06-01";
        String notExpected = "2023-12-13";
        java.sql.Date expectedDate = new java.sql.Date(df.parse(expected).getTime());
        java.sql.Date notExpectedDate = new java.sql.Date(df.parse(notExpected).getTime());
        java.sql.Date actualDate = bookingService.convertStringToDate(expected);
        Assertions.assertEquals(expectedDate, actualDate);
        Assertions.assertNotEquals(notExpectedDate, actualDate);
    }

    @Test // WORKING 👍
    public void testRoomTypeRepository() {
        Optional<RoomType> optional1 = roomTypeRepository.findById(1L);
        Optional<RoomType> optional2 = roomTypeRepository.findById(2L);
        Optional<RoomType> optional3 = roomTypeRepository.findById(3L);
        List<RoomType> actualList = roomTypeRepository.findAll();
        List<RoomType> expectedList = Arrays.asList(rt1, rt2, rt3);
        if (optional1.isPresent() && optional2.isPresent() && optional3.isPresent()) {
            RoomType actual1 = optional1.get();
            RoomType actual2 = optional2.get();
            RoomType actual3 = optional3.get();
            Assertions.assertEquals(actual1, rt1);
            Assertions.assertEquals(actual2, rt2);
            Assertions.assertEquals(actual3, rt3);
            Assertions.assertEquals(actualList, expectedList);
        } else
            Assertions.fail(ANSI_RED + "Roomtype(s) not found" + ANSI_RESET);
    }

    @Test // WORKING 👍
    public void testRoomTypeService() {
        List<RoomTypeDto> actual = roomTypeService.getAll();
        List<RoomTypeDto> expected = Arrays.asList(rtdto1, rtdto2, rtdto3);
        Assertions.assertEquals(actual, expected);
    }

    @Test // WORKING 👍
    public void testCustomerRepository() {
        Optional<Customer> optional1 = customerRepository.findById(1L);
        Optional<Customer> optional2 = customerRepository.findById(2L);
        Optional<Customer> optional3 = customerRepository.findById(3L);
        List<Customer> actualList = customerRepository.findAll();
        List<Customer> expectedList = Arrays.asList(c1, c2, c3);
        if (optional1.isPresent() && optional2.isPresent() && optional3.isPresent()) {
            Customer actual1 = optional1.get();
            Customer actual2 = optional2.get();
            Customer actual3 = optional3.get();
            Assertions.assertEquals(actual1, c1);
            Assertions.assertEquals(actual2, c2);
            Assertions.assertEquals(actual3, c3);
            Assertions.assertEquals(actualList, expectedList);
        } else
            Assertions.fail(ANSI_RED + "Customer(s) not found" + ANSI_RESET);
    }

    @Test // WORKING 👍
    public void testCustomerService() {
        List<CustomerDto> actual = customerService.getAll();
        List<CustomerDto> expected = Arrays.asList(cdto1, cdto2, cdto3);
        Assertions.assertEquals(actual, expected);
    }

    @Test // WORKING 👍
    public void testRoomRepository() {
        Optional<Room> optional1 = roomRepository.findById(1L);
        Optional<Room> optional2 = roomRepository.findById(2L);
        Optional<Room> optional3 = roomRepository.findById(3L);
        List<Room> actualList = roomRepository.findAll();
        List<Room> expectedList = Arrays.asList(r1, r2, r3);
        if (optional1.isPresent() && optional2.isPresent() && optional3.isPresent()) {
            Room actual1 = optional1.get();
            Room actual2 = optional2.get();
            Room actual3 = optional3.get();
            Assertions.assertEquals(actual1, r1);
            Assertions.assertEquals(actual2, r2);
            Assertions.assertEquals(actual3, r3);
            Assertions.assertEquals(actualList, expectedList);
        } else
            Assertions.fail(ANSI_RED + "Room(s) not found" + ANSI_RESET);
    }

    @Test // WORKING 👍
    public void testRoomService() {
        List<CustomerDto> actual = customerService.getAll();
        List<CustomerDto> expected = Arrays.asList(cdto1, cdto2, cdto3);
        Assertions.assertEquals(actual, expected);
    }

    @Test // WORKING 👍
    public void testBookingRepository() {
        Optional<Booking> optional1 = bookingRepository.findById(1L);
        Optional<Booking> optional2 = bookingRepository.findById(2L);
        Optional<Booking> optional3 = bookingRepository.findById(3L);
        List<Booking> actualList = bookingRepository.findAll();
        List<Booking> expectedList = Arrays.asList(b1, b2, b3);
        if (optional1.isPresent() && optional2.isPresent() && optional3.isPresent()) {
            Booking actual1 = optional1.get();
            Booking actual2 = optional2.get();
            Booking actual3 = optional3.get();
            Assertions.assertEquals(actual1, b1);
            Assertions.assertEquals(actual2, b2);
            Assertions.assertEquals(actual3, b3);
            Assertions.assertEquals(actualList, expectedList);
        } else
            Assertions.fail(ANSI_RED + "Booking(s) not found" + ANSI_RESET);
    }

    @Test // WORKING 👍
    public void testBookingService() {
        List<BookingDto> actual = bookingService.getAll();
        List<BookingDto> expected = Arrays.asList(bdto1, bdto2, bdto3);
        Assertions.assertEquals(actual, expected);
    }

}
