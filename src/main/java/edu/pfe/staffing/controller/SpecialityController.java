package edu.pfe.staffing.controller;

import edu.pfe.staffing.model.Team;
import edu.pfe.staffing.model.User;
import edu.pfe.staffing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Speciality")
public class SpecialityController {
    @Autowired
    UserService userService;

    @GetMapping("/ReasearchUserBySpeciality")
    public ResponseEntity<?> ReasearchUserBySpeciality(@PathParam("speciality") String speciality) {

        List<User> u = userService.findUserBySpeciality(speciality);

        HashMap<String, String> Msg = new HashMap<>();
        if (u == null) {
            Msg.put("Message", "There is no user in this speciality" + speciality);

        }
        return ResponseEntity.status(HttpStatus.OK).body(u);
    }
}