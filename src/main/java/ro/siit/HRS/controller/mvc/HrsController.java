package ro.siit.HRS.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HrsController {

    @GetMapping(path = "/index")
    public String index() {

        return "/index";
    }
}
