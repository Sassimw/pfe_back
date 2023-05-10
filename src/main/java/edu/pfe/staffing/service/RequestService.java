package edu.pfe.staffing.service;

import edu.pfe.staffing.model.AssignmentRequest;
import edu.pfe.staffing.repository.AssignmentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    AssignmentRequestRepository assignmentRequestRepository;


    public List<AssignmentRequest> getAllRequests() {
        return assignmentRequestRepository.findAll();
    }
}
