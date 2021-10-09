package pl.sda.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.springdemo.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

}
