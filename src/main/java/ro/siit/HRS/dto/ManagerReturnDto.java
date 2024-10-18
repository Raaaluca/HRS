package ro.siit.HRS.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ManagerReturnDto {

    private String name;
    private String email;
    private String city;
    private String phoneNumber;
    private String nationalId;
    private String gender;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
}
