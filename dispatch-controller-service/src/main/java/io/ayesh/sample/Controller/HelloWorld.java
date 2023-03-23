package io.ayesh.sample.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping("/")
    public String hello(@RequestParam String name) {
        return String.format("Hello, %s!", name);
    }
}
