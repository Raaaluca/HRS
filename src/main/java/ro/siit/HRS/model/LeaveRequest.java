package ro.siit.HRS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "leaverequests")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private int numberOfDays;
    private Long employeeId;
    private Long managerId;
    private boolean isApproved;
}
