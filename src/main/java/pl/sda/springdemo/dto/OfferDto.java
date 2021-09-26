package pl.sda.springdemo.dto;

import lombok.Data;

@Data
public class OfferDto {

    private String title;
    private String subcategoryId;
    private String description;
    private String localization;
    private String email;
    private String phone;

}
