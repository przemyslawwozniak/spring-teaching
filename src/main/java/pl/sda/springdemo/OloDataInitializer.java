package pl.sda.springdemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.sda.springdemo.model.Offer;
import pl.sda.springdemo.model.Subcategory;
import pl.sda.springdemo.model.User;
import pl.sda.springdemo.repository.OffersRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(-1)  //rozwiazuje konflikt ze spring shell przez ktory to sie nie odpalalo
class OloDataInitializer implements CommandLineRunner {

    private final OffersRepository offersRepository;
    private final PasswordEncoder pswdEnc;

    @Override
    public void run(String... args) throws Exception {
        initializeOffers();
    }

    private void initializeOffers() {
        //initialize users
        var user1 = User.builder()
                .email("allauth@gmail.com")
                //.password("{bcrypt}$2a$12$ZpRF/.pifMyhlPf4/pdIW.MK6VaSwTZVgF.rVxaysESL1spRBso5C")	//== pswd; https://bcrypt-generator.com/; tak mozna podac jak sie tworzy za pomoca UserDetails.withUsername() itd.
                .password(pswdEnc.encode("pswd"))
                .authorities(Arrays.asList("offers:write", "offers:modify", "offers:remove"))
                .build();
        var user2 = User.builder()
                .email("someauth@gmail.com")
                //.password("{bcrypt}$2a$12$ZpRF/.pifMyhlPf4/pdIW.MK6VaSwTZVgF.rVxaysESL1spRBso5C")	//== pswd; https://bcrypt-generator.com/
                .password(pswdEnc.encode("pswd"))
                .authorities(Arrays.asList("offers:write", "offers:modify"))
                .build();

        //initialize offers
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

        var offer1 = Offer.builder()
                .title("Laptop Lenovo")
                .subcategory(computersSubcategory)
                .city("Warszawa")
                .price(BigDecimal.valueOf(4500))
                .publishedDate(LocalDate.of(2021, 5, 2))
                .user(user1)
                .build();
        var offer2 = Offer.builder()
                .title("laptop asus")
                .subcategory(computersSubcategory)
                .city("Lublin")
                .price(BigDecimal.valueOf(1200))
                .user(user1)
                .build();
        var offer3 = Offer.builder()
                .title("Książka \"Wojna w kosmosie\" Jacek Bartosiak")
                .subcategory(booksSubcategory)
                .city("Kraków")
                .price(BigDecimal.valueOf(39.99))
                .user(user1)
                .build();
        var offer4 = Offer.builder()
                .title("Klocki Lego Duplo 10505 duża farma")
                .subcategory(toysSubcategory)
                .city("Warszawa")
                .price(BigDecimal.valueOf(199.99))
                .user(user2)
                .build();
        var offer5 = Offer.builder()
                .title("laptop MacBook Pro 16 inch late 2019")
                .subcategory(computersSubcategory)
                .city("Warszawa")
                .price(BigDecimal.valueOf(9700))
                .publishedDate(LocalDate.of(2021, 5, 2))
                .user(user2)
                .build();
        var offer6 = Offer.builder()
                .title("laptop MacBook Air z procesorem M1")
                .subcategory(computersSubcategory)
                .city("Warszawa")
                .price(BigDecimal.valueOf(6900))
                .publishedDate(LocalDate.of(2021, 11, 7))
                .user(user2)
                .build();

        //saves not only offers but also subcategories and users as per cascading setup
        offersRepository.saveAll(Arrays.asList(offer1, offer2, offer3, offer4, offer5, offer6));

        log.info("OLO database initialized");
    }
}
