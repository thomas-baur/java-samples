package com.example.observability;

import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ResourceClientController {

    private static final Logger LOG = null;

    @PostMapping("/fail")
    public String fail() {
        return "Fail!";
    }

    @GetMapping("/success")
    public String success() {
        return "Success!";
    }

    @GetMapping("/service1")  
    public String service1() {

        LOG.info("service 1 was called");

        return "this is service 1";
    }
    
}
