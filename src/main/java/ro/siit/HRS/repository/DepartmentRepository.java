package ro.siit.HRS.repository;

import ro.siit.HRS.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    public Department findByDepartmentName(String name);
}
