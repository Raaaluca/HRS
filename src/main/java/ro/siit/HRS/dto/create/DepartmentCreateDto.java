package ro.siit.HRS.dto.create;

import lombok.Data;

@Data
public class DepartmentCreateDto {

    private String departmentName;
    private Long managerId;
}
