package org.example.backend.Service.Impl;

import lombok.NoArgsConstructor;
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
    public List<Date> createDateInterval(String checkin, String checkout) throws ParseException {
        List<Date> interval = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date checkinDate = new java.sql.Date(df.parse(checkin).getTime());
        Date checkoutDate = new java.sql.Date(df.parse(checkout).getTime());
        Date iterateDate = checkinDate;
        while (!iterateDate.after(checkoutDate)) {
            interval.add(iterateDate);
            Calendar c = Calendar.getInstance();
            c.setTime(iterateDate);
            c.add(Calendar.DATE, 1);
            iterateDate = new java.sql.Date(c.getTimeInMillis());
        }
        return interval;
    }

    @Override
    public Long getNumberOfDaysBetweenTwoDates(String checkin, String checkout) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date checkinDate = new java.sql.Date(df.parse(checkin).getTime());
        Date checkoutDate = new java.sql.Date(df.parse(checkout).getTime());
        long differenceMillis = checkoutDate.getTime() - checkinDate.getTime();
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
}
