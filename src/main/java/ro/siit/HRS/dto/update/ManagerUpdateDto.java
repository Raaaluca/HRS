package ro.siit.HRS.dto.update;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ManagerUpdateDto {

    private Long id;
    @Pattern(regexp = "^[A-Z][a-z]+\\s[A-Z][a-z]+$", message = "Please enter a valid name!")
    @Size(min = 4, max = 35, message = "Name should be at least 4 characters and maximum 35!")
    private String name;
    private String email;
    private String city;
    @Pattern(regexp = "^\\d{10}$", message = "Number should contain 10 digits!")
    private String phoneNumber;
    private String address;
    private LocalDate endDate;
    @Min(value = 1, message = "At least 1 day is needed")
    @Max(value = 21, message = "Maximum 21 days are allowed")
    private Integer annualLeaveDays;

}
