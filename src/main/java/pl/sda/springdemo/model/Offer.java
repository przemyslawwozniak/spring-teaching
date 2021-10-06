package pl.sda.springdemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "offers") //domyślnie 'offer'
@Getter
@Setter
public class Offer {

    @Id
    @GeneratedValue
    //@Column(name = "offer_id")
    private String id;

    private String title;
    @ManyToOne
    @JoinColumn(name = "category_id")   //domyślnie subcategory_id
    private Subcategory subcategory;
    private String description;
    private String city;    //dzięki MapStruct i warstwom aplikacji łatwo mi to zmienić
    private BigDecimal price;
    private LocalDate publishedDate;
    private int views;
    //poniższe są duplikacją informacji z tabeli users; różne strategie modelowania
    @Transient
    private String email;
    @Transient
    private String phone;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    void setPublishedDate() {
        this.publishedDate = LocalDate.now();
    }

    @PostLoad
    void setContact() {
        this.email = this.user.getEmail();
        this.phone = this.user.getPhone();
    }

}
