package edu.pfe.staffing.service;

import edu.pfe.staffing.model.Team;
import edu.pfe.staffing.model.User;
import edu.pfe.staffing.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;


    public void createTeam(Team team){
        teamRepository.save(team);
    }

    public void deleteAll() {
        teamRepository.deleteAll();
    }

    public void deleteteamid(long teamid) {
        teamRepository.deleteById(teamid);
    }

    public List<Team> Viewallteams() {
        return teamRepository.findAll();

    }

    public Team Viewteamsid(long teamid) {
        if (teamRepository.findById(teamid).isPresent()){
            return teamRepository.findById(teamid).get();
        }
        else {
            return null;
        }


    }

    public void Updateteam(Team tm) {
        teamRepository.save(tm);
    }
    public Team findById(Long id) {
        return teamRepository.findById(id).get();
    }
}
