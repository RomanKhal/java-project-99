package hexlet.code.app.mapper;

import hexlet.code.app.Model.User;
import hexlet.code.app.dto.User.UserCreateDTO;
import hexlet.code.app.dto.User.UserDTO;
import hexlet.code.app.dto.User.UserUpdateDTO;
import org.mapstruct.*;

@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {
    public abstract User map(UserCreateDTO dto);
    public abstract void update(UserUpdateDTO dto, @MappingTarget User model);
    public abstract UserDTO map(User model);
}
