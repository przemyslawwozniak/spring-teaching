package pl.sda.springdemo.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.sda.springdemo.model.Offer;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "offers.restapiclient")
public class OffersRestApiClient {

    private final RestTemplate restTemplate;

    @Setter
    private String url;

    public Offer getOfferById(Long id) {
        return restTemplate.getForObject(url,   //wola sam siebie, ale mozna odpalic kolejna instancje aplikacji
                                        Offer.class,
                                        id);
    }

    public Offer getOfferById_v2(Long id) {
        var urlParams = new HashMap<String, String>();
        urlParams.put("id", id.toString());

        return restTemplate.getForObject(url,
                Offer.class,
                urlParams);
    }

    public Offer getOfferById_v3(Long id) {
        var urlParams = new HashMap<String, String>();
        urlParams.put("id", id.toString());

        var url = UriComponentsBuilder
                .fromHttpUrl(this.url)
                .build(urlParams);

        return restTemplate.getForObject(url, Offer.class);
    }

}
