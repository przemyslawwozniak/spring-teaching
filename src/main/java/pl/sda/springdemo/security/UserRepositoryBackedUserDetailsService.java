package pl.sda.springdemo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.sda.springdemo.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class UserRepositoryBackedUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    //sygnatura tej metody precyzyjnie opisuje kontrakt, ale jest też dokumentacja
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = usersRepository.findByEmail(username);
        if(user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
