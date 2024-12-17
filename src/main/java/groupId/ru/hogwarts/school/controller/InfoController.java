package groupId.ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/port")
public class InfoController {

@Value("${server.port0},${server.port1},${server.port2}")

    private int port0;
    private int port1;
    private int port2;

@GetMapping("/0")
    public int getPort0(){
    return port0;
}
    @GetMapping("/1")
    public int getPort1(){
        return port1;
    }
    @GetMapping("/2")
    public int getPort2(){
        return port2;
    }
}
