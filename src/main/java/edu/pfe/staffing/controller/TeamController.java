package edu.pfe.staffing.controller;

import edu.pfe.staffing.model.Team;
import edu.pfe.staffing.model.User;
import edu.pfe.staffing.repository.TeamRepository;
import edu.pfe.staffing.service.TeamService;
import edu.pfe.staffing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addnewteam(@RequestBody Team team) {
        teamService.createTeam(team);
        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("message", "INSERTED");
        return ResponseEntity.status(HttpStatus.OK).body(Msg);
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<?> deleteteam() {
        teamService.deleteAll();
        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("Message", "deletedall");
        return ResponseEntity.status(HttpStatus.OK).body(Msg);

    }

    @DeleteMapping("/deleteteamid/{id}")
    public ResponseEntity<?> deleteteamid(@PathVariable("id") long teamid) {
        Team tm = teamService.Viewteamsid(teamid);
        HashMap<String, String> Msg = new HashMap<>();
        if (tm == null) {
            Msg.put("message", "The Following id does not exist " + teamid);
        } else {
            teamService.deleteteamid(teamid);

            Msg.put("message", "deletedid " + teamid);
        }

        return ResponseEntity.status(HttpStatus.OK).body(Msg);

    }

    @GetMapping("/viewallteams")
    public ResponseEntity<?> Viewallteams() {
        List<Team> allteams = teamService.Viewallteams();
        return ResponseEntity.status(HttpStatus.OK).body(allteams);


    }
    // commentaire

    /*
    PathVariable est un utilitaire : quelque chose qui existe dans le path mais qui est variable
    on a utiliser celà psk on a comme path :/viewteamsid/{id}  pour la partie pas fixe comme viewteamsid mais pour la partie dynamique {id}
     */
    @GetMapping("/viewteamsid/{id}")
    public ResponseEntity<?> Viewteamsid(@PathVariable("id") long teamid) {
        Team t = teamService.Viewteamsid(teamid);
        HashMap<String, String> Msg = new HashMap<>();
        if (t == null) {
            Msg.put("Message", "The requested teamid does not exist " + teamid);
            return ResponseEntity.status(HttpStatus.OK).body(Msg);
        }


        return ResponseEntity.status(HttpStatus.OK).body(t);
        /*Web service avec raffinement

         */
    }

    @GetMapping("/{teamId}/members")
    public ResponseEntity<?> getAllUsersForOneTeam(@PathVariable("teamId") long teamId){
        Team t = teamService.Viewteamsid(teamId);
        HashMap<String, String> Msg = new HashMap<>();
        if (t == null) {
            Msg.put("Message", "The requested teamid does not exist " + teamId);
            return ResponseEntity.status(HttpStatus.OK).body(Msg);
        }
        return ResponseEntity.status(HttpStatus.OK).body(t.getUserList());
    }

    /*
    c'est un parametre c'est un utilitaire
    pas comme le path , le request body n'est pas dans le path mais quelque chose envoyé pa par l'url mais avec l'url
     */
    @PutMapping("/updateteamid")
    public ResponseEntity<?> Updateteamsid(@RequestBody Team t) {
        Team tm = teamService.Viewteamsid(t.getId());

        HashMap<String, String> Msg = new HashMap<>();
        if (tm == null) {

            Msg.put("Message", "The updated Team does not exist:" + t.getId());
            return ResponseEntity.status(HttpStatus.OK).body(Msg);
        } else {
            tm.setName(t.getName());
            teamService.Updateteam(tm);
            Msg.put("Message", "TeamUpdated:" + t.getId());
        }

        return ResponseEntity.status(HttpStatus.OK).body(Msg);

    }
    @PostMapping("/Updatemanager")
    public ResponseEntity<?> updatemanager(@PathParam("teamid") long teamid, @PathParam("userid") long userid) {
        Team t = teamService.findById(teamid);
        User u = userService.findUserById(userid);
        if ( t.getId() == u.getTeam().getId())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A member of a team cannot be the manager of it");
        t.setManager(u);
        teamService.Updateteam(t);
        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("Message", "Team updated:" + t.getId());
        return ResponseEntity.status(HttpStatus.OK).body(Msg);
    }

    @PostMapping("/Updatename")
    public ResponseEntity<?> updatename(@PathParam("teamid") long teamid, @PathParam("name") String name) {
        Team t = teamService.findById(teamid);
        t.setName(name);
        teamService.Updateteam(t);
        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("Message", "Team updated:" + t.getId());
        return ResponseEntity.status(HttpStatus.OK).body(Msg);
    }
}
