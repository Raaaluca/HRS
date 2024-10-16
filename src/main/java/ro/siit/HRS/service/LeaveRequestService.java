package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.LeaveRequestCreateDto;
import ro.siit.HRS.dto.LeaveRequestReturnDto;
import ro.siit.HRS.model.LeaveRequest;
import ro.siit.HRS.repository.LeaveRequestRepository;

@Service
public class LeaveRequestService {
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public LeaveRequest findById(Long id) {

        return leaveRequestRepository.findById(id).orElseThrow();
    }

    public LeaveRequestReturnDto mapLeaveRequestReturnDto(LeaveRequest leaveRequest) {

        LeaveRequestReturnDto leaveRequestReturnDto = new LeaveRequestReturnDto();
        leaveRequestReturnDto.setNumberOfDaysForLeaveRequest(leaveRequest.getNumberOfDays());
        leaveRequestReturnDto.setTypeOfLeaveRequest(leaveRequest.getType());
        leaveRequestReturnDto.setEmployeeId(leaveRequest.getEmployeeId());
        leaveRequestReturnDto.setManagerId(leaveRequest.getManagerId());

        return leaveRequestReturnDto;
    }

    public LeaveRequestReturnDto createLeaveRequest(LeaveRequestCreateDto leaveRequestCreateDto) {

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setType(leaveRequestCreateDto.getTypeOfLeaveRequest());
        leaveRequest.setNumberOfDays(leaveRequestCreateDto.getNumberOfDaysForLeaveRequest());
        leaveRequest.setManagerId(leaveRequestCreateDto.getManagerId());
        leaveRequest.setEmployeeId(leaveRequestCreateDto.getEmployeeId());
        leaveRequest.setApproved(false);
        leaveRequest = leaveRequestRepository.save(leaveRequest);

        return mapLeaveRequestReturnDto(leaveRequest);
    }
}
