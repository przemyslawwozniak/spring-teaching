package pl.sda.springdemo.olo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //bez podania strategii bedzie uzywac jednej sekwencji dla wszystkich encji
    //@Column(name = "offer_id")
    private Long id;

    private String title;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "category_id")   //domyślnie subcategory_id
    private Subcategory subcategory;
    private String description;
    private String city;    //dzięki MapStruct i warstwom aplikacji łatwo mi to zmienić
    private BigDecimal price;
    private LocalDate publishedDate;
    private long views;
    //poniższe są duplikacją informacji z tabeli users; różne strategie modelowania
    @Transient
    private String email;
    @Transient
    private String phone;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})  //bez tego nie zapisze obiektow w relacji przy zapisie tego obiektu
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    void setPublishedDate() {
        if(this.publishedDate == null) {    //zebysmy mogli ustawic w tescie date publikacji
            this.publishedDate = LocalDate.now();
        }
    }

    @PostLoad
    void setContact() {
        if(this.user != null) {
            this.email = this.user.getEmail();
            this.phone = this.user.getPhone();
        }
    }

    public Offer update(Offer offerUpdate) {
        if(offerUpdate.getTitle() != null && ! this.title.equals(offerUpdate.getTitle())) {
            this.title = offerUpdate.getTitle();
        }
        if(offerUpdate.getPhone() != null && ! this.phone.equals(offerUpdate.getPhone())) {
            this.phone = offerUpdate.getPhone();
        }
        if(offerUpdate.getEmail() != null && ! this.email.equals(offerUpdate.getEmail())) {
            this.email = offerUpdate.getEmail();
        }
        if(offerUpdate.getCity() != null && ! this.city.equals(offerUpdate.getCity())) {
            this.city = offerUpdate.getCity();
        }
        if(offerUpdate.getDescription() != null && ! this.description.equals(offerUpdate.getDescription())) {
            this.description = offerUpdate.getDescription();
        }
        if(offerUpdate.getPrice() != null && ! this.price.equals(offerUpdate.getPrice())) {
            this.price = offerUpdate.getPrice();
        }
        //wykorzystuje @EqualsAndHashCode; mapowane z DTO z name na entity, dlatego mozemy podmienic
        if(offerUpdate.getSubcategory() != null && ! this.subcategory.equals(offerUpdate.getSubcategory())) {
            this.subcategory = offerUpdate.getSubcategory();
        }

        return this;
    }

}
