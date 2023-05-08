package edu.pfe.staffing.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public User() {
    }

    public User(long id, String matcle, String email, String firstname, String lastname, String password, boolean enabled, Team team, Team ownedteam, List<Role> roles) {
        this.id = id;
        this.matcle = matcle;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.enabled = enabled;
        this.team = team;
        Ownedteam = ownedteam;
        this.roles = roles;
    }

    private String matcle;
    private String email;
    private String firstname;
    private String lastname;
    // @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean enabled;

    private String speciality;

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    @OneToOne
    @JsonIgnore
    private Planning planning;

    public String getMatcle() {
        return matcle;
    }

    public void setMatcle(String matcle) {
        this.matcle = matcle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isenabled() {
        return enabled;
    }

    public void setenabled(boolean enabled) {
        enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @ManyToOne
    private Team team;
    @OneToOne
    @JsonIgnore
    private Team Ownedteam;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();

    @JsonIgnore
    public List<Role> getRoleList() {
        return roles;
    }

    public void setRoleList(List<Role> roleList) {
        this.roles = roleList;
    }

    @JsonIgnore
    public Team getOwnedteam() {
        return Ownedteam;
    }

    public void setOwnedteam(Team ownedteam) {
        Ownedteam = ownedteam;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", matcle='" + matcle + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", team=" + team +
                ", Ownedteam=" + Ownedteam +
                ", roles=" + roles +
                '}';
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    private int getuserperproject(String project_name) {
        for (Assignment assignment : this.planning.getAssignments()) {
            if (assignment.getProject().equals(project_name)) {
                return assignment.getUser_score();
            }
        }
        return -1;
    }

    private List<Project> getalluserproject() {
        Set<Project> projects = new HashSet<>();
        for (Assignment assignment : this.planning.getAssignments()) {
            projects.add(assignment.getProject());

        }
        List<Project> allprojects = new ArrayList<>();
        allprojects.addAll(projects);
        return allprojects;
    }

    public int getnumberofuserprojects() {
        return getalluserproject().size();
    }

    public double getuserglobalscoreperproject() {
        int totalprojectnumber = getnumberofuserprojects();
        int somme = 0;
        for (Assignment assignment : this.planning.getAssignments()) {
            somme += assignment.getUser_score();
        }
        if (totalprojectnumber > 0)
            return somme / totalprojectnumber;
        else return 0;
    }

    private int nbrOfProjectsOfUser;
    private double globalUserScore;

    public int getNbrOfProjectsOfUser() {
        return nbrOfProjectsOfUser;
    }

    public void setNbrOfProjectsOfUser(int nbrOfProjectsOfUser) {
        this.nbrOfProjectsOfUser = nbrOfProjectsOfUser;
    }

    public double getGlobalUserScore() {
        return globalUserScore;
    }

    public void setGlobalUserScore(double globalUserScore) {
        this.globalUserScore = globalUserScore;
    }
}
