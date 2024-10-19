package ro.siit.HRS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToOne
    private User user;
    private String email;
    private String phoneNumber;
    private String city;
    private Long superiorId;
    private String nationalId;
    private String gender;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private String jobTitle;
    @OneToMany
    private List<LeaveRequest> leaveRequests;
}
