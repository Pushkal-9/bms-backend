package com.bmsbackend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {


    @GetMapping
    public String welcome(){
        return "Started BMS Backend Application";
    }

}
