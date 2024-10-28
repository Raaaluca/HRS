package ro.siit.HRS.dto.rturn;

import lombok.Data;

@Data
public class LeaveRequestReturnDto {

    private Long id;
    private String superiorName;
    private String typeOfLeaveRequest;
    private int numberOfDaysForLeaveRequest;
    private String jobTitle;
    private String employeeName;
    private String status;
    private Integer annualLeaveDays;
}
