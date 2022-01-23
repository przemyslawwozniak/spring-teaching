package pl.sda.springdemo.janusz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
public class Bid {

    @Id
    @GeneratedValue
    private Long bidId;

    private BigDecimal price;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "offer_id")
    private CarOffer carOffer;

}

/*
class Bid {
	bidId PK

	price

	offerId FK (rel 3)	//aby pobrac oferte uzywamy HTTP GET janusz.pl/offers/{offerId}
	Offer	//odwzorowanie po stronie Java, wynika z rel (3)
	w jaki sposob pobrac wartosc offerId bez JOIN-a (offerId jest w koncu kolumna tabeli BIDS)

	buyerId FK	(rel 2)
}
 */