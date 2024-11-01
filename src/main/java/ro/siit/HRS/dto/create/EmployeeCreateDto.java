package ro.siit.HRS.dto.create;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class EmployeeCreateDto {

    @Pattern(regexp = "^[A-Z][a-z]+\\s[A-Z][a-z]+$", message = "Please enter a valid name!")
    @Size(min = 4, max = 35, message = "Name should be at least 4 characters and maximum 35!")
    private String name;
    @Email(message = "Please enter a valid email address!")
    @NotEmpty(message = "Email should not be empty")
    private String email;
    @NotEmpty(message = "City should not be empty")
    private String city;
    @Pattern(regexp = "^\\d{10}$", message = "Number should contain 10 digits!")
    private String phoneNumber;
    private Long superiorId;
    @NotEmpty(message = "Please enter the national Id!")
    private String nationalId;
    @NotEmpty(message = "Please select the gender!")
    private String gender;
    @NotEmpty(message = "Address should not be empty")
    private String address;
    @FutureOrPresent(message = "Start date should be today or future, format yyyy-MM-dd!")
    private LocalDate startDate;
    @Future(message = "End date should be in the future, format yyyy-MM-dd!")
    private LocalDate endDate;
    @NotEmpty(message = "Please select the job title!")
    private String jobTitle;

}
