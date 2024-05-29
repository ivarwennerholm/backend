package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Model.Shipper;
import org.example.backend.Service.ShipperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("shippingcontractors")
@RequiredArgsConstructor
public class ShipperController {

    private final ShipperService shipService;

    @RequestMapping("all")
    protected String getAllShippers(Model model){
        List<Shipper> allShippers = shipService.getAllShippers();
        model.addAttribute("allShippers",allShippers);
        return "allShippers.html";
    }
}
