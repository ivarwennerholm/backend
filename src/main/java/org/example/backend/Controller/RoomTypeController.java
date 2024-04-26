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
    public List<RoomTypeDto> getAllRoomTypes() {
        return service.getAllRoomTypes();
    }

    @RequestMapping("add")
    public String addRoomType(@RequestParam Long id, @RequestParam String type, @RequestParam int maxExtraBed, @RequestParam int maxPerson) {
        service.addRoomType(new RoomTypeDto(id, type, maxExtraBed, maxPerson));
        return "room type (id: " + id + ", type: " + type + ", extrabed: " + maxExtraBed + ", maxperson: " + maxPerson + ") added";
    }
}
