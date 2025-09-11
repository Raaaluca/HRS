package ro.siit.HRS.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.create.LeaveRequestCreateDto;
import ro.siit.HRS.dto.rturn.LeaveRequestReturnDto;
import ro.siit.HRS.service.LeaveRequestService;

@RequestMapping(path = "/leaverequest")
@RestController
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @PostMapping(path = "/create")
    public LeaveRequestReturnDto createLeaveRequest(@RequestBody LeaveRequestCreateDto leaveRequestCreateDto) {

        return leaveRequestService.createLeaveRequest(leaveRequestCreateDto);
    }
}
