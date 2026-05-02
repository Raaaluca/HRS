package ro.siit.HRS.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.create.LeaveRequestCreateDto;
import ro.siit.HRS.dto.response.LeaveRequestReturnDto;
import ro.siit.HRS.service.LeaveRequestService;

@RequestMapping(path = "/leaverequests")
@RestController
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @PostMapping
    public ResponseEntity<LeaveRequestReturnDto> createLeaveRequest(@RequestBody @Valid LeaveRequestCreateDto leaveRequestCreateDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(leaveRequestService.createLeaveRequest(leaveRequestCreateDto));
    }
}
