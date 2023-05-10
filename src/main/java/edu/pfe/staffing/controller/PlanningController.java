package edu.pfe.staffing.controller;

import edu.pfe.staffing.model.Assignment;
import edu.pfe.staffing.model.Planning;
import edu.pfe.staffing.model.Team;
import edu.pfe.staffing.model.User;
import edu.pfe.staffing.service.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/planning")

public class PlanningController {
    //user.getplaning .get id =idplanning a partir de user
    @Autowired
    PlanningService planningService;
    @Autowired
    UserService userService;
    @Autowired
    TeamService teamService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    ProjectService projectService;

    @PostMapping("/add")
    public ResponseEntity<?> addnewassignement(@RequestParam("userid") long userid, @RequestParam("projectid") String project, @RequestParam("month") int month, @RequestParam("day") int day) {
        planningService.AddAssignmentToPlanning(userService.findUserById(userid).getPlanning().getId(), month, day, project);
        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("Message", "New Assignment to a project has been added to a user planning");
        return ResponseEntity.status(HttpStatus.OK).body(Msg);
    }
    @PostMapping("/addFromReq")
    public ResponseEntity<?> addnewassignement(@RequestParam("userid") long userid, @RequestParam("projectid") String project, @RequestParam("month") int month, @RequestParam("day") int day, @RequestParam("reqid") int reqid) {


        planningService.AddAssignmentToPlanning(userService.findUserById(userid).getPlanning().getId(), month, day, project,reqid);
        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("Message", "New Assignment to a project has been added to a user planning");
        return ResponseEntity.status(HttpStatus.OK).body(Msg);
    }
    @GetMapping("/ExportPlaningForOneUser")
    public ResponseEntity<?> exportplaningforoneuser(@PathParam("userid") long userid) {
        User i = userService.findUserById(userid);
        return ResponseEntity.status(HttpStatus.OK).body(i.getPlanning());

    }

    @GetMapping("/{userId}/download-planning")
    public ResponseEntity<?> downloadPlanning(@PathVariable("userId") long userId) {
        User user = userService.findUserById(userId);
        Planning planning = user.getPlanning();
        File file = planningService.savePlanningToFile(planning);

        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }

    @GetMapping("/ExportPlanningForTEAM")
    public ResponseEntity<?> exportplanningforteam(@PathParam("teamid") long teamid) {
        Team t = teamService.findById(teamid);
        List<User> u = t.getUserList();
        List<Planning> p = new ArrayList<>();
        for (int i = 0; i < u.size(); i++) {
            User n = u.get(i);
            Planning pl = n.getPlanning();
            p.add(pl);
        }

        return ResponseEntity.status(HttpStatus.OK).body(p);
    }

    @DeleteMapping("/{assignmentId}/delete")
    public ResponseEntity<?> deleteAssigbment(@PathVariable("assignmentId") long assignmentId) {
        this.planningService.deleteAssignmentById(assignmentId);
        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("Message", "Deleted assignment");
        return ResponseEntity.status(HttpStatus.OK).body(Msg);
    }

    @PutMapping("/{assignmentId}/update")
    public ResponseEntity<?> updateAssignment(@PathVariable("assignmentId") long assignmentid, @RequestParam("projectId") long projectId) {
        Assignment assignment = this.assignmentService.getAssignmentById(assignmentid);
        assignment.setProject(projectService.getById(projectId));
        assignmentService.updateAssignment(assignment);
        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("Message", "Updated assignment");
        return ResponseEntity.status(HttpStatus.OK).body(Msg);
    }
}
