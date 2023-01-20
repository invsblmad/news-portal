package kg.megalab.newsportal.repository;

import kg.megalab.newsportal.dto.response.data.UserView;
import kg.megalab.newsportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    UserView findViewById(Integer id);

}
