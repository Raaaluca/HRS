package ro.siit.HRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ro.siit.HRS.service.ManagerService;

@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;

}
