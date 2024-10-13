package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.EmployeeCreateDto;
import ro.siit.HRS.dto.EmployeeReturnDto;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.UserRepository;

import java.util.ArrayList;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;

    public EmployeeReturnDto mapEmployee(Employee employee) {

        EmployeeReturnDto employeeReturnDto = new EmployeeReturnDto();
        employeeReturnDto.setGender(employee.getGender());
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
        return mapEmployee(employee);
    }
    public String deleteEmployee(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        employeeRepository.delete(employee);
        return "This employee has been deleted!";
    }
}
