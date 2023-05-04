package edu.pfe.staffing.configuration;

import edu.pfe.staffing.model.Global;
import edu.pfe.staffing.model.Role;
import edu.pfe.staffing.repository.GlobalRepository;
import edu.pfe.staffing.service.RoleService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MainConfiguration implements InitializingBean {
    private static List<String> roleList = new ArrayList();
    @Autowired
    RoleService roleService;

    @Autowired
    GlobalRepository globalRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        roleList.add("COLLABORATEUR");
        roleList.add("MANAGER");
        roleList.add("CHEFPROJET");
        roleList.add("SUPERADMIN");

        for (String s : roleList) {
            if (roleService.FindRoleByName(s) == null) {
                Role role = new Role();
                role.setName(s);
                roleService.createRole(role);
            }
        }
    }

    public long getMaxMatcle() {
        Global global;
        if (globalRepository.findAll().size() == 0) {
            Global tmp = new Global();
            tmp.setMaxmatcle(1234);
            global = globalRepository.save(tmp);
        } else {
            global = globalRepository.findAll().get(0);
        }
        return global.getMaxmatcle();
    }

    public void inceremntMaxNudoss() {
        Global global = globalRepository.findAll().get(0);
        global.setMaxmatcle( global.getMaxmatcle()+1 );
        globalRepository.save(global);
    }
}
