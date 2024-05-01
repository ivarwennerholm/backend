package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.BookingDto;
import org.example.backend.DTO.CustomerDto;
import org.example.backend.DTO.RoomDto;
import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.example.backend.Repository.BookingRepository;
import org.example.backend.Repository.CustomerRepository;
import org.example.backend.Repository.RoomRepository;
import org.example.backend.Service.BookingService;
import org.example.backend.Service.CustomerService;
import org.example.backend.Service.RoomService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final RoomService roomService;
    private final CustomerService customerService;
    private final RoomRepository roomRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;

    @Override
    public BookingDto bookingToBookingDto(Booking b) {
        RoomDto roomDto = roomService.roomToRoomDto(roomRepository.getReferenceById(b.getRoom().getId()));
        CustomerDto customerDto = customerService.customerToCustomerDto(customerRepository.getReferenceById(b.getCustomer().getId()));
        return BookingDto.builder().
                id(b.getId()).
                checkinDate(b.getCheckinDate()).
                checkoutDate(b.getCheckoutDate()).
                guestAmt(b.getGuestAmt()).
                extraBedAmt(b.getExtraBedAmt()).
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
                customer(customer).
                room(room).
                build();
    }

    @Override
    public List<BookingDto> getAll() {
        System.out.println(bookingRepository.findAll().stream().toList());
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
    public void deleteBookingById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public BookingDto findBookingById(Long id) {
        return bookingToBookingDto(bookingRepository.findById(id).get());
    }

    @Override
    public void updateBookingDates(Long id, String newCheckIn, String newCheckOut) throws ParseException {
        Booking b = bookingRepository.findById(id).get();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (!newCheckIn.isEmpty()){
            b.setCheckinDate(new java.sql.Date(df.parse(newCheckIn).getTime()));
        }
        if (!newCheckOut.isEmpty()){
            b.setCheckoutDate(new java.sql.Date(df.parse(newCheckOut).getTime()));
        }
        bookingRepository.save(b);
    }

    @Override
    public void createAndAddBookingToDatabase(Date checkin, Date checkout, int guests, int extraBeds, long roomId, String name, String phone) {
        customerService.addCustomerWithoutID(name, phone);
        Customer customer = customerService.getCustomerByNameAndPhone(name, phone);
        Room room = roomRepository.findById(roomId).orElse(null);
        Booking booking = new Booking(checkin, checkout, guests, extraBeds, customer, room);
        bookingRepository.save(booking);
    }

    @Override
    public boolean areDatesOverlapping(List<Date> searchDates, List<Date> bookingDates) {
        boolean output = false;
        if (searchDates.getFirst().equals(bookingDates.getLast()) || searchDates.getLast().equals(bookingDates.getFirst()))
            return output;
        for (Date date : searchDates) {
            if (bookingDates.contains(date))
                output = true;
        }
        return output;
    }

    @Override
    public List<Date> createDateInterval(Date checkin, Date checkout) {
        List<Date> interval = new ArrayList<>();
        Date iterateDate = checkin;
        while (!iterateDate.after(checkout)) {
            interval.add(iterateDate);
            Calendar c = Calendar.getInstance();
            c.setTime(iterateDate);
            c.add(Calendar.DATE, 1);
            iterateDate = new java.sql.Date(c.getTimeInMillis());
        }
        return interval;
    }

    @Override
    public Long getNumberOfDaysBetweenTwoDates(Date checkin, Date checkout) {
        long differenceMillis = checkout.getTime() - checkin.getTime();
        return differenceMillis / (1000 * 60 * 60 * 24);
    }

    @Override
    public int getExtraBedsForBooking(RoomDto room, int guests) {
        int beds = 0;
        switch (room.getRoomType().getType()) {
            case "single":
                break;
            case "double":
                if (guests == 3) {
                    beds = 1;
                    break;
                } else
                    break;
            case "large_double":
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
        List<Date> datesInterval = createDateInterval(checkin, checkout);
        List<BookingDto> conflictingBookings = getAll().
                stream().
                filter(b -> b.getRoom().getId() == roomId).
                filter(b -> areDatesOverlapping(datesInterval, createDateInterval(b.getCheckinDate(), b.getCheckoutDate()))).
                toList();
        return conflictingBookings.isEmpty();
    }

    @Override
    public Date convertStringToDate(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return new java.sql.Date(df.parse(date).getTime());
    }

}
