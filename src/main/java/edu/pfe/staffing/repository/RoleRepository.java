package edu.pfe.staffing.repository;

import edu.pfe.staffing.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role,Long> {
    Role findOneByName(String name);
}
