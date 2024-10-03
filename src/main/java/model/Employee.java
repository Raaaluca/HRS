package model;

import jakarta.persistence.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDate startDay;
    private Employee manager;
    @OneToMany
    private List<LeaveRequest> leaveRequestList;
}
