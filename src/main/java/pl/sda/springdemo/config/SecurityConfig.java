package pl.sda.springdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sda.springdemo.security.UserRepositoryBackedUserDetailsService;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepositoryBackedUserDetailsService userRepositoryBackedUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //.authenticationProvider(authProvider());
                .userDetailsService(userRepositoryBackedUserDetailsService)
                .passwordEncoder(pswdEnc());
    }

    @Bean
    PasswordEncoder pswdEnc() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/offers/**").hasRole("USER")
                    .antMatchers(HttpMethod.PUT, "/offers/**").hasRole("USER")
                    .antMatchers(HttpMethod.PATCH, "/offers/**").hasRole("USER")
                    .antMatchers(HttpMethod.DELETE, "/offers/**").hasRole("USER")
                    .antMatchers("/**").permitAll().and()    //wszystkie pozostałe - wpuszczaj. dzieki regex nie warto szczegolowo wymieniac np /users itd.
                .httpBasic().and()  //uzywamy autoryzacji poprzez HTTP Basic
                .csrf().disable();  //CSRF wystepuje przy operacji POST; rozwiazanie tymczasowe, później pokażę lepsze rozwiązanie
    }

    @Bean
    DaoAuthenticationProvider authProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userRepositoryBackedUserDetailsService);
        authProvider.setPasswordEncoder(pswdEnc());
        return authProvider;
    }
}
