package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.*;
import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.example.backend.Repository.BookingRepository;
import org.example.backend.Repository.CustomerRepository;
import org.example.backend.Repository.RoomRepository;
import org.example.backend.Service.BookingService;
import org.example.backend.Service.CustomerService;
import org.example.backend.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final RoomService roomService;
    private final CustomerService customerService;
    private final RoomRepository roomRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final BlacklistService blackService;
//    private final DiscountService discountService;
    private DateService dateService = new DateService();

    @Override
    public BookingDto bookingToBookingDto(Booking b) {
        RoomDto roomDto = roomService.roomToRoomDto(roomRepository.findById(b.getRoom().getId()).orElse(null));
        CustomerDto customerDto = customerService.customerToCustomerDto(customerRepository.findById(b.getCustomer().getId()).orElse(null));
        return BookingDto.builder().
                id(b.getId()).
                checkinDate(b.getCheckinDate()).
                checkoutDate(b.getCheckoutDate()).
                guestAmt(b.getGuestAmt()).
                extraBedAmt(b.getExtraBedAmt()).
                totalPrice(b.getTotalPrice()).
                customer(customerDto).
                room(roomDto).
                build();
    }

    @Override
    public Booking bookindDtoToBooking(BookingDto bd) {
        Room room = roomService.roomDtoToRoom(bd.getRoom());
        Customer customer = customerService.customerDtoToCustomer(bd.getCustomer());
        return Booking.builder().
                id(bd.getId()).
                checkinDate(bd.getCheckinDate()).
                checkoutDate(bd.getCheckoutDate()).
                guestAmt(bd.getGuestAmt()).
                extraBedAmt(bd.getExtraBedAmt()).
                totalPrice(bd.getTotalPrice()).
                customer(customer).
                room(room).
                build();
    }

    @Override
    public List<BookingDto> getAll() {
        return bookingRepository.findAll().stream().map(this::bookingToBookingDto).toList();
    }

    @Override
    public void deleteBooking(BookingDto bd) {
        bookingRepository.deleteById(bd.getId());
    }

    @Override
    public void updateBooking(BookingDto bd) {
        bookingRepository.deleteById(bd.getId());
        bookingRepository.save(bookindDtoToBooking(bd));
    }

    @Override
    public String deleteBookingById(Long id) {
        Booking b = bookingRepository.findById(id).get();
        bookingRepository.delete(b);
        return "delete booking id " + b.getId();
    }

    //venus tar
    @Override
    public BookingDto findBookingById(Long id) {
        return bookingToBookingDto(bookingRepository.findById(id).get());
    }

    //venus tar
//    @Override
//    public void updateBookingDates(Long id, String newCheckIn, String newCheckOut) throws ParseException {
//        Booking b = bookingRepository.findById(id).get();
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        if (!newCheckIn.isEmpty()){
//            b.setCheckinDate(new java.sql.Date(df.parse(newCheckIn).getTime()));
//        }
//        if (!newCheckOut.isEmpty()){
//            b.setCheckoutDate(new java.sql.Date(df.parse(newCheckOut).getTime()));
//        }
//        bookingRepository.save(b);
//    }

    @Override
    public String updateBookingDates(Long id, String newCheckIn, String newCheckOut) throws ParseException {
        Room r = bookingRepository.findById(id).get().getRoom();
        Booking originBooking = bookingRepository.findById(id).get();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date wantedCheckIn = originBooking.getCheckinDate();
        Date wantedCheckOut = originBooking.getCheckoutDate();

        if (!newCheckIn.isEmpty()){
            wantedCheckIn = new java.sql.Date (df.parse(newCheckIn).getTime());
        }
        if (!newCheckOut.isEmpty()){
            wantedCheckOut = new java.sql.Date (df.parse(newCheckOut).getTime());
        }
        List<Date> datesInterval = dateService.createDateInterval(wantedCheckIn, wantedCheckOut);
        List<Booking> conflictBookList =
                bookingRepository.findAll().stream()
                .filter(k -> k.getRoom().getId()==r.getId())
                .filter(k -> k.getId()!=id)
                .filter(k -> dateService.areDatesOverlapping(datesInterval, dateService.createDateInterval(k.getCheckinDate(), k.getCheckoutDate())))
                .toList();

        if (conflictBookList.isEmpty()){
            originBooking.setCheckinDate(wantedCheckIn);
            originBooking.setCheckoutDate(wantedCheckOut);
            bookingRepository.save(originBooking);
            return "booking is updated";
        } else {
            throw new RuntimeException("The room is occupied during this new booking period");
        }
    }

    @Override
    public void createAndAddBookingToDatabase(Date checkin, Date checkout, int guests, int extraBeds, long roomId, String name, String phone, String email) throws Exception {
        System.out.println("BookingService: createAndAddBookingToDatabase initiated");
        if (blackService.isEmailValid(email)){
            Customer customer;
            Optional <Customer> optional = customerService.getCustomerByNamePhoneAndEmail(name, phone, email);
            System.out.println("BookingService: optional retrieved");
            if (optional.isPresent()) {
                System.out.println("BookingService: optional.isPresent");
                customer = optional.get();
                // customer = customerService.getCustomerByNamePhoneAndEmail(name, phone, email);
                System.out.println("BookingService: customer found in database = " + customer);
            } else {
                customerService.addCustomerWithoutID(name, phone, email);
                customer = customerService.getLastCustomer();
                System.out.println("BookingService: customer created = " + customer);
            }
            Room room = roomRepository.findById(roomId).orElse(null);
            System.out.println("BookingService: room = " + room);
            System.out.println("BookingService: customer = " + customer);
            assert room != null;
            // double totalPrice = 10000;
            //Customer customer = customerRepository.findById(customerId).orElse(null);
//            double totalPrice = discountService.getTotalPriceWithDiscounts(checkin, checkout, roomId, customer, null, false);
//            System.out.println("BookingService: totalPrice = " + totalPrice);
            Booking booking = new Booking(checkin, checkout, guests, extraBeds, 10000, customer, room);
            bookingRepository.save(booking);
            System.out.println("BookingService: booking saved = " + booking);
        } else {
            throw new Exception("Booking unsuccessful. Please contact Admin.");
        }
    }

    @Override
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

    @Override
    public boolean isRoomAvailableOnDates(RoomDto room, Date checkin, Date checkout) {
        long roomId = room.getId();
        List<Date> datesInterval = dateService.createDateInterval(checkin, checkout);
        List<BookingDto> conflictingBookings = getAll().
                stream().
                filter(b -> b.getRoom().getId() == roomId).
                filter(b -> dateService.areDatesOverlapping(datesInterval, dateService.createDateInterval(b.getCheckinDate(), b.getCheckoutDate()))).
                toList();
        return conflictingBookings.isEmpty();
    }

    @Override
    public Booking getLastBooking() {
        return bookingRepository.getLastBooking();
    }




}
