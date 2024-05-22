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
import org.example.backend.Service.Impl.*;
import org.example.backend.Service.RoomService;
import org.example.backend.Service.RoomTypeService;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookingServiceIT {

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Mock
    private RoomTypeService roomTypeService;
    // = new RoomTypeServiceImpl(roomTypeRepository);

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomService roomService;
    // = new RoomServiceImpl(roomRepository, roomTypeRepository, roomTypeService);

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private DateService dateService;

    @Mock
    private BlacklistService blacklistService;

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private BookingService sut1;
    //= new BookingServiceImpl(roomService, customerService, roomRepository, customerRepository, bookingRepository, blacklistService, discountService);

    //private final BookingService sut1 = new BookingServiceImpl(roomService, customerService, roomRepository, customerRepository, bookingRepository, blacklistService, discountService);

    //@Autowired
    //private BookingRepository sut2;

    private Customer customer;
    private RoomType roomType;
    private Room room;

    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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
        when(customerRepository.getCustomerByNamePhoneAndEmail(anyString(), anyString(), anyString())).thenReturn(Optional.of(customer));
        when(customerService.getCustomerByNamePhoneAndEmail(anyString(), anyString(), anyString())).thenReturn(Optional.of(customer));
        when(blacklistService.isEmailValid(anyString())).thenReturn(true);
        System.out.println("isEmailValid: " + blacklistService.isEmailValid("test"));
        //System.out.println("isEmailValid: " + sut1.blacklistService.isEmailValid("test"));

        when(dateService.getNumberOfDaysBetweenTwoDates(any(Date.class), any(Date.class))).thenReturn(7L);
        when(discountService.getTotalPriceWithDiscounts(any(Date.class), any(Date.class), anyLong(), anyLong(), any(Date.class), anyBoolean())).thenReturn(12000D);
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));

        // ACT
        sut1.createAndAddBookingToDatabase(checkin, checkout, guests, extraBeds, roomId, customerName, customerPhone, customerEmail);

        // ASSERT
        verify(bookingRepository, times(1)).save(any());
    }

        /*
        // ARRANGE
        Date checkin = new Date(df.parse("2024-06-01").getTime());
        Date checkout = new Date(df.parse("2024-06-07").getTime());
        int guests = 1;
        int extraBeds = 0;
        long roomId = room.getId();
        Optional<Customer> optional;
        String customerName = customer.getName();
        String customerPhone = customer.getPhone();
        String customerEmail = customer.getEmail();

        when(customerRepository.getCustomerByNamePhoneAndEmail(anyString(), anyString(), anyString())).thenReturn(Optional.ofNullable(customer));
        when(customerService.getCustomerByNamePhoneAndEmail(customerName, customerPhone, customerEmail)).thenReturn(Optional.ofNullable(customer));
        doReturn(7L).when(dateService).getNumberOfDaysBetweenTwoDates(any(Date.class), any(Date.class));

        when(discountService.getTotalPriceWithDiscounts(any(Date.class), any(Date.class), anyLong(), anyLong(), any(Date.class), anyBoolean())).thenReturn(12000D);
        optional = customerService.getCustomerByNamePhoneAndEmail(customerName, customerPhone, customerEmail);
        if (optional.isPresent())
            customer = optional.get();
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        // bookingRepository.deleteAll();

        // ACT
        sut.createAndAddBookingToDatabase(checkin, checkout, guests, extraBeds, roomId, customerName, customerPhone, customerEmail);

        // ASSURE
        verify(bookingRepository, times(1)).save(any());
        System.out.println(ANSI_GREEN + "Number of entries in database: " + bookingRepository.findAll().size() + ANSI_RESET);
        /*
        List<Booking> list = bookingRepository.findAll().stream().toList();
        for (Booking booking : list) {
            System.out.println(booking);
        }
        */

    // assertEquals(1, (long) bookingRepository.findAll().size());
    // verify(bookingRepository.times(3).).save

    // Verification
    // verify(customerService).getCustomerByNamePhoneAndEmail(customerName, customerPhone, customerEmail);
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

