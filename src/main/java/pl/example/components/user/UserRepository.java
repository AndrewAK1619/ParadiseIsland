package pl.example.components.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.example.components.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByEmail(String email);
    List<User> findAllByOrderByIdAsc();
    List<User> findAllByLastNameContainingIgnoreCaseOrderByIdAsc(String lastName);
}
