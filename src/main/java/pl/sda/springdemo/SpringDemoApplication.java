package pl.sda.springdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.sda.springdemo.model.Offer;
import pl.sda.springdemo.model.Subcategory;
import pl.sda.springdemo.repository.OffersRepository;
import pl.sda.springdemo.repository.SubcategoriesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringDemoApplication implements CommandLineRunner {

	private final OffersRepository offersRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initializeDatabase();
	}

	private void initializeDatabase() {
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
}
