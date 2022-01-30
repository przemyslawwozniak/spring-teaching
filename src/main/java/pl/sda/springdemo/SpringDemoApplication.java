package pl.sda.springdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.sda.springdemo.janusz.model.Address;
import pl.sda.springdemo.janusz.model.CarBodyType;
import pl.sda.springdemo.janusz.model.CarCondition;
import pl.sda.springdemo.janusz.model.CarModel;
import pl.sda.springdemo.janusz.model.CarOffer;
import pl.sda.springdemo.janusz.model.ContactData;
import pl.sda.springdemo.janusz.model.Dealer;
import pl.sda.springdemo.janusz.model.FuelType;
import pl.sda.springdemo.janusz.model.GearboxType;
import pl.sda.springdemo.olo.model.Offer;
import pl.sda.springdemo.olo.model.Subcategory;
import pl.sda.springdemo.janusz.repository.CarOffersRepository;
import pl.sda.springdemo.olo.repository.OffersRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringDemoApplication implements CommandLineRunner {

	private final OffersRepository offersRepository;
	private final CarOffersRepository carOffersRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initOloData();
		initJanuszData();
	}

	private void initOloData() {
		var computersSubcategory = Subcategory.builder()
				.name("Computers")
				.mainCategory(Subcategory.MainCategory.ELECTRONICS)
				.build();
		var booksSubcategory = Subcategory.builder()
				.name("Books")
				.mainCategory(Subcategory.MainCategory.SPORTS_HOBBY)
				.build();
		var toysSubcategory = Subcategory.builder()
				.name("Toys")
				.mainCategory(Subcategory.MainCategory.BABY)
				.build();

		//mamy kaskadowosc, kategorie zapisza sie wraz z zapisem ofert
		//subcategoriesRepository.saveAll(Arrays.asList(computersSubcategory, booksSubcategory, toysSubcategory));

		//city, title, price, published
		var offer1 = Offer.builder()
				.title("Laptop Lenovo")
				.subcategory(computersSubcategory)
				.city("Warszawa")
				.price(BigDecimal.valueOf(4500))
				.publishedDate(LocalDate.of(2021, 5, 2))
				.build();
		var offer2 = Offer.builder()
				.title("laptop asus")
				.subcategory(computersSubcategory)
				.city("Lublin")
				.price(BigDecimal.valueOf(1200))
				.build();
		var offer3 = Offer.builder()
				.title("Książka \"Wojna w kosmosie\" Jacek Bartosiak")
				.subcategory(booksSubcategory)
				.city("Kraków")
				.price(BigDecimal.valueOf(39.99))
				.build();
		var offer4 = Offer.builder()
				.title("Klocki Lego Duplo 10505 duża farma")
				.subcategory(toysSubcategory)
				.city("Warszawa")
				.price(BigDecimal.valueOf(199.99))
				.build();
		var offer5 = Offer.builder()
				.title("laptop MacBook Pro 16 inch late 2019")
				.subcategory(computersSubcategory)
				.city("Warszawa")
				.price(BigDecimal.valueOf(9700))
				.publishedDate(LocalDate.of(2021, 5, 2))
				.build();
		var offer6 = Offer.builder()
				.title("laptop MacBook Air z procesorem M1")
				.subcategory(computersSubcategory)
				.city("Warszawa")
				.price(BigDecimal.valueOf(6900))
				.publishedDate(LocalDate.of(2021, 11, 7))
				.build();

		offersRepository.saveAll(Arrays.asList(offer1, offer2, offer3, offer4, offer5, offer6));
	}

	void initJanuszData() {
		var dealer1 = createDealer("dealer_1", "Warszawa");
		var dealer2 = createDealer("dealer_2", "Lublin");
		var dealer3 = createDealer("dealer_3", "Katowice");

		var offer1_d1 = createCarOffer(dealer1, "MB W211", 35000L, CarBodyType.SEDAN, LocalDateTime.of(2022, 1, 23, 10, 0), CarModel.CarBrand.MERCEDES, "E Klasse", (short)2008);
		var offer2_d1 = createCarOffer(dealer1, "Audi A4 B9", 60000L, CarBodyType.COMBI, LocalDateTime.of(2021, 12, 31, 23, 59), CarModel.CarBrand.AUDI, "A4", (short)2008);

		var offer3_d2 = createCarOffer(dealer2, "Mazda CX-9", 100000L, CarBodyType.SUV, LocalDateTime.of(2021, 10, 3, 10, 0), CarModel.CarBrand.MAZDA, "CX-9", (short)2008);
		var offer4_d2 = createCarOffer(dealer2, "Kia Sportage", 59000L, CarBodyType.SUV, LocalDateTime.of(2021, 5, 2, 10, 0), CarModel.CarBrand.KIA, "Sportage", (short)2008);

		var offer1_d3 = createCarOffer(dealer3, "Porsche Cayenne 2017", 190000L, CarBodyType.SUV, LocalDateTime.of(2022, 1, 23, 10, 0), CarModel.CarBrand.PORSCHE, "Cayenne", (short)2017);
		var offer2_d3 = createCarOffer(dealer3, "Porsche Cayenne 2014", 120000L, CarBodyType.SUV, LocalDateTime.of(2021, 1, 23, 10, 0), CarModel.CarBrand.PORSCHE, "Cayenne", (short)2014);
		var offer3_d3 = createCarOffer(dealer3, "Porsche Cayenne 2010", 80000L, CarBodyType.SUV, LocalDateTime.of(2021, 1, 23, 10, 0), CarModel.CarBrand.PORSCHE, "Cayenne", (short)2010);
		var offer4_d3 = createCarOffer(dealer3, "Porsche Boxter 2010", 65000L, CarBodyType.CABRIO, LocalDateTime.of(2021, 1, 23, 10, 0), CarModel.CarBrand.PORSCHE, "Boxter", (short)2010);
		var offer5_d3 = createCarOffer(dealer3, "Porsche Boxter 2004", 35000L, CarBodyType.CABRIO, LocalDateTime.of(2021, 1, 23, 10, 0), CarModel.CarBrand.PORSCHE, "Boxter", (short)2004);

		carOffersRepository.saveAll(Arrays.asList(offer1_d1, offer2_d1, offer3_d2, offer4_d2, offer1_d3, offer2_d3, offer3_d3, offer4_d3, offer5_d3));
	}

	private Dealer createDealer(String dealerName, String city) {
		var dealer = new Dealer();
		dealer.setName(dealerName);
		dealer.setAddress(new Address("Sezamkowa","25A",1,"01-001",city));
		dealer.setDesc("Used car dealer");
		dealer.setContactData(new ContactData("500-000-000","januszex@gmail.com"));
		return dealer;
	}

	private CarOffer createCarOffer(Dealer dealer, String title, Long price, CarBodyType bodyType, LocalDateTime publishedDate, CarModel.CarBrand carBrand, String carModelName, short prodY) {
		var offer = new CarOffer();
		offer.setTitle(title);
		offer.setDesc("Stan idealny");
		offer.setCarCondition(CarCondition.SECOND_HAND);
		offer.setCarBodyType(bodyType);
		var brand = carBrand;
		offer.setCarBrand(brand);
		offer.setCarModel(new CarModel(carModelName, brand));
		offer.setEngineCapacity((short)2000);
		offer.setDesc("Polecam, bardzo dobre auto");
		offer.setEnginePower((short)186);
		offer.setCarMileageInKm(170000);
		offer.setFuelType(FuelType.PETROL);
		offer.setGearboxType(GearboxType.AUTOMATIC);
		offer.setPrice(BigDecimal.valueOf(price));
		offer.setProductionYear(prodY);
		offer.setPublishedDate(publishedDate);
		offer.setDealer(dealer);
		return offer;
	}
}
