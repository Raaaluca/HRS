package ro.siit.HRS.repository;

import ro.siit.HRS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
