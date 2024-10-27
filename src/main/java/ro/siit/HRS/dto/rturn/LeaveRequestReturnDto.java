package ro.siit.HRS.dto.rturn;

import lombok.Data;

@Data
public class LeaveRequestReturnDto {

    private Long id;
    private String typeOfLeaveRequest;
    private int numberOfDaysForLeaveRequest;
    private String employeeName;
    private String status;
}
