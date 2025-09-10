package ro.siit.HRS.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.rturn.EmployeeReturnDto;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.ManagerRepository;
import ro.siit.HRS.util.MapperUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl {

    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;
    private final MapperUtil mapperUtil;

    public List<EmployeeReturnDto> getAllEmployees() {

        List<EmployeeReturnDto> allEmployees = new ArrayList<>();

        List<EmployeeReturnDto> employeeReturnDtoList;
        List<EmployeeReturnDto> employeeReturnDtoListFromManager;

        List<Employee> employeeList = employeeRepository.findAll();
        List<Manager> managerList = managerRepository.findAll();

        employeeReturnDtoList = employeeList.stream()
                .map(mapperUtil::mapEmployee).toList();
        employeeReturnDtoListFromManager = managerList.stream()
                .map(mapperUtil::mapManagerToEmployeeReturnDto).toList();

        allEmployees.addAll(employeeReturnDtoList);
        allEmployees.addAll(employeeReturnDtoListFromManager);

        return allEmployees;
    }
}
