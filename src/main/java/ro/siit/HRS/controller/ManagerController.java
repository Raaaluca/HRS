package ro.siit.HRS.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.create.ManagerCreateDto;
import ro.siit.HRS.dto.response.ManagerReturnDto;
import ro.siit.HRS.dto.update.ManagerUpdateDto;
import ro.siit.HRS.service.ManagerService;

@RestController
@RequestMapping(path = "/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping(path = "/{managerId}")
    public ResponseEntity<ManagerReturnDto> getManagerById(@PathVariable Long managerId) {

        return ResponseEntity
                .ok()
                .body(managerService.findById(managerId));
    }

    @PostMapping
    public ResponseEntity<ManagerReturnDto> createManager(@RequestBody @Valid ManagerCreateDto managerCreateDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(managerService.createManager(managerCreateDto));
    }

    @PutMapping(path = "/add/{employeeId}/{managerId}")
    public ResponseEntity<ManagerReturnDto> addEmployee(@PathVariable Long employeeId, @PathVariable Long managerId) {

        return ResponseEntity.ok(managerService.assignEmployeeToManager(employeeId, managerId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteManager(@RequestParam Long managerId) {

        managerService.deleteManager(managerId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<ManagerReturnDto> updateManager(@RequestBody @Valid ManagerUpdateDto managerUpdateDto) {

        return ResponseEntity
                .ok()
                .body(managerService.updateManager(managerUpdateDto));
    }
}
