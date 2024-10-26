package ro.siit.HRS.repository;

import ro.siit.HRS.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    public Department findByDepartmentName(String name);

    public Optional<Department> findByManagerId(Long managerId);
}
