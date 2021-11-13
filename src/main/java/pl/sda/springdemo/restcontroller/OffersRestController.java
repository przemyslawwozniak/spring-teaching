package pl.sda.springdemo.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.springdemo.dto.AddOfferDto;
import pl.sda.springdemo.exception.DbResourceNotFoundException;
import pl.sda.springdemo.response.ErrorResponse;
import pl.sda.springdemo.response.OfferTileResponse;
import pl.sda.springdemo.dto.RecentOffersQuerySpecsDto;
import pl.sda.springdemo.mapper.OffersMapper;
import pl.sda.springdemo.model.Offer;
import pl.sda.springdemo.service.OffersService;

import java.util.List;

@RestController
@RequestMapping("/offers")  //produces = "application/json"
@RequiredArgsConstructor
class OffersRestController {

    private final OffersService offersService;
    private final OffersMapper offersMapper;

    @GetMapping("/recent")
    List<OfferTileResponse> recentOffers() {
        var foundOffers = offersService.getRecentOffers();
        return offersMapper.mapFromDomainToTileDto(foundOffers);
    }

    @GetMapping("/recent/{subcategoryName}")    //uwaga na konflikt z path postaci /recent/{id}: Ambiguous handler methods mapped for '/offers/recent/5' (path nie rozroznia String od Long)
    List<OfferTileResponse> recentOffersForSubcategory(@PathVariable String subcategoryName) {   //jeśli nazwa pathVar != nazwy arg metody, podajemy pathVar tak: @PathVariable("pathName
        var foundOffers = offersService.getRecentOffers(subcategoryName);
        return offersMapper.mapFromDomainToTileDto(foundOffers);
    }

    @GetMapping("/recent-rp")
    List<OfferTileResponse> recentOffers(@RequestParam String subcategory) {
        var foundOffers = offersService.getRecentOffers(subcategory);
        return offersMapper.mapFromDomainToTileDto(foundOffers);
    }

    @GetMapping("/recent-rb")
    List<OfferTileResponse> recentOffers(@RequestBody RecentOffersQuerySpecsDto querySpecs) {
        var foundOffers = offersService.getRecentOffers(querySpecs);
        return offersMapper.mapFromDomainToTileDto(foundOffers);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Offer getOffer(@PathVariable Long id) {
/*
        var offerOptional = offersService.getOffer(id);
        return offerOptional
                .map(offer -> new ResponseEntity<>(offer, HttpStatus.OK))   //true
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND)); //false
 */
        return offersService.getOffer(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Offer addOffer(@RequestBody AddOfferDto offerDto) {
        var mappedOffer = offersMapper.mapFromAddOfferDtoToDomain(offerDto);
        return offersService.addOffer(mappedOffer);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Offer replaceOffer(@RequestBody AddOfferDto offerDto, @PathVariable Long id) {
        offersService.checkOfferExist(id);
        var mappedOffer = offersMapper.mapFromAddOfferDtoToDomain(offerDto);
        mappedOffer.setId(id);
        return offersService.addOffer(mappedOffer); //repository.save() dla wskazanego id 'podmieni' wpis w bazie dla tego id
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Offer updateOffer(@RequestBody AddOfferDto offerDto, @PathVariable Long id) {
        var offerUpdate = offersMapper.mapFromAddOfferDtoToDomain(offerDto);
        return offersService.updateOffer(offerUpdate, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOffer(@PathVariable Long id) {
        offersService.deleteOffer(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)   //404 oznacza brak zasobu
    @ExceptionHandler(DbResourceNotFoundException.class)
    ErrorResponse handleDbResourceNotFoundException(final DbResourceNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

}
