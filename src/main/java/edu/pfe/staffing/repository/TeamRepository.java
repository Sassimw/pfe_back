package edu.pfe.staffing.repository;

import edu.pfe.staffing.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository  extends JpaRepository <Team,Long> {
}
