package ro.siit.HRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.create.LeaveRequestCreateDto;
import ro.siit.HRS.dto.rturn.LeaveRequestReturnDto;
import ro.siit.HRS.service.LeaveRequestService;
import ro.siit.HRS.service.impl.LeaveRequestServiceImpl;

@RequestMapping(path = "/leaverequest")
@RestController
public class LeaveRequestController {
    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping(path = "/create")
    public LeaveRequestReturnDto createLeaveRequest(@RequestBody LeaveRequestCreateDto leaveRequestCreateDto) {

        return leaveRequestService.createLeaveRequest(leaveRequestCreateDto);
    }
}
