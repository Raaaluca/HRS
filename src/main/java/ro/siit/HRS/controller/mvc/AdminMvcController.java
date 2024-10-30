package ro.siit.HRS.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.siit.HRS.service.AdminService;
import ro.siit.HRS.service.EmployeeService;
import ro.siit.HRS.service.HrsUserDetails;

@Controller
@RequestMapping(path = "/admin")
public class AdminMvcController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path = "/employees")
    public String getEmployees(@AuthenticationPrincipal HrsUserDetails user, Model model) {

        model.addAttribute("employees", adminService.getAllEmployees());
        model.addAttribute("authenticationDetails", employeeService
                .getAuthenticationDetails(user.getUsername()));

        return "/employees";
    }

}
