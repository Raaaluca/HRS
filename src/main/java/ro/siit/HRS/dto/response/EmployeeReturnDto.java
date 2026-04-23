package ro.siit.HRS.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeReturnDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private Long superiorId;
    private String superiorName;
    private String address;
    private String city;
    private String gender;
    private LocalDate startDate;
    private LocalDate endDate;
    private String jobTitle;
    private Integer annualLeaveDays;
}
