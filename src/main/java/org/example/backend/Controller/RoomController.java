package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.RoomDto;
import org.example.backend.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @RequestMapping("all")
    public String getAllRooms(Model model) {
        model.addAttribute("rooms",roomService.getAll());
        return "allRooms.html";
    }
}
