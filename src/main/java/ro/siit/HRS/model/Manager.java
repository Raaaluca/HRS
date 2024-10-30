package ro.siit.HRS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "managers")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User user;
    @OneToMany
    private List<Employee> employees;
    @OneToMany
    private List<LeaveRequest> leaveRequestsToManage;
    private String name;
    private String email;
    private String city;
    private String phoneNumber;
    private String nationalId;
    private String gender;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private String jobTitle;
    private Integer annualLeaveDays;
    @OneToMany
    private List<LeaveRequest> leaveRequests;
}
