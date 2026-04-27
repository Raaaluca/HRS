package ro.siit.HRS.service;

import ro.siit.HRS.dto.response.EmployeeReturnDto;

import java.util.List;

public interface AdminService {

    List<EmployeeReturnDto> getAllEmployees();
}
