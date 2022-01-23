package pl.sda.springdemo.janusz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
class Address {

    private String street;
    private String streetNo;
    private int homeNo;
    private String zipCode;
    private String city;

}
