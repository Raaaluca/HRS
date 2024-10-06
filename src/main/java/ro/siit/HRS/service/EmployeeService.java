package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Employee createEmployee() {

        Employee employee = new Employee();

        User user = new User();
        user.setUsername("bia_u");
        user.setPassword("670d$1P");
        user = userRepository.save(user);

        employee.setUser(user);
        employee.setSuperiorId(1L);
        employee.setGender("female");
        employee.setEmail("bia_udrea@yahoo.com");
        employee.setAddress("Str. Scoverga");
        employee.setStartDate(LocalDate.of(2024, 3, 22));
        employee.setEndDate(LocalDate.of(2024, 12, 28));
        employee.setLeaveRequests(new ArrayList<>());
        employee.setName("Bianca Udrea");
        employee.setNationalId("123489");
        employee.setPhoneNumber("0799500886");
        employee = employeeRepository.save(employee);
        return employee;
    }
}
