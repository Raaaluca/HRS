package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.create.ManagerCreateDto;
import ro.siit.HRS.dto.rturn.LeaveRequestReturnDto;
import ro.siit.HRS.dto.rturn.ManagerReturnDto;
import ro.siit.HRS.dto.update.ManagerUpdateDto;
import ro.siit.HRS.exceptions.ManagerNotFoundException;
import ro.siit.HRS.model.*;
import ro.siit.HRS.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    @Autowired
    private LeaveRequestService leaveRequestService;
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public ManagerReturnDto mapManager(Manager manager) {

        ManagerReturnDto managerReturnDto = new ManagerReturnDto();
        managerReturnDto.setAddress(manager.getAddress());
        managerReturnDto.setCity(manager.getCity());
        managerReturnDto.setEmail(manager.getEmail());
        managerReturnDto.setName(manager.getName());
        managerReturnDto.setGender(manager.getGender());
        managerReturnDto.setNationalId(manager.getNationalId());
        managerReturnDto.setPhoneNumber(manager.getPhoneNumber());
        managerReturnDto.setStartDate(manager.getStartDate());
        managerReturnDto.setEndDate(manager.getEndDate());

        return managerReturnDto;
    }

    public ManagerReturnDto findById(Long id) {

        return mapManager(managerRepository.findById(id)
                .orElseThrow(() -> new ManagerNotFoundException(
                        "This manager id " + id + "does not exist!")));
    }

    public ManagerReturnDto createManager(ManagerCreateDto managerCreateDto) {

        Manager manager = new Manager();

        User user = new User();
        user.setRole("MANAGER");
        user.setUsername(managerCreateDto.getEmail());
        user.setPassword(managerCreateDto.getNationalId()); // to be encripted
        user = userRepository.save(user);

        manager.setUser(user);
        manager.setAddress(managerCreateDto.getAddress());
        manager.setEmail(managerCreateDto.getEmail());
        manager.setCity(managerCreateDto.getCity());
        manager.setGender(managerCreateDto.getGender());
        manager.setName(managerCreateDto.getName());
        manager.setStartDate(managerCreateDto.getStartDate());
        manager.setEndDate(managerCreateDto.getEndDate());
        manager.setNationalId(managerCreateDto.getNationalId());
        manager.setPhoneNumber(managerCreateDto.getPhoneNumber());
        manager = managerRepository.save(manager);
        return mapManager(manager);
    }

    public ManagerReturnDto updateManager(ManagerUpdateDto managerUpdateDto) {

        Manager manager = managerRepository.findById(managerUpdateDto.getId())
                .orElseThrow(() -> new ManagerNotFoundException(
                        "This manager id " + managerUpdateDto.getId() + "can not be found!!"));
        if (managerUpdateDto.getAddress() != null) {
            manager.setAddress(managerUpdateDto.getAddress());
        }
        if (managerUpdateDto.getName() != null) {
            manager.setName(managerUpdateDto.getName());
        }
        if (managerUpdateDto.getCity() != null) {
            manager.setCity(managerUpdateDto.getCity());
        }
        if (managerUpdateDto.getEmail() != null) {
            manager.setEmail(managerUpdateDto.getEmail());
            manager.getUser().setUsername(manager.getEmail());
        }
        if (managerUpdateDto.getEndDate() != null) {
            manager.setEndDate(managerUpdateDto.getEndDate());
        }
        if (managerUpdateDto.getPhoneNumber() != null) {
            manager.setPhoneNumber(managerUpdateDto.getPhoneNumber());
        }
        manager = managerRepository.save(manager);

        return mapManager(manager);
    }

    public ManagerReturnDto assignEmployeeToManager(Long employeeId, Long managerId) {

        Employee employee = employeeRepository.findById(employeeId).get();
        Manager manager = managerRepository.findById(managerId).get();
        manager.getEmployees().add(employee);
        manager = managerRepository.save(manager);
        return mapManager(manager);
    }

    public String deleteManager(Long managerId) {

        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() ->
                        new ManagerNotFoundException("This manager id " + managerId + "was not found!"));

        managerRepository.deleteById(managerId);
        userRepository.deleteById(manager.getUser().getId());
        return "This manager has been deleted!";
    }

    public List<Employee> getManagerEmployees(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();

        return manager.getEmployees();
    }

    public List<LeaveRequestReturnDto> getManagerPendingLeaveRequests(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();

        return manager.getLeaveRequestsToManage()
                .stream()
                .map(p -> leaveRequestService.mapLeaveRequestReturnDto(p))
                .collect(Collectors.toList());
    }

    public String getAuthenticationDetails(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();
        Department department = departmentRepository.findByManagerId(manager.getId()).orElseThrow();

        return manager.getName() + ", " + department.getDepartmentName() + " Manager";
    }

    public void approveLeaveRequest(Long leaveRequestId) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId).orElseThrow();
        leaveRequest.setApproved(true);
        leaveRequestRepository.save(leaveRequest);
    }
}
