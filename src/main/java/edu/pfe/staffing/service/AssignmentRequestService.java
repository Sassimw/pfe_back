package edu.pfe.staffing.service;

import edu.pfe.staffing.model.AssignmentRequest;
import edu.pfe.staffing.repository.AssignmentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentRequestService {

    @Autowired
    AssignmentRequestRepository assignmentRequestRepository;

    public void saveNewAssignment(AssignmentRequest assignmentRequest){
        assignmentRequestRepository.save(assignmentRequest);
    }
}
