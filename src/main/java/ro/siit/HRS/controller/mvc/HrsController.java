package ro.siit.HRS.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ro.siit.HRS.service.ManagerService;
import ro.siit.HRS.service.impl.ManagerServiceImpl;

@Controller
public class HrsController {

    @GetMapping(path = "/index")
    public String index() {

        return "/index";
    }
}
