package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.repository.EmployeeRepository;

import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    public String findById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.get().getEmail();

    }
}
