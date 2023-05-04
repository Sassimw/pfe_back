package edu.pfe.staffing.repository;

import edu.pfe.staffing.model.Planning;
import edu.pfe.staffing.model.Role;
import edu.pfe.staffing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findOneByMatcleAndPassword(String matcle,String password);
    User findOneByMatcle(String matcle);

    List<User> findAllBySpecialityContainingIgnoreCase(String speciality);
    List<User> findAllByFirstnameContainingIgnoreCase(String firstname);
    List<User> findAllByLastnameContainingIgnoreCase(String lastname);
    User findOneByPlanning(Planning planning);
}
