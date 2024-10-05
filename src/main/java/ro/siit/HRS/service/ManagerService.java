package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.ManagerRepository;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    public Manager findById(Long id) {

        return managerRepository.findById(id)
                .orElseThrow();
    }
}
