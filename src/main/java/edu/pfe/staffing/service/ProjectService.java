package edu.pfe.staffing.service;

import edu.pfe.staffing.model.Planning;
import edu.pfe.staffing.model.Project;
import edu.pfe.staffing.model.Team;
import edu.pfe.staffing.model.User;
import edu.pfe.staffing.repository.AssignmentRepository;
import edu.pfe.staffing.repository.PlanningRepository;
import edu.pfe.staffing.repository.ProjectRepository;
import edu.pfe.staffing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    UserRepository userRepository;

    public void addProject(Project project) {
        projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProject(long id) {
        projectRepository.deleteById(id);
    }

    public void updateProject(Project project) {
        projectRepository.save(project);
    }

    public Project getById(long id) {
        return projectRepository.getOne(id);
    }

    public List<User> listAllUsersAssignedToProject(Project project) {
        List<Planning> plannings = new ArrayList<>();
        assignmentRepository.findAllByProject(project).forEach(assignment -> plannings.add(assignment.getPlanning()));

        List<User> users = new ArrayList<>();
        plannings.forEach(planning -> {
            User user = userRepository.findOneByPlanning(planning);
            if (!users.contains(user))
                users.add(user);
        });

        return users;
    }

    public Project Viewprojectsid(long projectid) {
        if (projectRepository.findById(projectid).isPresent()){
            return projectRepository.findById(projectid).get();
        }
        else {
            return null;
        }


    }
}
