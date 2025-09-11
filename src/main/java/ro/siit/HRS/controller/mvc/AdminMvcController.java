package ro.siit.HRS.controller.mvc;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.siit.HRS.dto.create.EmployeeCreateDto;
import ro.siit.HRS.dto.rturn.EmployeeReturnDto;
import ro.siit.HRS.dto.update.EmployeeUpdateDto;
import ro.siit.HRS.service.AdminService;
import ro.siit.HRS.service.EmployeeService;
import ro.siit.HRS.service.ManagerService;
import ro.siit.HRS.service.impl.AdminServiceImpl;
import ro.siit.HRS.service.impl.EmployeeServiceImpl;
import ro.siit.HRS.config.HrsUserDetails;
import ro.siit.HRS.service.impl.ManagerServiceImpl;

@Controller
@RequestMapping(path = "/admin")
public class AdminMvcController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ManagerService managerService;

    @GetMapping(path = "/allemployees")
    public String getEmployees(@AuthenticationPrincipal HrsUserDetails user, Model model) {

        model.addAttribute("employees", adminService.getAllEmployees());
        model.addAttribute("authenticationDetails", employeeService
                .getAuthenticationDetails(user.getUsername()));

        return "/employees";
    }

    @PostMapping(path = "/employeeupdate")
    public String employeeUpdate(@AuthenticationPrincipal HrsUserDetails user, @ModelAttribute EmployeeUpdateDto employeeUpdateDto, Model model) {

        if (employeeUpdateDto.getJobTitle().equals("HR Manager")
                || employeeUpdateDto.getJobTitle().equals("SALES Manager")
                || employeeUpdateDto.getJobTitle().equals("IT Manager")) {
            model.addAttribute("employeeUpdateDto",
                    managerService.getUpdatePersonalDetails(employeeUpdateDto.getEmail()));
        } else {
            model.addAttribute("employeeUpdateDto",
                    employeeService.getUpdatePersonalDetails(employeeUpdateDto.getEmail()));
        }
        model.addAttribute("authenticationDetails",
                employeeService.getAuthenticationDetails(user.getUsername()));
        return "/updateemployee";
    }

    @PostMapping(path = "/update")
    public String update(@AuthenticationPrincipal HrsUserDetails user,
                         @Valid @ModelAttribute EmployeeUpdateDto employeeUpdateDto,
                         BindingResult result,
                         Model model) {

        if (!result.hasErrors()) {
            if (employeeUpdateDto.getJobTitle().equals("HR Manager")
                    || employeeUpdateDto.getJobTitle().equals("SALES Manager")
                    || employeeUpdateDto.getJobTitle().equals("IT Manager")) {
                managerService.updateManagerFromEmployeeDto(employeeUpdateDto);
            } else {
                employeeService.updateEmployeeDto(employeeUpdateDto);
            }
            return "redirect:allemployees";
        }
        model.addAttribute("employeeUpdateDto", employeeUpdateDto);
        model.addAttribute("authenticationDetails",
                employeeService.getAuthenticationDetails(user.getUsername()));

        return "/updateemployee";
    }

    @GetMapping(path = "/createemployee")
    public String createEmployee(@AuthenticationPrincipal HrsUserDetails user, Model model) {

        model.addAttribute("employeeCreateDto", new EmployeeCreateDto());
        model.addAttribute("authenticationDetails",
                employeeService.getAuthenticationDetails(user.getUsername()));
        return "/createemployee";
    }

    @PostMapping(path = "/create")
    public String create(@AuthenticationPrincipal HrsUserDetails user,
                         @ModelAttribute @Valid EmployeeCreateDto employeeCreateDto,
                         BindingResult result,
                         Model model) {

        if (!result.hasErrors()) {
            employeeService.createEmployee(employeeCreateDto);
            return "redirect:allemployees";
        }
        model.addAttribute("authenticationDetails",
                employeeService.getAuthenticationDetails(user.getUsername()));
        model.addAttribute("employeeCreateDto", employeeCreateDto);
        return "/createemployee";
    }

    @PostMapping(path = "/deleteemployee")
    public String deleteEmployee(@ModelAttribute EmployeeReturnDto employeeReturnDto) {

        employeeService.deleteEmployee(employeeReturnDto.getId());
        return "redirect:allemployees";
    }

}
