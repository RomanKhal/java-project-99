package hexlet.code.app.Service;

import hexlet.code.app.Repository.UserRepository;
import hexlet.code.app.dto.User.UserCreateDTO;
import hexlet.code.app.dto.User.UserDTO;
import hexlet.code.app.dto.User.UserUpdateDTO;
import hexlet.code.app.exeption.ResourceNotFoundException;
import hexlet.code.app.exeption.UserAlreadyExistsException;
import hexlet.code.app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserMapper mapper;

    public List<UserDTO> findAll() {
        return userRepo.findAll().stream().map(mapper::map).toList();
    }

    public UserDTO findById(Long id) {
        var user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not Found: " + id));
        return mapper.map(user);
    }

    public UserDTO create(UserCreateDTO dto) {
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        var user = userRepo.save(mapper.map(dto));
        return mapper.map(user);
    }

    public UserDTO update(Long id, UserUpdateDTO dto) {
        var user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not Found: " + id));
        mapper.update(dto, user);
        userRepo.save(user);
        return mapper.map(user);
    }

    public void delete(Long id) {
        var user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not Found: " + id));
        userRepo.delete(user);
    }
}
