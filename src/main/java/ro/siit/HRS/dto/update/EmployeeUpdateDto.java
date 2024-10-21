package ro.siit.HRS.dto.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeUpdateDto {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String city;
    private String address;
    private LocalDate endDate;
    private String jobTitle;
}
