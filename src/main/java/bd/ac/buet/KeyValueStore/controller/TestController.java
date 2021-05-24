package bd.ac.buet.KeyValueStore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test-hello")
    public String testResponse(){
        return "Hello from Key value store- from test controller xxxx";
    }
}
