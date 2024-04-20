package ru.geekbraines.jwt_hometask.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api")
public class ResourseController {

    @GetMapping("/resource")
    public String resource(){

        return "Resource";
    }


}
