package pl.sda.springdemo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String phone;
    private String password;

    @Getter(AccessLevel.NONE)   //Lombok: do not generate getter for this field (getAuthorities ma okreslony kontrakt nizej)
    @ElementCollection(fetch = FetchType.EAGER)  //w przeciwnym wypadku mapping exception -> https://thorben-janssen.com/hibernate-tips-elementcollection/
    private List<String> authorities = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "observes",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private List<Offer> observedOffers = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "talks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private List<Chat> talks = new ArrayList<>();

    //ta metoda to czesc kontraktu -> https://www.baeldung.com/spring-security-granted-authority-vs-role#userdetailsservice
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(var authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return grantedAuthorities;
    }

    //Security zakłada domyślny schemat z polem 'username', wskazujemy, co jest naszym username - tu email
    @Override
    public String getUsername() {
        return email;
    }
    //poniższe ustawione na 'true' z domyślnego 'false'
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
