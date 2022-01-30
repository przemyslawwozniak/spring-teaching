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
public class Dealer {

    @Id
    @GeneratedValue
    private Long dealerId;
    
    private String name;
    private String desc;

    @Embedded
    private Address address;

    @Embedded
    private ContactData contactData;

}

/*
class Diller {
	dillerId PK
	name
	desc

	street		---> class Address (@Embeddable)
	streetNo
	zipcode
	city

	phone		---> class ContactData (@Embeddable)
	email
}
 */
