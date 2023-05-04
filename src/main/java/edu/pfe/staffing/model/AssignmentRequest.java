package edu.pfe.staffing.model;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class AssignmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User requester;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private User resource;

    @ManyToOne
    private Project targetProject;

    private boolean accepted;

    private LocalDate creationDate;

    private LocalDate requestedDate;

    // ************************************


    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getResource() {
        return resource;
    }

    public void setResource(User resource) {
        this.resource = resource;
    }

    public Project getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(Project targetProject) {
        this.targetProject = targetProject;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
