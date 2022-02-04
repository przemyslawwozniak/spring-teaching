package pl.sda.springdemo.mapper;

import org.mapstruct.Mapper;
import pl.sda.springdemo.dto.UserDto;
import pl.sda.springdemo.model.User;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    User fromDto(UserDto userDto);

}
