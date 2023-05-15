package edu.pfe.staffing.model;

import java.util.ArrayList;
import java.util.List;

public class AssigmentUser {

    private User user;

    private List<Assignment> assignments=new ArrayList<>();

    public AssigmentUser(User user, List<Assignment> assignments) {
        this.user = user;
        this.assignments = assignments;
    }

    public User getUser() {
        return user;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }
}
