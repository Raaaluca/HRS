package ro.siit.HRS.dto;

import lombok.Data;

@Data
public class DepartmentReturnDto {

    private Long id;
    private String departmentName;
    private Long managerId;
}
