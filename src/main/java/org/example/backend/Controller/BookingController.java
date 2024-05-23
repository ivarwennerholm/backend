package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.BookingDto;
import org.example.backend.DTO.AvailableBookingDto;
import org.example.backend.Model.Booking;
import org.example.backend.Service.BookingService;
import org.example.backend.Service.Impl.DateService;
import org.example.backend.Service.Impl.EmailService;
import org.example.backend.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("bookings")
@RequiredArgsConstructor
public class BookingController {

    private final RoomService roomService;
    private final BookingService bookService;
    private final DateService dateService;
    private List<BookingDto> allBookings = new ArrayList<>();

    @Autowired
    private EmailService emailService;

    @RequestMapping("all")
    public String allBookings(Model model){
        allBookings = bookService.getAll();
        model.addAttribute("allBookings",allBookings);
        return "allBooking.html";
    }

    @RequestMapping("delete/{id}")
    public String deleteBookings(@PathVariable Long id, Model model){
        String s = bookService.deleteBookingById(id);
        return allBookings(model);
    }

    @RequestMapping("updateForm/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BookingDto b = bookService.findBookingById(id);
        model.addAttribute("booking",b);
        return "updateBooking.html";
    }

    @PostMapping("update/{id}")
    public String updateBookings(@PathVariable Long id,
                                 @RequestParam(required = false) String newCheckIn,
                                 @RequestParam(required = false) String newCheckOut,
                                 Model model){
        try {
            bookService.updateBookingDates(id, newCheckIn, newCheckOut);
            model.addAttribute("okBook","Successfully update booking dates");
        } catch (RuntimeException | ParseException e) {
            model.addAttribute("failBook",e.getMessage());
        }
        return updateForm(id,model);
    }

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
        Date checkinDate = dateService.convertStringToDate(checkin);
        Date checkoutDate = dateService.convertStringToDate(checkout);
        long differenceDays = dateService.getNumberOfDaysBetweenTwoDates(checkinDate, checkoutDate);
        model.addAttribute("nights", differenceDays);
        List<AvailableBookingDto> allBookingsForRoom = new ArrayList<>();
        roomService.
                getAll().
                stream().
                filter(rd -> rd.getRoomType().getMaxPerson() >= guests).
                forEach(rd -> allBookingsForRoom.add(new AvailableBookingDto(rd, checkinDate, checkoutDate, guests, bookService.getExtraBedsForBooking(rd, guests))));

        List<AvailableBookingDto> availableBookingsForRoom = allBookingsForRoom.
                stream().
                filter(b -> bookService.isRoomAvailableOnDates(b.getRoom(), checkinDate, checkoutDate)).
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
                                   @RequestParam String fullprice,
                                   Model model) {
        model.addAttribute("roomid", roomid);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("guests", guests);
        model.addAttribute("extrabeds", extrabeds);
        model.addAttribute("fullprice", fullprice);
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
                                         @RequestParam String email,
                                         @RequestParam String fullprice,
                                         Model model) throws ParseException {

        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("guests", guests);
        model.addAttribute("extrabeds", extrabeds);
        model.addAttribute("name", name);
        model.addAttribute("phone", phone);
        model.addAttribute("email",email);
        model.addAttribute("fullprice", fullprice);

        // Add booking to database
        Date checkinDate = dateService.convertStringToDate(checkin);
        Date checkoutDate = dateService.convertStringToDate(checkout);
        int guestsAmt = Integer.parseInt(guests);
        int extrabedsAmt = Integer.parseInt(extrabeds);
        long roomId = Long.parseLong(roomid);
        int roomNumber = roomService.getRoomById(roomId).getRoomNumber();
        model.addAttribute("roomnumber", roomNumber);
        try {
            bookService.createAndAddBookingToDatabase(checkinDate, checkoutDate, guestsAmt, extrabedsAmt, roomId, name, phone, email);
        } catch (Exception e) {
            model.addAttribute("blacklistMsg",e.getMessage());
        }
        Booking lastBooking = bookService.getLastBooking();
        double discountValue = Double.parseDouble(fullprice) - lastBooking.getTotalPrice();
        double discountedPriceValue = lastBooking.getTotalPrice();
        DecimalFormat df = new DecimalFormat("#.##");
        String discount = df.format(discountValue);
        String discountedPrice = df.format(discountedPriceValue);
        model.addAttribute("discount", discount);
        model.addAttribute("discountedprice", discountedPrice);
        emailService.sendMail(roomNumber, checkin, checkout, guests, extrabeds, name, phone, email, fullprice, discount, discountedPrice);
        return "getBookingConfirmation";
    }

}
