package ro.siit.HRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.ManagerCreateDto;
import ro.siit.HRS.dto.ManagerReturnDto;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.service.ManagerService;

@RestController
@RequestMapping(path="/managers")
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @GetMapping(path = "/id")
    public ManagerReturnDto getManagerById(@RequestParam Long id) {

        return managerService.findById(id);
    }
    @GetMapping(path = "/create")
    public ManagerReturnDto createManager(@RequestBody ManagerCreateDto managerCreateDto) {

        return managerService.createManager(managerCreateDto);
    }

    @GetMapping(path = "/add")
    public ManagerReturnDto addEmployee(@RequestParam Long employeeId, @RequestParam Long managerId) {

        return managerService.addEmployee(employeeId, managerId);
    }


}
