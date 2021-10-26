package pl.sda.springdemo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class OfferDto {

    @NotNull    //jak myślicie, czy to ma sens, skoro na dole mam ten @Size? (doc)
    @Size(min = 5, max = 80, message = "Title must be between 5 to 80 characters long")
    private String title;

    @NotNull(message = "Subcategory must be given")
    private Long subcategoryId;

    @NotBlank(message = "Please provide description")
    private String description;

    private String localization;

    @Email(message = "E-mail address format wrong")
    private String email;

    @Pattern(regexp = "([0-9]{3}\\s[0-9]{3}\\s[0-9]{3})|([0-9]{9})", message = "Allowed phone number formats: xxx xxx xxx or xxxxxxxxx")
    private String phone;

}
