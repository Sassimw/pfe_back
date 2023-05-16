package edu.pfe.staffing.service;

import edu.pfe.staffing.configuration.MainConfiguration;
import edu.pfe.staffing.dao.LoginRequest;
import edu.pfe.staffing.model.Planning;
import edu.pfe.staffing.model.Team;
import edu.pfe.staffing.model.User;
import edu.pfe.staffing.repository.PlanningRepository;
import edu.pfe.staffing.repository.RoleRepository;
import edu.pfe.staffing.repository.TeamRepository;
import edu.pfe.staffing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlanningRepository planningRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;


    @Autowired
    MainConfiguration mainConfiguration;

    public User register(User user) {
        user.setenabled(true);
        user.getRoleList().add(roleRepository.findOneByName("COLLABORATEUR"));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPlanning(planningRepository.save(new Planning()));
        user.setMatcle("SP" + mainConfiguration.getMaxMatcle());
        userRepository.save(user);
        mainConfiguration.inceremntMaxNudoss();
        return user;
    }

    public boolean login(LoginRequest loginRequest) {
        User user = userRepository.findOneByMatcleAndPassword(loginRequest.getLogin(), loginRequest.getPwd());
        if (user == null)
            return false;
        else return true;


    }


    public User findUserByMatcle(String matcle) {
        return userRepository.findOneByMatcle(matcle);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> findUserBySpeciality(String speciality) {
        return userRepository.findAllBySpecialityContainingIgnoreCase(speciality);
    }

    public List<User> findByFirstNameOrLastName(String name) {
        List<User> users = new ArrayList<>();
        userRepository.findAllByFirstnameContainingIgnoreCase(name).forEach(users::add);
        userRepository.findAllByLastnameContainingIgnoreCase(name).forEach(user -> {
            if (!users.contains(user)) {
                users.add(user);
            }
        });
        return users;
    }

    public void UpdateUser(User ur) {
        userRepository.save(ur);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateAllUsers(List<User> allUsers) {
        userRepository.saveAll(allUsers);
    }

    @Autowired
    TeamRepository teamRepository;

    public Team findUserTeam(User user) {
        AtomicReference<Team> team = null;
        teamRepository.findAll().forEach(t->{
            if(t.getUserList().contains(user))
                team.set(t);
        });
        return team.get();
    }

    public void deleteUser(long userid) {
        planningRepository.deleteById(userid);
        userRepository.deleteById(userid);
    }

}
