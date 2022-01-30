package pl.sda.springdemo.janusz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class CarOffer {

    @Id
    @Access(AccessType.PROPERTY)
    @GeneratedValue
    private Long offerId;

    private String title;
    private LocalDateTime publishedDate;
    private String desc;
    private BigDecimal price;
    private CarModel.CarBrand carBrand; //duplikujemy z CarModel poniewaz jest to nasz podstawowy parametr wyszukiwania
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;
    private short productionYear;
    private int carMileageInKm;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    private short engineCapacity;
    private short enginePower;
    @Enumerated(EnumType.STRING)
    private GearboxType gearboxType;
    @Enumerated(EnumType.STRING)
    private CarCondition carCondition;
    @Enumerated(EnumType.STRING)
    private CarBodyType carBodyType;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "dealer_id")
    private Dealer dealer;
}

/*
class Offer {
	offerId PK

	title
	publishedDate
	desc
	price
	carBrand ENUM
	carModel ENUM
	yearOfProduction
	carMileageInKm
	fuelType ENUM
	engineCapacity
	enginePower
	carBodyType	ENUM
	gearBox ENUM
	carCondition ENUM

	dillerId FK	(rel 1)
	Diller	//odwzorowanie po stronie Java, wynika z rel (1)
}
 */
