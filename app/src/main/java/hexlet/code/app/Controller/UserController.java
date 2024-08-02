package hexlet.code.app.Controller;

import hexlet.code.app.Repository.UserRepository;
import hexlet.code.app.dto.User.UserDTO;
import hexlet.code.app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@ControllerAdvice
public class UserController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserMapper mapper;

    @GetMapping(path = "")
    List<UserDTO> index() {
        return userRepo.findAll().stream().map(mapper::map).toList();
    }
}
