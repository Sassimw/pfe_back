package edu.pfe.staffing.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Planning {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    @OneToMany(mappedBy = "planning")
    private List<Assignment> assignments=new ArrayList<>();
}
