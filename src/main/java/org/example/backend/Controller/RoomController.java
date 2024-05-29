package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @RequestMapping("all")
    protected String getAllRooms(Model model) {
        model.addAttribute("rooms",roomService.getAll());
        return "allRooms.html";
    }
}
