package pl.sda.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.springdemo.model.Chat;

@Repository
public interface ChatRepository  extends JpaRepository<Chat, Long> {
}
