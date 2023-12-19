package fii.dip.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class GotlingTestEndpoint {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

}
