package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.Function;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.FunctionRepository;

@Service
public class FunctionService {
    @Autowired
    private FunctionRepository functionRepository;
    public Function findById(Long id) {

        return functionRepository.findById(id)
                .orElseThrow();
    }
}
