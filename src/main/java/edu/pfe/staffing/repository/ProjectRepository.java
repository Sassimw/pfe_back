package edu.pfe.staffing.repository;

import edu.pfe.staffing.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
