package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Service.RoomEventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("roomevents")
@RequiredArgsConstructor
public class RoomEventController {
    private final RoomEventService rmEventService;

    @RequestMapping("/{roomno}")
    protected String getRoomEventsByRoomNo(@PathVariable int roomno, Model model){
        model.addAttribute("roomno",roomno);
        model.addAttribute("allevents",rmEventService.getRoomEventsByRoomNo(roomno));
        return "individualRoomEvents.html";
    }
}
