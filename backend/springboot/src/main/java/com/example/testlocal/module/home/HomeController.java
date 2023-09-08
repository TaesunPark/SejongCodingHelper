package com.example.testlocal.module.home;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String HomePage(){
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("welcome" + localDateTime);
        return "welcome";
    }

    @GetMapping("/logs")
    public String LogPage(){
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("log page" + localDateTime);
        return "welcome log";
    }
}
