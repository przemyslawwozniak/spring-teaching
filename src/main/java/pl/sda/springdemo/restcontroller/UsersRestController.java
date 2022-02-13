package pl.sda.springdemo.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.springdemo.dto.UserDto;
import pl.sda.springdemo.model.User;
import pl.sda.springdemo.security.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersRestController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void registerUser(@RequestBody UserDto userDto) {
        userService.register(userDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<User> getUsers() {
        return userService.getUsers();
    }

    @PatchMapping()
    //User updateUserRoles(@PathVariable Long id, @RequestParam String roles) { //?roles=
    User updateUserRoles(@RequestBody UserDto userDto) {
        return userService.updateUserRoles(userDto.getEmail(), userDto.getRoles());
    }
}
