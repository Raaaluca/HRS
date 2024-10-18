package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.EmployeeCreateDto;
import ro.siit.HRS.dto.EmployeeReturnDto;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.ManagerRepository;
import ro.siit.HRS.repository.UserRepository;

import java.util.ArrayList;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;

    public EmployeeReturnDto mapEmployee(Employee employee) {

        EmployeeReturnDto employeeReturnDto = new EmployeeReturnDto();
        employeeReturnDto.setGender(employee.getGender());
        employeeReturnDto.setCity(employee.getCity());
        employeeReturnDto.setEmail(employee.getEmail());
        employeeReturnDto.setSuperiorId(employee.getSuperiorId());
        employeeReturnDto.setStartDate(employee.getStartDate());
        employeeReturnDto.setEndDate(employee.getEndDate());

        return employeeReturnDto;
    }

    public EmployeeReturnDto findById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow();
        return mapEmployee(employee);
    }



    public EmployeeReturnDto createEmployee(EmployeeCreateDto employeeCreateDto) {

        Employee employee = new Employee();

        User user = new User();
        user.setUsername("bia_u");
        user.setPassword("670d$1P");
        employee.setUser(user);
        userRepository.save(user);

        employee.setSuperiorId(employeeCreateDto.getSuperiorId());
        employee.setGender(employeeCreateDto.getGender());
        employee.setCity(employeeCreateDto.getCity());
        employee.setEmail(employeeCreateDto.getEmail());
        employee.setAddress(employeeCreateDto.getAddress());
        employee.setStartDate(employeeCreateDto.getStartDate());
        employee.setEndDate(employeeCreateDto.getEndDate());
        employee.setLeaveRequests(new ArrayList<>());
        employee.setName(employeeCreateDto.getName());
        employee.setNationalId(employeeCreateDto.getNationalId());
        employee.setPhoneNumber(employeeCreateDto.getPhoneNumber());
        employee = employeeRepository.save(employee);

        Manager manager = managerRepository.findById(employeeCreateDto.getSuperiorId()).orElseThrow();
        manager.getEmployees().add(employee);
        managerRepository.save(manager);

        return mapEmployee(employee);
    }
    public String deleteEmployee(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        Long managerId = employee.getSuperiorId();

        Manager manager = managerRepository.findById(managerId).orElseThrow();
        manager.getEmployees().remove(employee);

        managerRepository.save(manager);
        employeeRepository.delete(employee);

        User user = userRepository.findById(employee.getUser().getId()).orElseThrow();
        userRepository.deleteById(user.getId());
        return "This employee has been deleted!";
    }
}
