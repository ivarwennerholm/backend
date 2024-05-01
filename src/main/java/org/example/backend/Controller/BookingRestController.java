// FILE FOR TESTING ONLY, CAN BE DELETED

package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.BookingDto;
import org.example.backend.Repository.CustomerRepository;
import org.example.backend.Repository.RoomRepository;
import org.example.backend.Service.BookingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("booking")
public class BookingRestController {

    private final BookingService bookingService;
    private final RoomRepository rRepo;
    private final CustomerRepository cRepo;

    @RequestMapping("getAll")
    public List<BookingDto> getAllBookings(){
        return bookingService.getAll();
    }

}
