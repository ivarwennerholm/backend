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
    protected List<RoomTypeDto> getAll() {
        return service.getAll();
    }

}
