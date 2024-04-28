package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
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

    @RequestMapping("/add")
    public String addBooking() {
        return "searchBooking";
    }

    @RequestMapping("/getAvailable")
    public String getAvailableBookings(@RequestParam int guests, @RequestParam String checkin, @RequestParam String checkout, Model model) throws ParseException {
        model.addAttribute("guests", guests);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date checkinDate = new java.sql.Date(df.parse(checkin).getTime());
        Date checkoutDate = new java.sql.Date(df.parse(checkout).getTime());
        long differenceMillis = checkoutDate.getTime() - checkinDate.getTime();
        long differenceDays = differenceMillis / (1000 * 60 * 60 * 24);
        model.addAttribute("nights", differenceDays);
        
        List<Date> allDatesInSearch = new ArrayList<>();
        Date iterateDate = checkinDate;
        while (!iterateDate.after(checkoutDate)) {
            allDatesInSearch.add(iterateDate);
            Calendar c = Calendar.getInstance();
            c.setTime(iterateDate);
            c.add(Calendar.DATE, 1);
            iterateDate = new java.sql.Date(c.getTimeInMillis());
        }

        List <RoomDto> roomList = roomService.
                getAll().
                stream().
                filter(rd -> rd.getRoomType().getMaxPerson() >= guests).
                toList();

        List <Long> roomIdList = roomList.
                stream().
                map(RoomDto::getId).
                toList();

        List <BookingDto> bookingList = bookingService.
                getAll().
                stream().
                filter(b -> roomIdList.contains(b.getRoom().getId())).
                toList();

        HashMap <Long, List<Date>> roomIdAndDateRange = new HashMap<>();
        bookingList.forEach(b -> roomIdAndDateRange.put(b.getId(),b.getListOfAllDatesInBooking()));       ;

        roomList = roomList.
                stream().
                filter(r -> areDatesOverlapping(allDatesInSearch, roomIdAndDateRange.get(r.getId()))).
                toList();

        model.addAttribute("roomList", roomList);

        //System.out.println(allDatesInSearch); // TEST

        return "getAvailableRooms";
    }

    public boolean areDatesOverlapping(List<Date> list1, List<Date> list2) {
        boolean output = false;
        for (Date date: list1) {
            if (list2.contains(date))
                output = true;
        }
        return output;
    }

    public Long getNumberOfNightsInDateInterval(String checkin, String checkout) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date checkinDate = new java.sql.Date(df.parse(checkin).getTime());
        Date checkoutDate = new java.sql.Date(df.parse(checkout).getTime());
        long differenceMillis = checkoutDate.getTime() - checkinDate.getTime();
        return differenceMillis / (1000 * 60 * 60 * 24);
    }

}
