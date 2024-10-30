package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.create.EmployeeCreateDto;
import ro.siit.HRS.dto.rturn.EmployeeReturnDto;
import ro.siit.HRS.dto.rturn.LeaveRequestReturnDto;
import ro.siit.HRS.dto.rturn.ManagerReturnDto;
import ro.siit.HRS.dto.update.EmployeeUpdateDto;
import ro.siit.HRS.dto.update.ManagerUpdateDto;
import ro.siit.HRS.exceptions.DepartmentNotFoundException;
import ro.siit.HRS.exceptions.EmployeeNotFoundException;
import ro.siit.HRS.exceptions.ManagerNotFoundException;
import ro.siit.HRS.exceptions.UserNotFoundException;
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
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private LeaveRequestService leaveRequestService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final List<String> IT_DEPARTMENT_JOB_TITLES = List.of("IT Engineer", "Tester", "UI/UX Designer");
    public static final List<String> SALES_DEPARTMENT_JOB_TITLES = List.of("Sales Officer", "Associate Officer");
    public static final List<String> HR_DEPARTMENT_JOB_TITLES = List.of("HR Admin", "Payroll Admin");


    public EmployeeReturnDto mapEmployee(Employee employee) {

        EmployeeReturnDto employeeReturnDto = new EmployeeReturnDto();
        employeeReturnDto.setId(employee.getId());
        employeeReturnDto.setGender(employee.getGender());
        employeeReturnDto.setName(employee.getName());
        employeeReturnDto.setPhoneNumber(employee.getPhoneNumber());
        employeeReturnDto.setAddress(employee.getAddress());
        employeeReturnDto.setCity(employee.getCity());
        employeeReturnDto.setEmail(employee.getEmail());
        employeeReturnDto.setSuperiorId(employee.getSuperiorId());
        employeeReturnDto.setStartDate(employee.getStartDate());
        employeeReturnDto.setEndDate(employee.getEndDate());
        employeeReturnDto.setJobTitle(employee.getJobTitle());
        employeeReturnDto.setAnnualLeaveDays(employee.getAnnualLeaveDays());

        return employeeReturnDto;
    }

    public EmployeeReturnDto mapManagerToEmployeeReturnDto(Manager manager) {

        EmployeeReturnDto employeeReturnDto = new EmployeeReturnDto();
        employeeReturnDto.setId(manager.getId());
        employeeReturnDto.setGender(manager.getGender());
        employeeReturnDto.setName(manager.getName());
        employeeReturnDto.setPhoneNumber(manager.getPhoneNumber());
        employeeReturnDto.setAddress(manager.getAddress());
        employeeReturnDto.setCity(manager.getCity());
        employeeReturnDto.setEmail(manager.getEmail());
        employeeReturnDto.setStartDate(manager.getStartDate());
        employeeReturnDto.setEndDate(manager.getEndDate());
        employeeReturnDto.setJobTitle(manager.getJobTitle());
        employeeReturnDto.setAnnualLeaveDays(manager.getAnnualLeaveDays());

        return employeeReturnDto;
    }

    public EmployeeReturnDto findById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("This employee id " + id + "does not exist!"));
        return mapEmployee(employee);
    }


    public EmployeeReturnDto createEmployee(EmployeeCreateDto employeeCreateDto) {

        Employee employee = new Employee();

        User user = new User();
        user.setRole("EMPLOYEE");
        user.setUsername(employeeCreateDto.getEmail());
        user.setPassword(passwordEncoder.encode(employeeCreateDto.getNationalId()));
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
        employee.setAnnualLeaveDays(21);
        employee = employeeRepository.save(employee);

        Employee finalEmployee = employee;
        Manager manager = managerRepository.findById(employee.getSuperiorId())
                .orElseThrow(() -> new ManagerNotFoundException("This manager id" + finalEmployee.getSuperiorId() + "does not exist"));
        manager.getEmployees().add(employee);
        managerRepository.save(manager);

        return mapEmployee(employee);
    }

    public EmployeeReturnDto updateEmployee(EmployeeUpdateDto employeeUpdateDto) {

        Employee employee = employeeRepository.findById(employeeUpdateDto.getId())
                .orElseThrow(() -> new EmployeeNotFoundException("This employee id " + employeeUpdateDto.getId() + "can not be found!!"));
        if (employeeUpdateDto.getAddress() != null) {
            employee.setAddress(employeeUpdateDto.getAddress());
        }
        if (employeeUpdateDto.getName() != null) {
            employee.setName(employeeUpdateDto.getName());
        }
        if (employeeUpdateDto.getCity() != null) {
            employee.setCity(employeeUpdateDto.getCity());
        }
        if (employeeUpdateDto.getEmail() != null) {
            employee.setEmail(employeeUpdateDto.getEmail());
        }
        if (employeeUpdateDto.getJobTitle() != null) {
            employee.setJobTitle(employeeUpdateDto.getJobTitle());

            Manager oldManager = managerRepository.findById(employee.getSuperiorId()).orElseThrow();
            oldManager.getEmployees().remove(employee);
            managerRepository.save(oldManager);

            employee.setSuperiorId(getSuperiorIdByJobTitle(employee.getJobTitle()));
            Manager newManager = managerRepository.findById(employee.getSuperiorId()).orElseThrow();
            newManager.getEmployees().add(employee);
            managerRepository.save(newManager);
        }
        if (employeeUpdateDto.getPhoneNumber() != null) {
            employee.setPhoneNumber(employeeUpdateDto.getPhoneNumber());
        }
        if (employeeUpdateDto.getEndDate() != null) {
            employee.setEndDate(employeeUpdateDto.getEndDate());
        }

        employee = employeeRepository.save(employee);

        return mapEmployee(employee);
    }

    public Long getSuperiorIdByJobTitle(String jobTitle) {

        Long superiorId = null;
        if (IT_DEPARTMENT_JOB_TITLES.contains(jobTitle)) {
            Department department = departmentRepository.findByDepartmentName("IT");
            if (department == null) {
                throw new DepartmentNotFoundException("IT Department was not found");
            }
            superiorId = department.getManagerId();
        }
        if (SALES_DEPARTMENT_JOB_TITLES.contains(jobTitle)) {
            Department department = departmentRepository.findByDepartmentName("SALES");
            if (department == null) {
                throw new DepartmentNotFoundException("SALES Department was not found");
            }
            superiorId = department.getManagerId();
        }
        if (HR_DEPARTMENT_JOB_TITLES.contains(jobTitle)) {
            Department department = departmentRepository.findByDepartmentName("HR");
            if (department == null) {
                throw new DepartmentNotFoundException("HR Department was not found");
            }
            superiorId = department.getManagerId();
        }
        return superiorId;

    }

    public String deleteEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("The employee with id " + employeeId + "was not found!"));
        Long managerId = employee.getSuperiorId();

        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new ManagerNotFoundException("This manager id " + managerId + "does not exist!"));
        manager.getEmployees().remove(employee);

        managerRepository.save(manager);
        employeeRepository.delete(employee);

        User user = userRepository.findById(employee.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("The user with id " + employee.getUser().getId() + "does not exist!"));
        userRepository.deleteById(user.getId());
        return "This employee has been deleted!";
    }

    public String getAuthenticationDetails(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Employee employee = employeeRepository.findByUser(user).orElseThrow();

        return employee.getName() + ", " + employee.getJobTitle();
    }

    public EmployeeReturnDto getUpdatePersonalDetails(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Employee employee = employeeRepository.findByUser(user).orElseThrow();

        return mapEmployee(employee);
    }

    public List<LeaveRequestReturnDto> myLeaveRequests(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Employee employee = employeeRepository.findByUser(user).orElseThrow();

        return employee.getLeaveRequests()
                .stream()
                .map(p -> leaveRequestService.mapLeaveRequestReturnDto(p))
                .collect(Collectors.toList());
    }

    public Integer getEmployeeRemainingDays(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Employee employee = employeeRepository.findByUser(user).orElseThrow();

        return employee.getAnnualLeaveDays();
    }
    public void updateEmployeeDto(EmployeeUpdateDto employeeUpdateDto) {

        Employee employee = employeeRepository.findById(employeeUpdateDto.getId())
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "This employee id " + employeeUpdateDto.getId() + "can not be found!!"));
        if (employeeUpdateDto.getAddress() != null) {
            employee.setAddress(employeeUpdateDto.getAddress());
        }
        if (employeeUpdateDto.getName() != null) {
            employee.setName(employeeUpdateDto.getName());
        }
        if (employeeUpdateDto.getCity() != null) {
            employee.setCity(employeeUpdateDto.getCity());
        }
        if (employeeUpdateDto.getEmail() != null) {
            employee.setEmail(employeeUpdateDto.getEmail());
            employee.getUser().setUsername(employee.getEmail());
        }
        if (employeeUpdateDto.getEndDate() != null) {
            employee.setEndDate(employeeUpdateDto.getEndDate());
        }
        if (employeeUpdateDto.getPhoneNumber() != null) {
            employee.setPhoneNumber(employeeUpdateDto.getPhoneNumber());
        }
        employeeRepository.save(employee);
    }
}
