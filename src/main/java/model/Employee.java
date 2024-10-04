package model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private User user;
    private String email;
    private String phoneNumber;
    private String nationalId;
    private String gender;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee manager;
    @OneToMany
    private List<LeaveRequest> leaveRequestList;
}
