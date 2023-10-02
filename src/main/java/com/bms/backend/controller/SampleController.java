package com.bms.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {


    @GetMapping("/welcome")
    public String welcome(){
        return "Started BMS Backend Application";
    }

}
