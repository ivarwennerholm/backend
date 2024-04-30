package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.AvailableBookingDto;
import org.example.backend.DTO.BookingDto;
import org.example.backend.DTO.RoomDto;
import org.example.backend.Service.BookingService;
import org.example.backend.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequiredArgsConstructor
//@RequestMapping("booking")
public class BookingController {

    private final RoomService roomService;
    private final BookingService bookingService;

    @RequestMapping("booking/add")
    public String addBooking() {
        return "searchBooking";
    }

    @RequestMapping("booking/getAvailableRooms")
    public String getAvailableBookings(@RequestParam int guests, @RequestParam String checkin, @RequestParam String checkout, Model model) throws ParseException {
        model.addAttribute("guests", guests);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        Date checkinDate = bookingService.convertStringToDate(checkin);
        Date checkoutDate = bookingService.convertStringToDate(checkout);
        long differenceDays = bookingService.getNumberOfDaysBetweenTwoDates(checkinDate, checkoutDate);
        model.addAttribute("nights", differenceDays);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        List<Date> seachedDates = bookingService.createDateInterval(checkinDate, checkoutDate);
        List<AvailableBookingDto> availableBookings = new ArrayList<>();
        roomService.
                getAll().
                stream().
                filter(rd -> rd.getRoomType().getMaxPerson() >= guests).
                forEach(rd -> availableBookings.add(new AvailableBookingDto(rd, checkinDate, checkoutDate, guests, bookingService.getExtraBedsForBooking(rd, guests))));

        availableBookings.
                stream().
                filter(b -> !bookingService.areDatesOverlapping(seachedDates, bookingService.createDateInterval(b.getCheckinDate(), b.getCheckoutDate()))).
                toList();


        model.addAttribute("list", availableBookings);
        return "showAvailableRooms";
    }

//    @RequestMapping("/getBookingConfirmation")
//    public String getBookingConfirmation(@RequestParam("booking") String booking, Model model) {
//        System.out.println(booking);
//        return "showBookingConfirmation";
//    }

//    @PostMapping("/getBookingConfirmation")
//    public String getBookingConfirmation(@RequestBody AvailableBookingDto booking, Model model) {
//        System.out.println(booking);
//        return "showBookingConfirmation";
//    }

    @PostMapping("booking/getBookingConfirmation/{id}")
    public String getBookingConfirmation(@PathVariable Long id, @RequestParam String checkin, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("checkin", checkin);
        System.out.println(id);
        System.out.println(checkin);
        return "showBookingConfirmation";
    }

//    public boolean areDatesOverlapping(List<Date> searchDates, List<Date> bookingDates) {
//        boolean output = false;
//        if (searchDates.getFirst().equals(bookingDates.getLast()) || searchDates.getLast().equals(bookingDates.getFirst()))
//            return output;
//        for (Date date : searchDates) {
//            if (bookingDates.contains(date))
//                output = true;
//        }
//        return output;
//    }
//
//    public List<Date> createDateInterval(String checkin, String checkout) throws ParseException {
//        List<Date> interval = new ArrayList<>();
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Date checkinDate = new java.sql.Date(df.parse(checkin).getTime());
//        Date checkoutDate = new java.sql.Date(df.parse(checkout).getTime());
//        Date iterateDate = checkinDate;
//        while (!iterateDate.after(checkoutDate)) {
//            interval.add(iterateDate);
//            Calendar c = Calendar.getInstance();
//            c.setTime(iterateDate);
//            c.add(Calendar.DATE, 1);
//            iterateDate = new java.sql.Date(c.getTimeInMillis());
//        }
//        return interval;
//    }
//
//    public Long getNumberOfDaysBetweenTwoDates(String checkin, String checkout) throws ParseException {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Date checkinDate = new java.sql.Date(df.parse(checkin).getTime());
//        Date checkoutDate = new java.sql.Date(df.parse(checkout).getTime());
//        long differenceMillis = checkoutDate.getTime() - checkinDate.getTime();
//        return differenceMillis / (1000 * 60 * 60 * 24);
//    }
//
//    public int getExtraBedsForBooking(RoomDto room, int guests) {
//        int beds = 0;
//        switch (room.getRoomType().getType()) {
//            case "single":
//                break;
//            case "double":
//                if (guests == 3) {
//                    beds = 1;
//                    break;
//                } else
//                    break;
//            case "large_double":
//                if (guests == 4) {
//                    beds = 2;
//                    break;
//                } else if (guests == 3) {
//                    beds = 1;
//                    break;
//                } else
//                    break;
//        }
//        return beds;
//    }

}
