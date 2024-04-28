package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.BookingDto;
import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.example.backend.Repository.BookingRepository;
import org.example.backend.Repository.CustomerRepository;
import org.example.backend.Repository.RoomRepository;
import org.example.backend.Service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//@RestController
@Controller
@RequestMapping("bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookService;
    private List<BookingDto> allBookings = new ArrayList<>();

    @RequestMapping("test")
    public String testPage(Model model){
        model.addAttribute("test","testingtesting");
        return "testBooking.html";
    }

    @RequestMapping("all")
    public String allBookings(Model model){
        allBookings = bookService.getAll();
        System.out.println(allBookings);
        model.addAttribute("allBookings",allBookings);
        return "allBooking.html";
    }

//    private final BookingRepository repo;
//    private final RoomRepository rRepo;
//    private final CustomerRepository cRepo;
//
//    public BookingController(BookingRepository repo, RoomRepository rRepo, CustomerRepository cRepo) {
//        this.repo = repo;
//        this.rRepo = rRepo;
//        this.cRepo = cRepo;
//    }
//
//    @RequestMapping("getAll")
//    public List<Booking> getAllBookings(){
//        return repo.findAll();
//    }
//
//    @RequestMapping("add")
//    public String addBooking(@RequestParam String checkin, @RequestParam String checkout, @RequestParam int guests, @RequestParam int extrabed,  @RequestParam Long custid, @RequestParam Long roomid) throws ParseException {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Date checkinDate = new java.sql.Date(df.parse(checkin).getTime());
//        Date checkoutDate = new java.sql.Date(df.parse(checkout).getTime());
//        Customer cust = cRepo.findById(custid).get();
//        Room room = rRepo.findById(roomid).get();
//        Booking booking = new Booking(checkinDate, checkoutDate, guests, extrabed, cust, room);
//        repo.save(booking);
//        return "Booking (" + checkin + " - " + checkout + ") added successfully";
//    }
}
