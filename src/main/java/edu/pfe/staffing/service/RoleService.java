package edu.pfe.staffing.service;

import edu.pfe.staffing.model.Role;
import edu.pfe.staffing.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository  roleRepository;
    public void createRole(Role role){roleRepository.save(role);}
    public Role FindRoleByName(String name){return roleRepository.findOneByName(name);}
}
