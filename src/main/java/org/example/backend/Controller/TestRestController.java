// This file is for testing only, can be safely deleted if needed - Ivar

package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Service.ContractCustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class TestRestController {
    private final ContractCustomerService service;

    @RequestMapping("/simple")
    public String test1() {
        return "TEST";
    }
}
