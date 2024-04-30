package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.AvailableBookingDto;
import org.example.backend.Service.BookingService;
import org.example.backend.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("booking")
public class BookingController {

    private final RoomService roomService;
    private final BookingService bookingService;

    @RequestMapping("search")
    public String searchBooking() {
        return "searchBooking";
    }

    @PostMapping("getAvailableRooms")
    public String getAvailableRooms(@RequestParam int guests,
                                    @RequestParam String checkin,
                                    @RequestParam String checkout,
                                    Model model) throws ParseException {
        model.addAttribute("guests", guests);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        Date checkinDate = bookingService.convertStringToDate(checkin);
        Date checkoutDate = bookingService.convertStringToDate(checkout);
        long differenceDays = bookingService.getNumberOfDaysBetweenTwoDates(checkinDate, checkoutDate);
        model.addAttribute("nights", differenceDays);
        List<AvailableBookingDto> allBookingsForRoom = new ArrayList<>();
        roomService.
                getAll().
                stream().
                filter(rd -> rd.getRoomType().getMaxPerson() >= guests).
                forEach(rd -> allBookingsForRoom.add(new AvailableBookingDto(rd, checkinDate, checkoutDate, guests, bookingService.getExtraBedsForBooking(rd, guests))));

        List<AvailableBookingDto> availableBookingsForRoom = allBookingsForRoom.
                stream().
                filter(b -> bookingService.isRoomAvailableOnDates(b.getRoom(), checkinDate, checkoutDate)).
                toList();

        model.addAttribute("list", availableBookingsForRoom);
        return "getAvailableRooms";
    }

    @PostMapping("inputUserDetails")
    public String inputUserDetails(@RequestParam String roomid,
                                   @RequestParam String checkin,
                                   @RequestParam String checkout,
                                   @RequestParam String guests,
                                   @RequestParam String extrabeds,
                                   Model model) {
        model.addAttribute("roomid", roomid);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("guests", guests);
        model.addAttribute("extrabeds", extrabeds);
        return "inputUserDetails";
    }

    @PostMapping("getBookingConfirmation")
    public String getBookingConfirmation(@RequestParam String roomid,
                                         @RequestParam String checkin,
                                         @RequestParam String checkout,
                                         @RequestParam String guests,
                                         @RequestParam String extrabeds,
                                         @RequestParam String name,
                                         @RequestParam String phone,
                                         Model model) throws ParseException {

        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("guests", guests);
        model.addAttribute("extrabeds", extrabeds);
        model.addAttribute("name", name);
        model.addAttribute("phone", phone);

        // Add booking to database
        Date checkinDate = bookingService.convertStringToDate(checkin);
        Date checkoutDate = bookingService.convertStringToDate(checkout);
        int guestsAmt = Integer.parseInt(guests);
        int extrabedsAmt = Integer.parseInt(extrabeds);
        long roomId = Long.parseLong(roomid);
        int roomNumber = roomService.getRoomById(roomId).getRoomNumber();
        model.addAttribute("roomnumber", roomNumber);
        bookingService.createAndAddBookingToDatabase(checkinDate, checkoutDate, guestsAmt, extrabedsAmt, roomNumber, name, phone);
        return "getBookingConfirmation";
    }

}
