package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.RoomTypeDto;
import org.example.backend.Service.RoomTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("roomtypes")
public class RoomTypeController {
    private final RoomTypeService service;

    @RequestMapping("getAll")
    public List<RoomTypeDto> getAll() {
        return service.getAll();
    }

    @RequestMapping("add")
    public String addRoomType(@RequestParam Long id, @RequestParam String type, @RequestParam int maxExtraBed, @RequestParam int maxPerson, @RequestParam double pricePerNight) {
        service.addRoomType(new RoomTypeDto(id, type, maxExtraBed, maxPerson, pricePerNight));
        return "room type (id: " + id + ", type: " + type + ", extrabed: " + maxExtraBed + ", maxperson: " + maxPerson + ") added";
    }
}
