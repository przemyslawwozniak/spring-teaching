package pl.sda.springdemo.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.springdemo.dto.UserDto;
import pl.sda.springdemo.security.UserRegistrationService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersRestController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void registerUser(@RequestBody UserDto userDto) {
        userRegistrationService.register(userDto);
    }

}