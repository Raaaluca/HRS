package ro.siit.HRS.dto.create;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LeaveRequestCreateDto {

    private Long id;
    @NotEmpty(message = "Please select the leave request type!")
    private String typeOfLeaveRequest;
    @Min(value = 1, message = "At least 1 day is needed!")
    @Max(value = 21, message = "Maximum 21 days are allowed!")
    private int numberOfDaysForLeaveRequest;
    private Long employeeId;
    private Long managerId;
    private boolean isApproved;
}
