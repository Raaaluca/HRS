package ro.siit.HRS.repository;

import ro.siit.HRS.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.siit.HRS.model.User;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    public Optional<Manager> findByUser(User user);

}
