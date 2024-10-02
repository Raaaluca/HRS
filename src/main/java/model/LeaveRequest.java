package model;

import jakarta.persistence.*;

@Entity
@Table(name = "leaverequests")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private int numberOfDays;
    private Employee sender;
    private Manager approver;
    private boolean isApproved;
}
