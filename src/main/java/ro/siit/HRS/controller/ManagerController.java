package ro.siit.HRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.service.ManagerService;

@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @GetMapping(path = "/managers/id")
    public Manager getManagerById(@RequestParam Long id) {

        return managerService.findById(id);
    }
    @GetMapping(path = "/managers/create")
    public Manager createManager() {

        return managerService.createManager();
    }

    @GetMapping(path = "/managers/add")
    public Manager addEmployee(@RequestParam Long employeeId, @RequestParam Long managerId) {

        return managerService.addEmployee(employeeId, managerId);
    }


}
