package pl.sda.springdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService inMemoryUserDetailsService() {
        UserDetails simba = User.withDefaultPasswordEncoder()
                .username("simba")
                .password("pswd")
                .roles("USER")
                .build();

        UserDetails mufasa = User.withDefaultPasswordEncoder()
                .username("mufasa")
                .password("pswd")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(simba, mufasa);
    }

}
