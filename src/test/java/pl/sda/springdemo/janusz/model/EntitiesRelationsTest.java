package pl.sda.springdemo.janusz.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.sda.springdemo.janusz.repository.BidsRepository;
import pl.sda.springdemo.janusz.repository.CarOffersRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DataJpaTest
public class EntitiesRelationsTest {

    @Autowired
    private CarOffersRepository carOffersRepository;

    @Autowired
    private BidsRepository bidsRepository;

    @Test
    void dealer_adds_offer() {
        //given
        var dealer = createDealer();
        var offer = createCarOffer(dealer);

        carOffersRepository.save(offer);
        //when
        var savedOffer = carOffersRepository.findById(offer.getOfferId());

        //then
        Assertions.assertThat(savedOffer).isNotEmpty();
        Assertions.assertThat(savedOffer.get().getTitle()).isEqualTo(offer.getTitle());
        Assertions.assertThat(dealer.getDealerId()).isNotNull();
    }

    @Test
    void buyer_bids_offer() {
        //given
        var dealer = createDealer();
        var offer = createCarOffer(dealer);
        var buyer = createBuyer();
        var bid = createBid(buyer, offer);

        bidsRepository.save(bid);
        //when
        var savedBid = bidsRepository.findById(bid.getBidId());

        //then
        Assertions.assertThat(savedBid).isNotEmpty();
        Assertions.assertThat(savedBid.get().getPrice()).isEqualTo(bid.getPrice());
        Assertions.assertThat(dealer.getDealerId()).isNotNull();
        Assertions.assertThat(buyer.getBuyerId()).isNotNull();
        Assertions.assertThat(offer.getOfferId()).isNotNull();

        Assertions.assertThat(offer.getDealer().getDealerId()).isEqualTo(dealer.getDealerId());
    }

    private Dealer createDealer() {
        var dealer = new Dealer();
        dealer.setAddress(new Address("Sezamkowa","25A",1,"01-001","Warszawa"));
        dealer.setDesc("Used car dealer ");
        dealer.setContactData(new ContactData("500-000-000","januszex@gmail.com"));
        return dealer;
    }

    private CarOffer createCarOffer(Dealer dealer) {
        var offer = new CarOffer();
        offer.setTitle("MB W211");
        offer.setDesc("Stan idealny");
        offer.setCarCondition(CarCondition.SECOND_HAND);
        offer.setCarBodyType(CarBodyType.SEDAN);
        var brand = CarModel.CarBrand.MERCEDES;
        offer.setCarBrand(brand);
        offer.setCarModel(new CarModel("E Klasse, W211", brand));
        offer.setEngineCapacity((short)2000);
        offer.setDesc("Polecam, bardzo dobre auto");
        offer.setEnginePower((short)186);
        offer.setCarMileageInKm(170000);
        offer.setFuelType(FuelType.PETROL);
        offer.setGearboxType(GearboxType.AUTOMATIC);
        offer.setPrice(BigDecimal.valueOf(35000L));
        offer.setProductionYear((short)2008);
        offer.setPublishedDate(LocalDateTime.now());
        offer.setDealer(dealer);
        return offer;
    }

    private Bid createBid(Buyer buyer, CarOffer carOffer) {
        var bid = new Bid();
        bid.setPrice(BigDecimal.valueOf(30000L));
        bid.setBuyer(buyer);
        bid.setCarOffer(carOffer);
        return bid;
    }

    private Buyer createBuyer() {
        var buyer = new Buyer();
        buyer.setContactData(new ContactData("700-700-700","janbuy@gmail.com"));
        buyer.setName("Jan Buyer");
        return buyer;
    }

}
