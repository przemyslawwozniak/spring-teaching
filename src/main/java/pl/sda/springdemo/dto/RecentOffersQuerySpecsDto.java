package pl.sda.springdemo.dto;

import lombok.Data;

@Data
public class RecentOffersQuerySpecsDto {

    private int page;
    private int offersPerPage;
    private String subcategoryName;

}
