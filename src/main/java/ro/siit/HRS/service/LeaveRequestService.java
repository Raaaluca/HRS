package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.LeaveRequest;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.LeaveRequestRepository;

@Service
public class LeaveRequestService {
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    public LeaveRequest findById(Long id) {

        return leaveRequestRepository.findById(id).orElseThrow();
    }
}
