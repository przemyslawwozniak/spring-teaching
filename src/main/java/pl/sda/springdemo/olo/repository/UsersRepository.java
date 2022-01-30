package pl.sda.springdemo.olo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.springdemo.janusz.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

}
