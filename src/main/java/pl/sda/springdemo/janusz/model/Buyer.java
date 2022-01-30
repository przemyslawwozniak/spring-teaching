package pl.sda.springdemo.janusz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Buyer {

    @Id
    @GeneratedValue
    private Long buyerId;

    private String name;

    @Embedded
    private ContactData contactData;

}

/*
class Buyer {
	buyerId PK
	name

	phone
	email

	List<Bid> bids;	//wynika z relacji (2), odwzorowanie po stronie Java
}
 */
