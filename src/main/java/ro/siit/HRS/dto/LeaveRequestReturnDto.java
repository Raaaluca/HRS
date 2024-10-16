package ro.siit.HRS.dto;

import lombok.Data;

@Data
public class LeaveRequestReturnDto {
    private String typeOfLeaveRequest;
    private int numberOfDaysForLeaveRequest;
    private Long employeeId;
    private Long managerId;
    private boolean isApproved;
}
