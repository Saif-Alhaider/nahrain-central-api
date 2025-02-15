package io.github.saifalhaider.nahrain.nahrain_central_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/home")
public class HomeController {

    @GetMapping
    public ResponseEntity<String> sayHello() {
        try{
            return ResponseEntity.ok("Hello World!");
        }catch (Exception e){
            throw e;
        }
    }
}
