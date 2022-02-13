package pl.sda.springdemo.dto;

import lombok.Data;

@Data
public class UserDto {

    private String email;
    private String phone;
    private String password;
    private String roles;

}
