package ro.siit.HRS.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestCreateDto {
    private String typeOfLeaveRequest; // baza ierarhie cu tipurile de concediu (ccc, co, cfp, cm, evd)
    private int numberOfDaysForLeaveRequest;
    private Long employeeId;
    private Long managerId;
}
