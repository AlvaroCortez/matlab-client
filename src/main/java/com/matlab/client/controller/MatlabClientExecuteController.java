package com.matlab.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatlabClientExecuteController {

    @GetMapping("/v1/test")
    public String getTestString() {
        return "Hello!";
    }
}
