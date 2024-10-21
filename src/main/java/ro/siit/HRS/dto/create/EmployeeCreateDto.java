package ro.siit.HRS.dto.create;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeCreateDto {

    private String name;
    private String email;
    private String city;
    private String phoneNumber;
    private Long superiorId;
    private String nationalId;
    private String gender;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private String jobTitle;

}
