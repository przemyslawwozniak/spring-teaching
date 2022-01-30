package pl.sda.springdemo.janusz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.springdemo.janusz.dto.CarOfferSort;
import pl.sda.springdemo.janusz.dto.CarOfferSpecification;
import pl.sda.springdemo.janusz.dto.CarOfferTileDto;
import pl.sda.springdemo.janusz.service.CarOffersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/caroffers")
class CarOffersRestController {

    private final CarOffersService carOffersService;

    @GetMapping //domyslnie podpina pod GET glowny mapping tego kontrolera, tj. /caroffers
    List<CarOfferTileDto> getAll() {
        return carOffersService.getAll();
    }

    @GetMapping("/dealer/{dealerId}")
    List<CarOfferTileDto> getAllByDealerId(@PathVariable Long dealerId) {
        return carOffersService.getAllByDealerId(dealerId);
    }

    @GetMapping("/search")
    List<CarOfferTileDto> getAllBySpecification(@RequestBody CarOfferSpecification specification) {
        return carOffersService.getAllBySpecification(specification);
    }
    //todo: 400 - bad request
    @GetMapping("/sorted")
    List<CarOfferTileDto> getAll(@RequestBody CarOfferSort carOfferSort) {
        return carOffersService.getAllSorted(carOfferSort);
    }

}
