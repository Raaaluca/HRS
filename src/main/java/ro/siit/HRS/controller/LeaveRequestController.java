package ro.siit.HRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.siit.HRS.dto.LeaveRequestCreateDto;
import ro.siit.HRS.dto.LeaveRequestReturnDto;
import ro.siit.HRS.service.LeaveRequestService;

@RequestMapping(path = "/leaverequest")
@RestController
public class LeaveRequestController {
    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping(path = "/create")
    public LeaveRequestReturnDto createLeaveRequest(@RequestBody LeaveRequestCreateDto leaveRequestCreateDto) {

        return leaveRequestService.createLeaveRequest(leaveRequestCreateDto);
    }
}
