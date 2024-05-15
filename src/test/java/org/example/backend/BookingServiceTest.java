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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private BookingRepository bookRepo;

    @Mock
    private RoomRepository rmRepo;

    @Mock
    private RoomTypeRepository rmTypeRepo;

    @Mock
    private CustomerRepository cusRepo;

    @Mock
    private RoomService rS;

    @Mock
    private CustomerService cS;

    @InjectMocks
    private BookingServiceImpl bookService;

    @InjectMocks
    private RoomServiceImpl rmService;

    @InjectMocks
    private RoomTypeServiceImpl rmTypeService;

    @InjectMocks
    private CustomerServiceImpl cusService;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    Booking b1;
    Booking b2;
    @BeforeEach
    public void init() throws ParseException {
        b1 = Booking.builder()
                .id(1L)
                .checkinDate(new java.sql.Date(df.parse("2024-06-01").getTime()))
                .checkoutDate(new java.sql.Date(df.parse("2024-06-07").getTime()))
                .guestAmt(1)
                .extraBedAmt(0)
                .customer(new Customer(1L,"Venus", "111-1111111"))
                .room(new Room(1L,2099, new RoomType(1L, "single", 0, 1,500)))
                .build();

        b2 = Booking.builder()
                .id(2L)
                .checkinDate(new java.sql.Date(df.parse("2024-07-01").getTime()))
                .checkoutDate(new java.sql.Date(df.parse("2024-07-07").getTime()))
                .guestAmt(1)
                .extraBedAmt(0)
                .customer(new Customer(1L,"Venus", "111-1111111"))
                .room(new Room(1L,2099, new RoomType(1L, "single", 0, 1,500)))
                .build();

        when(bookRepo.findById(1L)).thenReturn(Optional.of(b1));
    }
    @Test
    public void deleteBookingByIdTest(){
        Assertions.assertTrue(bookService.deleteBookingById(1L).equals("delete booking id 1"));
    }

    @Test
    public void updateBookingDatesTest() throws ParseException {
        when(bookRepo.findAll()).thenReturn(Arrays.asList(b1,b2));
        Assertions.assertTrue(bookService.updateBookingDates(1L, "2024-06-08", "2024-06-31").equals("booking is updated"));

        Exception ex = assertThrows(RuntimeException.class,() -> bookService.updateBookingDates(1L, "2024-07-01", "2024-07-31"));
        Assertions.assertEquals("The room is occupied during this new booking period",ex.getMessage());
    }




//    @Test
//    public void findBookingByIdTest() throws ParseException {
//        Room r = new Room(1L, 2099, new RoomType(1L, "single", 1, 0, 500));
//        Customer c = new Customer(1L, "Venus", "111-1111111",null);
//        RoomType rt = new RoomType(1L, "single", 1,0,500);
//
//        RoomTypeDto rtDto = new RoomTypeDto(1L,"single",1,0,500);
//        RoomDto rDto = new RoomDto(1L,2099,rtDto);
//        CustomerDto cDto = new CustomerDto(1L,"Venus","111-1111111");
//
//        when(rmTypeRepo.getReferenceById(1L)).thenReturn(rt);
//        when(rmRepo.getReferenceById(1L)).thenReturn(r);
//        when(cusRepo.getReferenceById(1L)).thenReturn(c);
//
//        when(rmTypeService.roomTypeToRoomTypeDto(Mockito.any(RoomType.class))).thenReturn(rtDto);
//        when(rmService.roomToRoomDto(Mockito.any(Room.class))).thenReturn(rDto);
//        when(cusService.customerToCustomerDto(Mockito.any(Customer.class))).thenReturn(cDto);
//
//        BookingDto bDto = bookService.findBookingById(1L);
//
//        Assertions.assertTrue(bDto.getId()==1L);
//        Assertions.assertTrue(bDto.getCheckinDate().toString().equals("2024-06-01"));
//        Assertions.assertTrue(bDto.getCheckoutDate().toString().equals("2024-06-07"));
//        Assertions.assertTrue(bDto.getGuestAmt()==1);
//        Assertions.assertTrue(bDto.getExtraBedAmt()==0);
//        Assertions.assertTrue(bDto.getCustomer().getId()==1L);
//        Assertions.assertTrue(bDto.getCustomer().getName().equals("Venus"));
//        Assertions.assertTrue(bDto.getCustomer().getPhone().equals("111-1111111"));
//        Assertions.assertTrue(bDto.getRoom().getId()==1L);
//        Assertions.assertTrue(bDto.getRoom().getRoomNumber()==2099);
//        Assertions.assertTrue(bDto.getRoom().getRoomType().getId()==1L);
//        Assertions.assertTrue(bDto.getRoom().getRoomType().getType().equals("single"));
//        Assertions.assertTrue(bDto.getRoom().getRoomType().getMaxPerson()==1);
//        Assertions.assertTrue(bDto.getRoom().getRoomType().getMaxExtraBed()==0);
//        Assertions.assertTrue(bDto.getRoom().getRoomType().getPricePerNight()==500);
//    }
}
