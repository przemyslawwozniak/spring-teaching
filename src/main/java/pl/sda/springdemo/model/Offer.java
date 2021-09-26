package pl.sda.springdemo.model;

import lombok.Data;

@Data
public class Offer {

    private String id;

    private String title;
    private Subcategory subcategory;
    private String description;
    private String localization;
    private String email;
    private String phone;

}
