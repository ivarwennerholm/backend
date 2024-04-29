package org.example.backend.Controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.AvailableBookingDto;
import org.example.backend.DTO.BookingDto;
import org.example.backend.DTO.RoomDto;
import org.example.backend.Service.BookingService;
import org.example.backend.Service.CustomerService;
import org.example.backend.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("booking")
public class BookingController {

    private final RoomService roomService;
    private final BookingService bookingService;
    private final CustomerService customerService;
//
//    public BookingController() {
//        this.roomService = null;
//        this.bookingService = null;
//        this.customerService = null;
//    }

    @RequestMapping("/add")
    public String addBooking() {
        return "searchBooking";
    }

    @RequestMapping("/getAvailable")
    public String getAvailableBookings(@RequestParam int guests, @RequestParam String checkin, @RequestParam String checkout, Model model) throws ParseException {
        model.addAttribute("guests", guests);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        long differenceDays = getNumberOfDaysBetweenTwoDates(checkin, checkout);
        model.addAttribute("nights", differenceDays);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date checkinDate = new java.sql.Date(df.parse(checkin).getTime());
        Date checkoutDate = new java.sql.Date(df.parse(checkout).getTime());
        //List<Date> allDatesInSearch = createDateInterval(checkin, checkout);
        List <AvailableBookingDto> availableBookings = new ArrayList<>();
        roomService.
            getAll().
            stream().
            filter(rd -> rd.getRoomType().getMaxPerson() >= guests).
            forEach(rd -> availableBookings.add(new AvailableBookingDto(rd, checkinDate, checkoutDate, guests, getExtraBedsForBooking(rd, guests))));

        model.addAttribute("list", availableBookings);

//        List <Long> roomIdList = roomList.
//                stream().
//                map(RoomDto::getId).
//                toList();

//        List <BookingDto> bookingList = bookingService.
//                getAll().
//                stream().
//                filter(b -> roomIdList.contains(b.getRoom().getId())).
//                toList();

//        HashMap <Long, List<Date>> roomIdAndDateRange = new HashMap<>();
//        bookingList.forEach(b -> roomIdAndDateRange.put(b.getId(),b.getListOfAllDatesInBooking()));       ;
//
//        roomList = roomList.
//                stream().
//                filter(r -> areDatesOverlapping(allDatesInSearch, roomIdAndDateRange.get(r.getId()))).
//                toList();
//
//        model.addAttribute("roomList", roomList);

        //System.out.println(allDatesInSearch); // TEST

        return "getAvailableRooms";
    }

    public boolean areDatesOverlapping(List<Date> searchDates, List<Date> bookingDates) {
        boolean output = false;
        if (searchDates.getFirst().equals(bookingDates.getLast()) || searchDates.getLast().equals(bookingDates.getFirst()))
            return output;
        for (Date date: searchDates) {
            if (bookingDates.contains(date))
                output = true;
        }
        return output;
    }

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

    public Long getNumberOfDaysBetweenTwoDates(String checkin, String checkout) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date checkinDate = new java.sql.Date(df.parse(checkin).getTime());
        Date checkoutDate = new java.sql.Date(df.parse(checkout).getTime());
        long differenceMillis = checkoutDate.getTime() - checkinDate.getTime();
        return differenceMillis / (1000 * 60 * 60 * 24);
    }

    public int getExtraBedsForBooking (RoomDto room, int guests) {
        int beds = 0;
        switch(room.getRoomType().getType()) {
            case "single":
                break;
            case "double":
                if (guests == 3) {
                    beds = 1;
                    break;
                }
                else
                    break;
            case "large_double":
                if (guests == 4) {
                    beds = 2;
                    break;
                }
                else if (guests == 3) {
                    beds = 1;
                    break;
                }
                else
                    break;
        }
        return beds;
    }

}
