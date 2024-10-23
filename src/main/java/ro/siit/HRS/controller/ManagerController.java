package ro.siit.HRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.create.ManagerCreateDto;
import ro.siit.HRS.dto.rturn.EmployeeReturnDto;
import ro.siit.HRS.dto.rturn.ManagerReturnDto;
import ro.siit.HRS.dto.update.EmployeeUpdateDto;
import ro.siit.HRS.dto.update.ManagerUpdateDto;
import ro.siit.HRS.service.ManagerService;

@RestController
@RequestMapping(path = "/managers")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @GetMapping(path = "/id")
    public ManagerReturnDto getManagerById(@RequestParam Long id) {

        return managerService.findById(id);
    }

    @PostMapping(path = "/create")
    public ManagerReturnDto createManager(@RequestBody ManagerCreateDto managerCreateDto) {

        return managerService.createManager(managerCreateDto);
    }

    @GetMapping(path = "/add")
    public ManagerReturnDto addEmployee(@RequestParam Long employeeId, @RequestParam Long managerId) {

        return managerService.assignEmployeeToManager(employeeId, managerId);
    }

    @DeleteMapping(path = "/delete")
    public String deleteManager(@RequestParam Long managerId) {

        return managerService.deleteManager(managerId);
    }
    @PutMapping(path = "/update")
    public ManagerReturnDto update(@RequestBody ManagerUpdateDto managerUpdateDto) {

        return managerService.updateManager(managerUpdateDto);
    }
}
