package pl.sda.springdemo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfferTileDto {

    private Long id;
    private String title;
    private BigDecimal price;

}
