package ro.siit.HRS.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeReturnDto {

    private String email;
    private Long superiorId;
    private String city;
    private String gender;
    private LocalDate startDate;
    private LocalDate endDate;
    private String jobTitle;
}
