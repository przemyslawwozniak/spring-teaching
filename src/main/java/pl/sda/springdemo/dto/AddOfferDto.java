package pl.sda.springdemo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddOfferDto {

    private String title;
    private String subcategoryName;
    private String description;
    private String city;
    private String email;
    private String phone;
    private BigDecimal price;

}
