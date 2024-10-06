package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.ManagerRepository;
import ro.siit.HRS.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ManagerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public Manager findById(Long id) {

        return managerRepository.findById(id)
                .orElseThrow();
    }

    public Manager createManager() {

        Manager manager = new Manager();

        User user = new User();
        user.setUsername("emy_e");
        user.setPassword("8899@!");
        user = userRepository.save(user);

        manager.setUser(user);
        manager.setAddress("Str Dionisie Lupu");
        manager.setEmail("emilian_e@yahoo.com");
        manager.setGender("male");
        manager.setName("Emilian Enache");
        manager.setStartDate(LocalDate.of(2024, 5, 15));
        manager.setEndDate(LocalDate.of(2025, 12, 3));
        manager.setNationalId("112200");
        manager.setPhoneNumber("0754312927");
        manager = managerRepository.save(manager);
        return manager;
    }

    public Manager addEmployee(Long employeeId, Long managerId) {

        Employee employee = employeeRepository.findById(employeeId).get();
        Manager manager = findById(managerId);
        manager.getEmployees().add(employee);
        return managerRepository.save(manager);
    }
}
