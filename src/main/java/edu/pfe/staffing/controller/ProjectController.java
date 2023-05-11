package edu.pfe.staffing.controller;

import edu.pfe.staffing.model.Assignment;
import edu.pfe.staffing.model.Project;
import edu.pfe.staffing.model.Team;
import edu.pfe.staffing.model.User;
import edu.pfe.staffing.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> listAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping
    public ResponseEntity<?> addNewProject(@RequestBody Project project) {
        projectService.addProject(project);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Project added successfully");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{projectId}/members")
    public ResponseEntity<?> listAllMembersInProject(@PathVariable("projectId") long projectId){
        Project project = projectService.getById(projectId);
        List<User> users = projectService.listAllUsersAssignedToProject(project);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/deleteprojectid/{id}")
    public ResponseEntity<?> deleteprojectid(@PathVariable("id") long projectid) {
        Project prj = projectService.Viewprojectsid(projectid);
        HashMap<String, String> Msg = new HashMap<>();
        if (prj == null) {
            Msg.put("message", "The Following id does not exist " + projectid);
        } else {
            projectService.deleteProject(projectid);

            Msg.put("message", "deletedid " + projectid);
        }

        return ResponseEntity.status(HttpStatus.OK).body(Msg);

    }
    @PostMapping("/UpdateProject")
    public ResponseEntity<?> UpdateUser(@PathParam("projectid") long projectid, @PathParam("name") String name, @PathParam("budget") double budget
            , @PathParam("workload") int workload,@PathParam("crucuality") String crucuality)  {
        Project prj = projectService.Viewprojectsid(projectid);
        prj.setName(name);
        prj.setBudget(budget);
        prj.setWorkload(workload);
        prj.setCriticality(crucuality);
        projectService.updateProject(prj);
        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("Message", "UserUpdated:" + prj.getId());
        return ResponseEntity.status(HttpStatus.OK).body(Msg);
    }

}
