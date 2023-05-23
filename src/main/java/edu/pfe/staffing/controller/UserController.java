package edu.pfe.staffing.controller;

import edu.pfe.staffing.model.AssignmentRequest;
import edu.pfe.staffing.model.Project;
import edu.pfe.staffing.model.Team;
import edu.pfe.staffing.model.User;
import edu.pfe.staffing.security.JwtTokenUtil;
import edu.pfe.staffing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    TeamService teamService;
    @Autowired
    EmailService emailService;

    @PostMapping("/Updateteam")
    public ResponseEntity<?> addusertoateam(@PathParam("userid") long userid, @PathParam("teamid") long teamid) {
        Team t = teamService.findById(teamid);
        User u = userService.findUserById(userid);
        u.setTeam(t);
        userService.UpdateUser(u);
        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("Message", "UserUpdated:" + u.getId());
        //Date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        String body = "Hello " + u.getFirstname() + " "
                + u.getLastname() + " , You have been changed to the team "  + t.getName() + " on " + date;
        String Subject="[Staffing App] Team Changed";
        emailService.sendEmail(u.getEmail(),Subject,body );
        return ResponseEntity.status(HttpStatus.OK).body(Msg);
    }





    @PostMapping("/UpdateUser")
    public ResponseEntity<?> UpdateUser(@PathParam("userid") long userid, @PathParam("firstname") String firstname,@PathParam("lastname") String lastname,@PathParam("email") String email)  {
        User u = userService.findUserById(userid);
        u.setFirstname(firstname);
        u.setLastname(lastname);
        u.setEmail(email);
        userService.UpdateUser(u);
        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("Message", "UserUpdated:" + u.getId());
        return ResponseEntity.status(HttpStatus.OK).body(Msg);
    }



    @GetMapping("/calculate")
    public ResponseEntity<?> calculateAllScores() {
        HashMap<String, String> Msg = new HashMap<>();
        try {
            List<User> allUsers = userService.getAllUsers();
            for (User user : allUsers) {
                user.setNbrOfProjectsOfUser(user.getnumberofuserprojects());
                user.setGlobalUserScore(user.getuserglobalscoreperproject());
            }
            userService.updateAllUsers(allUsers);
            Msg.put("message", "All scores have been calculates");
        } catch (Exception e) {
            e.printStackTrace();
            Msg.put("message", "Error when updating users scores");
        }
        return ResponseEntity.status(HttpStatus.OK).body(Msg);
    }

    @GetMapping("/AllUsers")
    public ResponseEntity<?> allusers() {
        User u ;
        List<User> finallist = new ArrayList<>();
        List<User> alluserslist = userService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(alluserslist);
    }

    @GetMapping("/{userId}/details")
    public ResponseEntity<?> getOneUserDetails(@PathVariable("userId") long userId) {
        User user = userService.findUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/team/{userid}")
    public ResponseEntity<?> getTeam(@PathVariable("userid") long userId) {
        User user = userService.findUserById(userId);
        Team team = teamService.findById(user.getTeam().getId());
        return ResponseEntity.status(HttpStatus.OK).body(team);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchForUsers(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "speciality", required = false) String speciality) {
        List<User> users;
        if (speciality != null && name == null) {
            users = userService.findUserBySpeciality(speciality);
        } else if (speciality == null && name != null) {
            users = userService.findByFirstNameOrLastName(name);
        } else {
            users = new ArrayList<>();
        }
        return ResponseEntity.ok().body(users);
    }

    @Autowired
    ProjectService projectService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AssignmentRequestService assignmentRequestService;

    @GetMapping("/{userId}/request/{projectId}")
    public ResponseEntity<?> requestUser(@PathVariable("userId") long userId, @PathVariable("projectId") long projectId, @RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("day") int day, HttpServletRequest httpServletRequest) {
        User user = userService.findUserById(userId);
        Team team = user.getTeam();
        User manager = team.getManager();

        String token = httpServletRequest.getHeader("Authorization");
        if (token.startsWith("Bearer")) {
            token = token.substring("Bearer ".length());
        }
        String matcle = jwtTokenUtil.getUsernameFromToken(token);
        User requester = userService.findUserByMatcle(matcle);

        Project project = projectService.getById(projectId);

        AssignmentRequest assignmentRequest = new AssignmentRequest();
        assignmentRequest.setRequester(requester);
        assignmentRequest.setReceiver(manager);
        assignmentRequest.setTargetProject(project);
        assignmentRequest.setResource(user);
        assignmentRequest.setCreationDate(LocalDate.now());
        assignmentRequest.setRequestedDate(LocalDate.of(year, month, day));

        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("message", "Request sent");

        try {
            assignmentRequestService.saveNewAssignment(assignmentRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Msg.put("message", "Error when sending request");
        }

        return ResponseEntity.ok().body(Msg);
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<?> deleteuser(@PathVariable("id") long userid) {
        User user = userService.findUserById(userid);
        HashMap<String, String> Msg = new HashMap<>();
        if ( user == null) {
            Msg.put("message", "The Following id does not exist " + user);
        } else {
            userService.deleteUser(userid);

            Msg.put("message", "deletedid " + userid);
        }

        return ResponseEntity.status(HttpStatus.OK).body(Msg);

    }

}
