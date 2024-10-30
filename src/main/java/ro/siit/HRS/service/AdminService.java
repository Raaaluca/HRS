package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.rturn.EmployeeReturnDto;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.ManagerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeService employeeService;

    public List<EmployeeReturnDto> getAllEmployees(){

        List<EmployeeReturnDto> allEmployees = new ArrayList<>();

        List<EmployeeReturnDto> employeeReturnDtoList = new ArrayList<>();
        List<EmployeeReturnDto> employeeReturnDtoListFromManager = new ArrayList<>();

        List<Employee> employeeList = employeeRepository.findAll();
        List<Manager> managerList = managerRepository.findAll();

        employeeReturnDtoList = employeeList.stream()
                .map(employee -> employeeService.mapEmployee(employee)).collect(Collectors.toList());
        employeeReturnDtoListFromManager = managerList.stream()
                .map(manager -> employeeService.mapManagerToEmployeeReturnDto(manager)).collect(Collectors.toList());

        allEmployees.addAll(employeeReturnDtoList);
        allEmployees.addAll(employeeReturnDtoListFromManager);

        return allEmployees;

    }
}
