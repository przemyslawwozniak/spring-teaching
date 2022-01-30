package pl.sda.springdemo.janusz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.springdemo.janusz.dto.DealerTileDto;
import pl.sda.springdemo.janusz.service.DealersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dealers")
public class DealersRestController {

    private final DealersService dealersService;

    @GetMapping
    List<DealerTileDto> getAll() {
        return dealersService.getAll();
    }

}
