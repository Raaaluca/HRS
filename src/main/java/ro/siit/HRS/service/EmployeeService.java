package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.EmployeeCreateDto;
import ro.siit.HRS.dto.EmployeeReturnDto;
import ro.siit.HRS.model.Department;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.DepartmentRepository;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.ManagerRepository;
import ro.siit.HRS.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public static final List<String> IT_DEPARTMENT_JOB_TITLES = List.of("IT Engineer", "Tester", "UI/UX Designer");
    public static final List<String> SALES_DEPARTMENT_JOB_TITLES = List.of("Sales Officer", "Associate Officer");
    public static final List<String> HR_DEPARTMENT_JOB_TITLES = List.of("HR Admin", "Payroll Admin");


    public EmployeeReturnDto mapEmployee(Employee employee) {

        EmployeeReturnDto employeeReturnDto = new EmployeeReturnDto();
        employeeReturnDto.setGender(employee.getGender());
        employeeReturnDto.setCity(employee.getCity());
        employeeReturnDto.setEmail(employee.getEmail());
        employeeReturnDto.setSuperiorId(employee.getSuperiorId());
        employeeReturnDto.setStartDate(employee.getStartDate());
        employeeReturnDto.setEndDate(employee.getEndDate());
        employeeReturnDto.setJobTitle(employee.getJobTitle());

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
        user.setRole("EMPLOYEE");
        user.setUsername(employeeCreateDto.getEmail());
        user.setPassword(employeeCreateDto.getNationalId());  //to be encripted
        employee.setUser(user);
        userRepository.save(user);

        employee.setSuperiorId(getSuperiorIdByJobTitle(employeeCreateDto.getJobTitle()));
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
        employee.setJobTitle(employeeCreateDto.getJobTitle());
        employee = employeeRepository.save(employee);

        Manager manager = managerRepository.findById(employee.getSuperiorId()).orElseThrow();
        manager.getEmployees().add(employee);
        managerRepository.save(manager);

        return mapEmployee(employee);
    }

    public Long getSuperiorIdByJobTitle(String jobTitle) {

        Long superiorId = null;
        if (IT_DEPARTMENT_JOB_TITLES.contains(jobTitle)) {
            Department department = departmentRepository.findByDepartmentName("IT");
            superiorId = department.getManagerId();
        }
        if (SALES_DEPARTMENT_JOB_TITLES.contains(jobTitle)) {
            Department department = departmentRepository.findByDepartmentName("SALES");
            superiorId = department.getManagerId();
        }
        if (HR_DEPARTMENT_JOB_TITLES.contains(jobTitle)) {
            Department department = departmentRepository.findByDepartmentName("HR");
            superiorId = department.getManagerId();
        }
        return superiorId;

    }

    public String deleteEmployee(Long employeeId) {

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
