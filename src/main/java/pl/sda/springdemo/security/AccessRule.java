package pl.sda.springdemo.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "rules")
@Getter
@Setter
@NoArgsConstructor
public class AccessRule {

    @Id
    private String urlPattern;

    private String httpMethod;

    private String authority;

    public AccessRule(HttpMethod httpMethod, String urlPattern, String authority) {
        this.httpMethod = httpMethod.name();
        this.urlPattern = urlPattern;
        this.authority = authority;
    }

}
