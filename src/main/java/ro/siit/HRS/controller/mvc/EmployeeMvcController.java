package ro.siit.HRS.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.siit.HRS.dto.create.LeaveRequestCreateDto;
import ro.siit.HRS.dto.update.EmployeeUpdateDto;
import ro.siit.HRS.dto.update.ManagerUpdateDto;
import ro.siit.HRS.model.LeaveRequest;
import ro.siit.HRS.service.EmployeeService;
import ro.siit.HRS.service.HrsUserDetails;
import ro.siit.HRS.service.LeaveRequestService;

@Controller
@RequestMapping(path = "/employees")
public class EmployeeMvcController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping(path = "/selfservice")
    public String selfService(@AuthenticationPrincipal HrsUserDetails user, Model model) {

        model.addAttribute("authenticationDetails", employeeService
                .getAuthenticationDetails(user.getUsername()));

        return "/selfservice";
    }

    @GetMapping(path = "/personaldetails")
    public String getPersonalDetails(@AuthenticationPrincipal HrsUserDetails user, Model model) {

        model.addAttribute("employee",
                employeeService.getUpdatePersonalDetails(user.getUsername()));
        model.addAttribute("authenticationDetails",
                employeeService.getAuthenticationDetails(user.getUsername()));

        return "/personaldetails";
    }

    @PostMapping(path = "/personaldetails/update")
    public String updatePersonalDetails(@AuthenticationPrincipal HrsUserDetails user,
                                        @ModelAttribute EmployeeUpdateDto employee, Model model) {

        model.addAttribute("employee",
                employeeService.updateEmployee(employee));
        model.addAttribute("authenticationDetails",
                employeeService.getAuthenticationDetails(user.getUsername()));

        return "/personaldetails";
    }

    @GetMapping(path = "/myleaverequests")
    public String getLeaveRequest(@AuthenticationPrincipal HrsUserDetails user, Model model) {

        model.addAttribute("leaverequest", new LeaveRequestCreateDto());
        model.addAttribute("authenticationDetails", employeeService
                .getAuthenticationDetails(user.getUsername()));
        model.addAttribute("myLeaveRequests",employeeService.myLeaveRequests(user.getUsername()));
        model.addAttribute("employeeRemainingDays", employeeService.getEmployeeRemainingDays(user.getUsername()));

        return "leaverequest";
    }

    @PostMapping(path = "/leaverequest/create")
    public String createLeaveRequest(@AuthenticationPrincipal HrsUserDetails user,
                                     @ModelAttribute("leaverequest") LeaveRequestCreateDto leaverequest,
                                     BindingResult bindingResult,
                                     Model model) {

        leaveRequestService.createEmployeeLeaveRequestByUsername(leaverequest, user.getUsername());
        model.addAttribute("leaverequest", new LeaveRequestCreateDto());
        model.addAttribute("authenticationDetails", employeeService
                .getAuthenticationDetails(user.getUsername()));
        model.addAttribute("myLeaveRequests",employeeService.myLeaveRequests(user.getUsername()));
        model.addAttribute("employeeRemainingDays", employeeService.getEmployeeRemainingDays(user.getUsername()));
        return "leaverequest";
    }

    @GetMapping(path = "/employees")
    public String getEmployees(@AuthenticationPrincipal HrsUserDetails user, Model model) {

//        model.addAttribute("employees", managerService
//                .getManagerEmployees(user.getUsername()));
//        model.addAttribute("authenticationDetails", managerService
//                .getAuthenticationDetails(user.getUsername()));

        return "/employees";
    }

    @GetMapping(path = "/requestsForApproval")
    public String getPendingLeaveRequests(@AuthenticationPrincipal HrsUserDetails user, Model model) {

//        model.addAttribute("pendingLeaveRequests",
//                managerService.getManagerPendingLeaveRequests(user.getUsername()));
//        model.addAttribute("authenticationDetails",
//                managerService.getAuthenticationDetails(user.getUsername()));

        return "/leaverequests";
    }

    @PostMapping(path = "/approve")
    public String approveLeaveRequest(@ModelAttribute("leaverequest") LeaveRequest leaveRequest,
                                      BindingResult bindingResult,
                                      Model model) {

//        managerService.approveLeaveRequest(leaveRequest.getId());

        return "redirect:requestsForApproval";
    }
    @GetMapping(path = "/services")
    public String services(@AuthenticationPrincipal UserDetails user, Model model) {

        model.addAttribute("authenticationDetails", employeeService
                .getAuthenticationDetails(user.getUsername()));
        return "/services";
    }
}
