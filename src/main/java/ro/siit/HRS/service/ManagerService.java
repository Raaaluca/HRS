package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.ManagerCreateDto;
import ro.siit.HRS.dto.ManagerReturnDto;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.ManagerRepository;
import ro.siit.HRS.repository.UserRepository;

@Service
public class ManagerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

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
                .orElseThrow());
    }

    public ManagerReturnDto createManager(ManagerCreateDto managerCreateDto) {

        Manager manager = new Manager();

        User user = new User();
        user.setUsername("emy_e");
        user.setPassword("8899@!");
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

    public ManagerReturnDto assignEmployeeToManager(Long employeeId, Long managerId) {

        Employee employee = employeeRepository.findById(employeeId).get();
        Manager manager = managerRepository.findById(managerId).get();
        manager.getEmployees().add(employee);
        manager = managerRepository.save(manager);
        return mapManager(manager);
    }
    public String deleteManager(Long managerId){

        Manager manager = managerRepository.findById(managerId).orElseThrow();

        managerRepository.deleteById(managerId);
        userRepository.deleteById(manager.getUser().getId());
        return "This manager has been deleted!";
    }
}
