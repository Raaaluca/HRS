package ro.siit.HRS.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.siit.HRS.dto.create.EmployeeCreateDto;
import ro.siit.HRS.dto.response.EmployeeReturnDto;
import ro.siit.HRS.dto.response.LeaveRequestReturnDto;
import ro.siit.HRS.dto.update.EmployeeUpdateDto;
import ro.siit.HRS.exceptions.DepartmentNotFoundException;
import ro.siit.HRS.exceptions.EmployeeNotFoundException;
import ro.siit.HRS.exceptions.ManagerNotFoundException;
import ro.siit.HRS.exceptions.UserNotFoundException;
import ro.siit.HRS.model.*;
import ro.siit.HRS.repository.*;
import ro.siit.HRS.service.EmployeeService;
import ro.siit.HRS.util.MapperUtil;
import ro.siit.HRS.util.Role;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final MapperUtil mapperUtil;

    public static final List<String> IT_DEPARTMENT_JOB_TITLES = List.of("IT Engineer", "Tester", "UI/UX Designer");
    public static final List<String> SALES_DEPARTMENT_JOB_TITLES = List.of("Sales Officer", "Associate Officer");
    public static final List<String> HR_DEPARTMENT_JOB_TITLES = List.of("HR Admin", "Payroll Admin");
    private static final String DEPT_IT    = "IT";
    private static final String DEPT_SALES = "SALES";
    private static final String DEPT_HR    = "HR";

    public EmployeeReturnDto findById(Long id) {

        Employee employee = employeeRepository.findById(id).orElseThrow(()
                -> new EmployeeNotFoundException("This employee id " + id + " does not exist!"));

        return mapperUtil.mapEmployee(employee);
    }

    /**
     * This method creates {@link User} Entity in DB based on {@link EmployeeCreateDto} object
     * When a user from {@link Employee} Entity is created, will automatically have the role "EMPLOYEE"
     * and the password will be the employee`s national id
     *
     * @param employeeCreateDto the {@link EmployeeCreateDto} parameter object
     * @return the {@link User} Entity
     */
    private User createUser(EmployeeCreateDto employeeCreateDto) {

        User user = new User();
        user.setRole(Role.EMPLOYEE.name());
        user.setUsername(employeeCreateDto.getEmail());
        user.setPassword(passwordEncoder.encode(employeeCreateDto.getNationalId()));

        return user;
    }

    /**
     * This method creates an {@link Employee} entity in DB based on {@link EmployeeCreateDto} object
     * When an employee is created, the related {@link User} is also created,
     * the employee will have the manager assigned based on the job title
     * and the {@link Manager} {@link List} of {@link Employee} will be updated
     * Throws a {@link ManagerNotFoundException} when the manager is not found
     *
     * @param employeeCreateDto the {@link EmployeeCreateDto} parameter object
     * @return the {@link EmployeeReturnDto} object
     */
    @Transactional
    public EmployeeReturnDto createEmployee(EmployeeCreateDto employeeCreateDto) {

        log.info("Preparing to create User from {}", employeeCreateDto);
        User user = createUser(employeeCreateDto);
        userRepository.save(user);
        log.info("User created successfully with id: {}", user.getId());

        log.info("Preparing to set Employee from {}", employeeCreateDto);
        Employee employee = mapperUtil.mapEmployeeEntity(employeeCreateDto);
        employee.setUser(user);
        employee = employeeRepository.save(employee);
        log.info("Employee created successfully with id: {}", employee.getId());

        log.info("Preparing to get manager Id from {}", employee.getSuperiorId());
        Long managerId = employee.getSuperiorId();
        Manager manager = managerRepository.findById(managerId).orElseThrow(()
                -> new ManagerNotFoundException("This manager id " + managerId + " does not exist"));

        manager.getEmployees().add(employee);
        managerRepository.save(manager);
        log.info("Employee {} successfully assigned to Manager {}", employee.getId(), employee.getSuperiorId());

        return mapperUtil.mapEmployee(employee);
    }

    public EmployeeReturnDto updateEmployee(EmployeeUpdateDto employeeUpdateDto) {

        Employee employee = employeeRepository.findById(employeeUpdateDto.getId()).orElseThrow(()
                -> new EmployeeNotFoundException("This employee id " + employeeUpdateDto.getId() + " can not be found!!"));
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
            employee.getUser().setUsername(employeeUpdateDto.getEmail());
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

        return mapperUtil.mapEmployee(employee);
    }

    public Long getSuperiorIdByJobTitle(String jobTitle) {

        if (IT_DEPARTMENT_JOB_TITLES.contains(jobTitle)) {
            Department department = departmentRepository.findByDepartmentName(DEPT_IT);
            if (department == null) {
                throw new DepartmentNotFoundException(DEPT_IT + " Department was not found");
            }
            return department.getManagerId();
        }
        if (SALES_DEPARTMENT_JOB_TITLES.contains(jobTitle)) {
            Department department = departmentRepository.findByDepartmentName(DEPT_SALES);
            if (department == null) {
                throw new DepartmentNotFoundException(DEPT_SALES + " Department was not found");
            }
            return department.getManagerId();
        }
        if (HR_DEPARTMENT_JOB_TITLES.contains(jobTitle)) {
            Department department = departmentRepository.findByDepartmentName(DEPT_HR);
            if (department == null) {
                throw new DepartmentNotFoundException(DEPT_HR + " Department was not found");
            }
            return department.getManagerId();
        }
        throw new DepartmentNotFoundException("No department found for job title: " + jobTitle);
    }

    @Transactional
    public String deleteEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()
                -> new EmployeeNotFoundException("The employee with id " + employeeId + " was not found!"));

        employee.getLeaveRequests().clear();
        employeeRepository.save(employee);

        Long managerId = employee.getSuperiorId();
        Manager manager = managerRepository.findById(managerId).orElseThrow(()
                -> new ManagerNotFoundException("This manager id " + managerId + " does not exist!"));

        manager.getEmployees().remove(employee);
        manager.getLeaveRequestsToManage()
                .removeIf(p -> p.getEmployeeId().equals(employee.getId()));
        managerRepository.save(manager);

        User user = userRepository.findById(employee.getUser().getId()).orElseThrow(()
                -> new UserNotFoundException("The user with id " + employee.getUser().getId() + "does not exist!"));
        employeeRepository.delete(employee);
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

        return mapperUtil.mapEmployee(employee);
    }

    public List<LeaveRequestReturnDto> myLeaveRequests(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Employee employee = employeeRepository.findByUser(user).orElseThrow();

        return employee.getLeaveRequests()
                .stream()
                .map(mapperUtil::mapLeaveRequestReturnDto)
                .toList();
    }

    public Integer getEmployeeRemainingDays(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Employee employee = employeeRepository.findByUser(user).orElseThrow();

        return employee.getAnnualLeaveDays();
    }

    public void updateEmployeeDto(EmployeeUpdateDto employeeUpdateDto) {

        Employee employee = employeeRepository.findById(employeeUpdateDto.getId()).orElseThrow(()
                -> new EmployeeNotFoundException(
                "This employee id: " + employeeUpdateDto.getId() + ", can not be found!!"));
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
