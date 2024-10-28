package ro.siit.HRS.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.siit.HRS.service.HrsUserDetailsService;
import ro.siit.HRS.service.ManagerService;

@Controller
public class HrsController {

    @Autowired
    private ManagerService managerService;

    @GetMapping(path = "/index")
    public String index() {

        return "/index";
    }
}
