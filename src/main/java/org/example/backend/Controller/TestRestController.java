package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.BookingDto;
import org.example.backend.Service.BookingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestRestController {

    private final BookingService bookingService;

    @RequestMapping("bookings/getAll")
    public List<BookingDto> getAllBookings() {
        return bookingService.getAll();
    }
}