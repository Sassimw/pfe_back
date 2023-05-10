package edu.pfe.staffing.repository;

import edu.pfe.staffing.model.AssignmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRequestRepository extends JpaRepository<AssignmentRequest, Long> {


}
