package com.oraz.saken.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestErrorController {
    @GetMapping("/error")
    public String generateError() throws Exception {
        throw new Exception("Тестовая ошибка 500 для проверки логов");
    }
}
