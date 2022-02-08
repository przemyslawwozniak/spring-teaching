package pl.sda.springdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import pl.sda.springdemo.security.AccessRuleAuthorizationManager;
import pl.sda.springdemo.security.UserRepositoryBackedUserDetailsService;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig {

    private final UserRepositoryBackedUserDetailsService userRepositoryBackedUserDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AccessRuleAuthorizationManager access) throws Exception {
        http
                .authenticationProvider(authProvider())
                .userDetailsService(userRepositoryBackedUserDetailsService)
                .authorizeHttpRequests((authz) -> authz.anyRequest().access(access))
        		.httpBasic(Customizer.withDefaults())
                //.cors(Customizer.withDefaults())    //potrzebne przy SPA, przykladowy bean ponizej
                /*.csrf((csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) //obiecane lepsze rozwiazanie niz wylaczenie csrf
                );*/
                .csrf().disable();

        return http.build();
    }

    @Bean
    PasswordEncoder pswdEnc() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userRepositoryBackedUserDetailsService);
        authProvider.setPasswordEncoder(pswdEnc());
        return authProvider;
    }

    /*  //przykladowa konfiguracja dla CORS dla SPA
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("http://127.0.0.1:8000");	//frontend app
        config.addAllowedCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
*/
}
