package pl.example.components.user.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	
	UserRole findByRole(String role);
}