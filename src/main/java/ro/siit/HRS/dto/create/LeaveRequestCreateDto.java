package ro.siit.HRS.dto.create;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestCreateDto {

    private String typeOfLeaveRequest;
    private int numberOfDaysForLeaveRequest;
    private Long employeeId;
    private Long managerId;
}
