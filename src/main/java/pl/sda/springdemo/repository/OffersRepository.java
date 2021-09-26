package pl.sda.springdemo.repository;

import org.springframework.stereotype.Component;
import pl.sda.springdemo.model.Offer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OffersRepository {

    private List<Offer> offers = new ArrayList<>();

    public Offer save(Offer offer) {
        offer.setId(UUID.randomUUID().toString());
        offers.add(offer);

        return offer;
    }

}
