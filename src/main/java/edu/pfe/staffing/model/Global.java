package edu.pfe.staffing.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Global {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long maxmatcle;

    public long getMaxmatcle() {
        return maxmatcle;
    }

    public void setMaxmatcle(long maxmatcle) {
        this.maxmatcle = maxmatcle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
