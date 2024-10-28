package ro.siit.HRS.repository;

import ro.siit.HRS.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.model.User;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    public Optional<Employee> findByUser(User user);
}
