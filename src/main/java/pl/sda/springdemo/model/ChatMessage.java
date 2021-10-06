package pl.sda.springdemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class ChatMessage {

    @Id
    @GeneratedValue
    private String id;

    private String text;
    private LocalDateTime sentDateTime;
    private String senderId;
    private String receiverId;

    @ManyToOne
    private Chat chat;

}
