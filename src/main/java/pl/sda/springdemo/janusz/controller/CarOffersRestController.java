package pl.sda.springdemo.janusz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.springdemo.janusz.dto.CarOfferTile;
import pl.sda.springdemo.janusz.model.CarOffer;
import pl.sda.springdemo.janusz.service.CarOffersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/caroffers")
class CarOffersRestController {

    private final CarOffersService carOffersService;

    @GetMapping //domyslnie podpina pod GET glowny mapping tego kontrolera, tj. /caroffers
    List<CarOfferTile> getAll() {
        return carOffersService.getAll();
    }

}
