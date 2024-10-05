package ro.siit.HRS.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User findById(Long id) {

        return userRepository.findById(id)
                .orElseThrow();
    }
}
