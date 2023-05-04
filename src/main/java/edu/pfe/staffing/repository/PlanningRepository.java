package edu.pfe.staffing.repository;

import edu.pfe.staffing.model.Assignment;
import edu.pfe.staffing.model.Planning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanningRepository extends JpaRepository<Planning,Long> {

}
