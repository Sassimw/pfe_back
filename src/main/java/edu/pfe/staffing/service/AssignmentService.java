package edu.pfe.staffing.service;

import edu.pfe.staffing.model.Assignment;
import edu.pfe.staffing.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService {

    @Autowired
    AssignmentRepository assignmentRepository;

    public Assignment getAssignmentById(long id){
        return assignmentRepository.getOne(id);
    }

    public void updateAssignment(Assignment assignment) {
        this.assignmentRepository.save(assignment);
    }
}
