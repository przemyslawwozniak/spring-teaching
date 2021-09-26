package pl.sda.springdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.springdemo.dto.OfferDto;
import pl.sda.springdemo.mapper.OffersMapper;
import pl.sda.springdemo.repository.OffersRepository;
import pl.sda.springdemo.service.OffersService;

import javax.validation.Valid;

@Controller
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OffersController {

    private final OffersService offersService;
    /*
        Pomocny IDEA podpowiada, że:
        Lombok does not copy the annotation 'org.springframework.beans.factory.annotation.Qualifier' into the constructor
        No więc ja wpisuję w Google "qualifier lombok" i trafiam tu: https://stackoverflow.com/a/50287955/3673353
     */
    @Qualifier("mapStructOffersMapperImpl")
    private final OffersMapper offersMapper;
    private final OffersRepository offersRepository;

    @GetMapping("/form")    //== @RequestMapping(method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("mainCategoriesToSubcategoriesMap", offersService.getMainCategoriesDisplayNamesToSubcategoriesMap());
        model.addAttribute("offerDto", new OfferDto());   //bindowane przez formularz

        return "/offers/form";
    }

    @PostMapping    //w tym przypadku path to po prostu /offers
    public String addOffer(@Valid OfferDto offerDto, Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("mainCategoriesToSubcategoriesMap", offersService.getMainCategoriesDisplayNamesToSubcategoriesMap());    //po co to? a dlaczego nie trzeba ustawiać 'offerDto'?
            return "/offers/form";
        }

        var offer = offersMapper.mapFromDtoToDomain(offerDto);
        offer = offersRepository.save(offer);

        return "redirect:/offers/success";
    }

}
