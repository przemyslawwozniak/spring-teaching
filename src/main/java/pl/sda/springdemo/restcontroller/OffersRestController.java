package pl.sda.springdemo.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.springdemo.dto.RecentOffersQuerySpecsDto;
import pl.sda.springdemo.model.Offer;
import pl.sda.springdemo.service.OffersService;

import java.util.List;

@RestController
@RequestMapping("/offers")  //produces = "application/json"
@RequiredArgsConstructor
public class OffersRestController {

    private final OffersService offersService;

    @GetMapping("/recent")
    public List<Offer> recentOffers() {
        return offersService.getRecentOffers();
    }

    @GetMapping("/recent/{subcategoryName}")    //która opcja jest bardziej naturalna - ta?
    public List<Offer> recentOffersForSubcategory(@PathVariable String subcategoryName) {   //jeśli nazwa pathVar != nazwy arg metody, podajemy pathVar tak: @PathVariable("pathName
        return offersService.getRecentOffers(subcategoryName);
    }

    @GetMapping("/recent-rp")   //czy ta?
    public List<Offer> recentOffers(@RequestParam String subcategory) {
        return offersService.getRecentOffers(subcategory);
    }

    @GetMapping("/recent-rb")
    public List<Offer> recentOffers(@RequestBody RecentOffersQuerySpecsDto querySpecs) {
        return offersService.getRecentOffers(querySpecs);
    }

    @GetMapping("/recent/{id}")
    public ResponseEntity<Offer> getOffer(@PathVariable Long id) {
        var offerOptional = offersService.getOffer(id);
        return offerOptional
                .map(offer -> new ResponseEntity<>(offer, HttpStatus.OK))   //true
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND)); //false
    }


}
