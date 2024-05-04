package org.example.backend;

import org.example.backend.DTO.RoomDto;
import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.example.backend.Model.RoomType;
import org.example.backend.Repository.BookingRepository;
import org.example.backend.Service.BookingService;
import org.example.backend.Service.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingServiceTest2 {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @MockBean
    private BookingRepository mockRepo;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    // Items for repo:
    // Customers
    Customer c1 = new Customer("Venus", "111-1111111");
    Customer c2 = new Customer("Alex", "222-2222222");
    Customer c3 = new Customer("Ivar", "333-3333333");

    // Room Types
    RoomType rt1 = new RoomType(1L, "Single", 0, 1, 500);
    RoomType rt2 = new RoomType(2L, "Double", 1, 3, 1000);
    RoomType rt3 = new RoomType(3L, "Large double", 2, 4, 1500);

    // Rooms
    Room r1 = new Room(101, rt1);
    Room r2 = new Room(102, rt2);
    Room r3 = new Room(103, rt3);

    // Bookings
    Booking b1 = new Booking(1L, new java.sql.Date(df.parse("2024-06-01").getTime()),
            new java.sql.Date(df.parse("2024-06-07").getTime()), 1, 0, c1, r1);
    Booking b2 = new Booking(2L, new java.sql.Date(df.parse("2024-08-22").getTime()),
            new java.sql.Date(df.parse("2024-08-23").getTime()), 3, 1, c2, r2);
    Booking b3 = new Booking(3L, new java.sql.Date(df.parse("2024-12-23").getTime()),
            new java.sql.Date(df.parse("2024-12-25").getTime()), 4, 2, c3, r3);

    // Ansi colors for readability
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public BookingServiceTest2() throws ParseException {
    }

    @BeforeEach // WORKING 👍
    public void init() throws ParseException {
        when(mockRepo.findById(1L)).thenReturn(Optional.of(b1));
        when(mockRepo.findById(2L)).thenReturn(Optional.of(b2));
        when(mockRepo.findById(3L)).thenReturn(Optional.of(b3));
        when(mockRepo.findAll()).thenReturn(Arrays.asList(b1, b2, b3));
        // System.out.println(ANSI_GREEN + "@BeforeEach completed" + ANSI_RESET); // FOR READABILITY ONLY
    }

    @Test // WORKING 👍
    public void contextLoads() throws Exception {
        assertThat(bookingService).isNotNull();
        assertThat(mockRepo).isNotNull();
    }

    @Test // WORKING 👍
    public void checkMockRepo() throws ParseException {
        Booking expected1 = b1;
        Booking expected2 = b2;
        Booking expected3 = b3;
        Optional<Booking> optional1 = mockRepo.findById(1L);
        Optional<Booking> optional2 = mockRepo.findById(2L);
        Optional<Booking> optional3 = mockRepo.findById(3L);
        List<Booking> actualList = mockRepo.findAll();
        if (optional1.isPresent() && optional2.isPresent() && optional3.isPresent()) {
            Booking actual1 = optional1.get();
            Booking actual2 = optional2.get();
            Booking actual3 = optional3.get();
            List<Booking> expectedList = Arrays.asList(b1, b2, b3);
            Assertions.assertEquals(actual1, expected1);
            Assertions.assertEquals(actual2, expected2);
            Assertions.assertEquals(actual3, expected3);
            Assertions.assertEquals(actualList, expectedList);
        } else {
            Assertions.fail("Booking not found");
        }
    }

    @Test
    public void testCreateAndAddBookingToDatabase() {
        /*
        public void createAndAddBookingToDatabase(Date checkin, Date checkout, int guests, int extraBeds, long roomId, String name, String phone) {
        customerService.addCustomerWithoutID(name, phone);
        Customer customer = customerService.getCustomerByNameAndPhone(name, phone);
        Room room = roomRepository.findById(roomId).orElse(null);
        Booking booking = new Booking(checkin, checkout, guests, extraBeds, customer, room);
        bookingRepository.save(booking);
    }
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
        Assertions.assertTrue(bookingService.areDatesOverlapping(overLapping1, search));
        Assertions.assertTrue(bookingService.areDatesOverlapping(overLapping2, search));
        Assertions.assertTrue(bookingService.areDatesOverlapping(search, search));
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

    @Test
    public void testGetExtraBedsForBooking() {
        RoomDto singleRoom = roomService.roomToRoomDto(r1);
        RoomDto doubleRoom = roomService.roomToRoomDto(r2);
        RoomDto largeDoubleRoom = roomService.roomToRoomDto(r3);
        int expectedSingle = 0;
        int expectedDouble2Guests = 0;
        int expectedDouble3Guests = 1;
        int expectedLargeDouble2Guests = 0;
        int expectedLargeDouble3Guests = 1;
        int expectedLargeDouble4Guests = 2;
        int notExpected = 4;
//        int actualSingle = bookingService.getExtraBedsForBooking(singleRoom, 1);
        int actualDouble2Guests = bookingService.getExtraBedsForBooking(doubleRoom, 2);
        int actualDouble3Guests = bookingService.getExtraBedsForBooking(doubleRoom, 3);
        int actualLargeDouble2Guests = bookingService.getExtraBedsForBooking(largeDoubleRoom, 2);
        int actualLargeDouble3Guests = bookingService.getExtraBedsForBooking(largeDoubleRoom, 3);
        int actualLargeDouble4Guests = bookingService.getExtraBedsForBooking(largeDoubleRoom, 4);
//        Assertions.assertEquals(actualSingle, expectedSingle);
//        Assertions.assertNotEquals(actualSingle, notExpected);
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
    /*
    public int getExtraBedsForBooking(RoomDto room, int guests) {
        int beds = 0;
        switch (room.getRoomType().getType()) {
            case "Single":
                break;
            case "Double":
                if (guests == 3) {
                    beds = 1;
                    break;
                } else
                    break;
            case "Large double":
                if (guests == 4) {
                    beds = 2;
                    break;
                } else if (guests == 3) {
                    beds = 1;
                    break;
                } else
                    break;
        }
        return beds;
    }
    */
    }

    @Test
    public void testIsRoomAvailableOnDates() {

        /*
        public boolean isRoomAvailableOnDates(RoomDto room, Date checkin, Date checkout) {
        long roomId = room.getId();
        List<Date> datesInterval = createDateInterval(checkin, checkout);
        List<BookingDto> conflictingBookings = getAll().
                stream().
                filter(b -> b.getRoom().getId() == roomId).
                filter(b -> areDatesOverlapping(datesInterval, createDateInterval(b.getCheckinDate(), b.getCheckoutDate()))).
                toList();
        return conflictingBookings.isEmpty();
        */
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

}
