package pl.sda.springdemo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.springdemo.dto.UserDto;
import pl.sda.springdemo.mapper.UsersMapper;
import pl.sda.springdemo.model.User;
import pl.sda.springdemo.repository.UsersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    public User register(UserDto userDto) {
        var user = usersMapper.fromDto(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        //moglibysmy tutaj np. ustawiac role, ale nie mamy tego pola (mamy hardcoded zestaw rol dla kazdego usera)
        user.setRoles("ROLE_USER");
        return usersRepository.save(user);
    }

    public List<User> getUsers() {
        return usersRepository.findAll();
    }

    public User updateUserRoles(String email, String roles) {
        var persistedUser = usersRepository.findByEmail(email);
        persistedUser.setRoles(roles);
        return usersRepository.save(persistedUser);
    }

}
