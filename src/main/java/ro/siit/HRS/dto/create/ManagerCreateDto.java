package ro.siit.HRS.dto.create;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ManagerCreateDto {

    @Pattern(regexp = "^[A-Z][a-z]+\\s[A-Z][a-z]+$", message = "Please enter a valid name!")
    @Size(min = 4, max = 35, message = "Name should be at least 4 characters and maximum 35!")
    private String name;
    private String email;
    private String city;
    @Pattern(regexp = "^\\d{10}$", message = "Number should contain 10 digits!")
    private String phoneNumber;
    private String nationalId;
    private String gender;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;

}
