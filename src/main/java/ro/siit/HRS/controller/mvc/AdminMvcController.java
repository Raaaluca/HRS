package ro.siit.HRS.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
public class AdminMvcController {

    @GetMapping(path = "/services")
    public String getAdminServices() {

        return "services";
    }

}
