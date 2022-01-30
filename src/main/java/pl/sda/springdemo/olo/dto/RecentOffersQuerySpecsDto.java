package pl.sda.springdemo.olo.dto;

import lombok.Data;

@Data
public class RecentOffersQuerySpecsDto {

    private int page;
    private int offersPerPage;
    private String subcategoryName;

}
