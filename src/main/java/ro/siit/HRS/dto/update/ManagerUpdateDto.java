package ro.siit.HRS.dto.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ManagerUpdateDto {

    private Long id;
    private String name;
    private String email;
    private String city;
    private String phoneNumber;
    private String address;
    private LocalDate endDate;
    private Integer annualLeaveDays;

}
