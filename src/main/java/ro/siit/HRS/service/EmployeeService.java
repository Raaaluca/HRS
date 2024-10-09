package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.EmployeeCreateDto;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;

    public Employee findById(Long id) {

        return employeeRepository.findById(id)
                .orElseThrow();
    }

    public Employee createEmployee(EmployeeCreateDto employeeCreateDto) {

        Employee employee = new Employee();

        User user = new User();
        user.setUsername("bia_u");
        user.setPassword("670d$1P");
        user = userRepository.save(user);

        employee.setUser(user);
        employee.setSuperiorId(employeeCreateDto.getSuperiorId());
        employee.setGender(employeeCreateDto.getGender());
        employee.setEmail(employeeCreateDto.getEmail());
        employee.setAddress(employeeCreateDto.getAddress());
        employee.setStartDate(employeeCreateDto.getStartDate());
        employee.setEndDate(employeeCreateDto.getEndDate());
        employee.setLeaveRequests(new ArrayList<>());
        employee.setName(employeeCreateDto.getName());
        employee.setNationalId(employeeCreateDto.getNationalId());
        employee.setPhoneNumber(employeeCreateDto.getPhoneNumber());
        employee = employeeRepository.save(employee);
        return employee;
    }
}
