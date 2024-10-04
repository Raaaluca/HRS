package ro.siit.HRS.repository;

import ro.siit.HRS.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long> {
}
