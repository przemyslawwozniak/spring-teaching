package pl.sda.springdemo.olo.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfferTileResponse {

    private Long id;
    private String title;
    private BigDecimal price;

}
