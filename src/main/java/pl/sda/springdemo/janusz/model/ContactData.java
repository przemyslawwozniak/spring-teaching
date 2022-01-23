package pl.sda.springdemo.janusz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
class ContactData {

    private String phone;
    private String email;

}
