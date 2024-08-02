package hexlet.code.app.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
