package com.loduone.damn.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class DamnOneController {

    @GetMapping
    public ResponseEntity<String> defaultPage(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
