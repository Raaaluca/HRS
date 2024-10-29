package ro.siit.HRS.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;



@Data
public class LeaveRequestCreateDto {

    private String typeOfLeaveRequest;
    @Min(value = 1, message = "At least 1 day is needed")
    @Max(value = 21, message = "Maximum 21 days are allowed")
    private int numberOfDaysForLeaveRequest;
    private Long employeeId;
    private Long managerId;
}
