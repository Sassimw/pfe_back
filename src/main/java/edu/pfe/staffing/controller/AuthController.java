package edu.pfe.staffing.controller;

import edu.pfe.staffing.dao.LoginRequest;
import edu.pfe.staffing.model.User;
import edu.pfe.staffing.security.JwtTokenUtil;
import edu.pfe.staffing.security.UserDetailsServiceImpl;
import edu.pfe.staffing.service.AuthenticationService;
import edu.pfe.staffing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/Auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<?> Registernewuser(@RequestBody User user) {
        System.out.println(user);
        userService.register(user);
        HashMap<String, String> Msg = new HashMap<>();
        Msg.put("message", "User has been created ");
        return ResponseEntity.status(HttpStatus.OK).body(Msg);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Loginuser(@RequestBody LoginRequest loginRequest) throws Exception {

        authenticationService.authenticate(loginRequest.getLogin(), loginRequest.getPwd());

        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginRequest.getLogin());

        User user=userService.findUserByMatcle(loginRequest.getLogin());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token,user.getRoles().get(0).getName()));
    }
}
