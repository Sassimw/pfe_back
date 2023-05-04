package edu.pfe.staffing.repository;

import edu.pfe.staffing.model.Assignment;
import edu.pfe.staffing.model.Project;
import edu.pfe.staffing.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    List<Assignment> findAllByProject(Project project);
}
