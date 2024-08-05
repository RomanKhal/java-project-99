package hexlet.code.app.Repository;

import hexlet.code.app.Model.User;
import hexlet.code.app.dto.User.UserDTO;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDTO> findByEmail(String email);
}
