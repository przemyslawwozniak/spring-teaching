package pl.sda.springdemo.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.sda.springdemo.model.Offer;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class OffersRestApiClient {

    private final RestTemplate restTemplate;

    public Offer getOfferById(Long id) {
        return restTemplate.getForObject("http://localhost:8080/offers/{id}",   //wola sam siebie, ale mozna odpalic kolejna instancje aplikacji
                                        Offer.class,
                                        id);
    }

    public Offer getOfferById_v2(Long id) {
        var urlParams = new HashMap<String, String>();
        urlParams.put("id", id.toString());

        return restTemplate.getForObject("http://localhost:8080/offers/{id}",
                Offer.class,
                urlParams);
    }

    public Offer getOfferById_v3(Long id) {
        var urlParams = new HashMap<String, String>();
        urlParams.put("id", id.toString());

        var url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/offers/{id}")
                .build(urlParams);

        return restTemplate.getForObject(url, Offer.class);
    }

}
