package org.example.backend;

import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.example.backend.Model.RoomType;
import org.example.backend.Repository.BookingRepository;
import org.example.backend.Repository.RoomRepository;
import org.example.backend.Service.Impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookingServiceIT {

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository sut1;

    @Mock
    private BlacklistService blacklistService;

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Customer customer;
    private RoomType roomType;
    private Room room;
    private Booking booking;

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    // ANSI colors for readability
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @BeforeEach
    public void init() throws ParseException {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1L, "Venus", "111-1111111", "venus@pear.com");
        roomType = new RoomType(1L, "Single", 0, 1, 500);
        room = new Room(1L, 101, roomType);
        booking = new Booking(1L, new Date(df.parse("2024-06-01").getTime()),
                new Date(df.parse("2024-06-07").getTime()),
                1, 0, 12000.00, customer, room);
    }

    @Test
    public void testCreateAndAddBookingToDatabase() throws Exception {
        // ARRANGE
        Date checkin = new Date(df.parse("2024-06-01").getTime());
        Date checkout = new Date(df.parse("2024-06-07").getTime());
        int guests = 1;
        int extraBeds = 0;
        long roomId = room.getId();
        String customerName = customer.getName();
        String customerPhone = customer.getPhone();
        String customerEmail = customer.getEmail();
        when(blacklistService.isEmailValid(anyString())).thenReturn(true);
        when(customerService.getCustomerByNamePhoneAndEmail(anyString(), anyString(), anyString())).thenReturn(Optional.of(customer));
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
        when(discountService.getTotalPriceWithDiscounts(any(Date.class), any(Date.class), anyLong(), anyLong(), isNull(), anyBoolean())).thenReturn(12000.00);
        System.out.println("Discount get discount : " + discountService.getTotalPriceWithDiscounts(checkin, checkout, 1L, 1L, null, false));

        // ACT
        bookingService.createAndAddBookingToDatabase(checkin, checkout, guests, extraBeds, roomId, customerName, customerPhone, customerEmail);

        // ASSERT
        verify(sut1,times(1)).save(argThat(booking ->
                                        (booking.getCheckinDate() == checkin) &&
                                        (booking.getCheckoutDate() == checkout) &&
                                        (booking.getGuestAmt() == 1) &&
                                        (booking.getExtraBedAmt() == 0) &&
                                        (booking.getTotalPrice() == 12000.00) &&
                                        (booking.getCustomer() == customer) &&
                                        (booking.getRoom() == room)
        ));
    }

    @Nested
    @SpringBootTest
    class BookingServiceDBIT {
        @Autowired
        private BookingRepository sut2;
        @Test
        public void writeToDataBaseTest() throws ParseException {
            // ARRANGE
            Customer customer = new Customer(1L, "Venus", "111-1111111", "venus@pear.com");
            RoomType roomType = new RoomType(1L, "Single", 0, 1, 500);
            Room room = new Room(1L, 101, roomType);
            Booking booking = new Booking(1L, new Date(df.parse("2024-06-01").getTime()),
                    new Date(df.parse("2024-06-07").getTime()),
                    1, 0, 12000.00, customer, room);

            // ACT
            sut2.deleteAll();
            sut2.save(booking);

            // ASSERT
            assertEquals(1, sut2.findAll().size(), "Expected exactly one booking in the database");
        }

    }
}
