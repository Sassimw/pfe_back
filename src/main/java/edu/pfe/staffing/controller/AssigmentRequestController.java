package edu.pfe.staffing.controller;

import edu.pfe.staffing.model.AssignmentRequest;
import edu.pfe.staffing.model.User;
import edu.pfe.staffing.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/request")
public class AssigmentRequestController {

    @Autowired
    RequestService requestService;
    @GetMapping("/AllRequests")
    public ResponseEntity<?> allrequests() {

        List<AssignmentRequest> allRequestsList = requestService.getAllRequests();

        return ResponseEntity.status(HttpStatus.OK).body(allRequestsList);
    }
}
