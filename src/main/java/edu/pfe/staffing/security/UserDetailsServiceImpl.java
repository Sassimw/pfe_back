package edu.pfe.staffing.security;

import edu.pfe.staffing.model.User;
import edu.pfe.staffing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository ;

    @Override
    public UserDetails loadUserByUsername(String matcle) throws UsernameNotFoundException {
        User User = userRepository.findOneByMatcle(matcle);
        if (User == null) {
            throw new UsernameNotFoundException(matcle);
        }

        List<GrantedAuthority> grantedAuthorities =
                User.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(User.getMatcle(), User.getPassword(), grantedAuthorities);
    }
}
