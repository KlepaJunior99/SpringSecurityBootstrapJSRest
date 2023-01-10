package springsecurity.result_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springsecurity.result_project.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
