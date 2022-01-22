package pl.sda.springdemo;

import org.assertj.core.api.Assertions;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import pl.sda.springdemo.model.Chat;
import pl.sda.springdemo.model.Offer;
import pl.sda.springdemo.model.Subcategory;
import pl.sda.springdemo.model.User;
import pl.sda.springdemo.repository.ChatRepository;
import pl.sda.springdemo.repository.OffersRepository;
import pl.sda.springdemo.repository.UsersRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@TestPropertySource("classpath:test.properties")
//@SpringBootTest
@DataJpaTest
public class EntitiesRelationsTest {

    @Autowired
    private OffersRepository offersRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ChatRepository chatRepository;

    @BeforeAll
    static void setupWebServer() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082")
                .start();
    }

    @Test
    void user_adds_offers() {
        //given
        var user = User.builder()
                .email("code.addict@gmail.com")
                .phone("111222333") //brak walidacji! i co teraz?
                .password("mysecret")
                .build();

        var subcategory = Subcategory.builder()
                .name("Mercedes-Benz")
                .mainCategory(Subcategory.MainCategory.MOTO)
                .build();

        var offer = Offer.builder()
                .title("Mercedes W211 sedan igła - Niemiec płakał jak sprzedawał!")
                .description("Jeśli szukają Państwo niezawodnego, prestiżowego auta z możliwością założenia instalacji LPG...")
                .city("Warszawa")
                .price(BigDecimal.valueOf(30000))
                .subcategory(subcategory)   //relacja ale na poziomie obiektu JVM
                .user(user)
                .build();
        //when
        offer = offersRepository.save(offer);

        //then
        Assertions.assertThat(offer.getId()).isNotNull();
        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(subcategory.getId()).isNotNull();

        var persistedOfferOptional = offersRepository.findById(offer.getId());
        Assertions.assertThat(persistedOfferOptional.isPresent()).isTrue();

        var persistedOffer = persistedOfferOptional.get();
        Assertions.assertThat(persistedOffer.getTitle()).isEqualTo(offer.getTitle());
        //przedyskutowac linijke ponizej
        //Assertions.assertThat(persistedOffer.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @Sql({"/offer_postload_test_data.sql"})
    void postload_is_called_for_offer_entity() {
        //given

        //when
        var user = usersRepository.getById(100L);
        var offer = offersRepository.getById(150L);

        //then
        Assertions.assertThat(offer.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void user_observes_offers() {
        //given
        var offer1 = Offer.builder()
                .title("bmw e36")
                .city("Białystok")
                .build();
        var offer2 = Offer.builder()
                .title("bmw 5 2014")
                .city("Gdańsk")
                .build();
        var offer3 = Offer.builder()
                .title("mazda 6 2018")
                .city("Warszawa")
                .build();

        offersRepository.saveAll(Arrays.asList(offer1, offer2, offer3));

        var user1 = buildUserWithOffers("bmw4ever@gmail.com", Arrays.asList(offer1, offer2));
        var user2 = buildUserWithOffers("whatever@gmail.com", Arrays.asList(offer1, offer2, offer3));
        usersRepository.saveAll(Arrays.asList(user1, user2));   //nadaje encjom ID (persistent object state)

        //when
        var bmwFan = usersRepository.getById(user1.getId());    //nie mozemy zakladac np. 1L bo baza zyje przez caly czas testow i jesli w tej tabeli znajduja sie juz rekordy, ID bedzie zgodne z sekwencja
        var allCarFan = usersRepository.getById(user2.getId());

        //then
        Assertions.assertThat(bmwFan.getObservedOffers()).isNotEmpty();
        Assertions.assertThat(bmwFan.getObservedOffers()).hasSize(2);

        Assertions.assertThat(allCarFan.getObservedOffers()).isNotEmpty();
        Assertions.assertThat(allCarFan.getObservedOffers()).hasSize(3);
    }

    //--- --- --- CWICZENIE 1 - rozwiazanie
    @Test
    void user_opens_chat_for_offer() {
        //given
        var user1 = buildUserWithOffers("chatUser1@gmail.com", new ArrayList<>());
        var user2 = buildUserWithOffers("chatUser2@gmail.com", new ArrayList<>());

        var chat = new Chat();
        var chat2 = new Chat();

        user1.setTalks(List.of(chat));
        user2.setTalks(List.of(chat, chat2));

        //chatRepository.saveAll(Arrays.asList(chat, chat2));
        usersRepository.saveAll(Arrays.asList(user1, user2));

        //when
        var user1Persisted = usersRepository.getById(user1.getId());
        var user2Persisted = usersRepository.getById(user2.getId());

        //then
        Assertions.assertThat(user1Persisted.getTalks()).isNotEmpty();
        Assertions.assertThat(user1Persisted.getTalks()).hasSize(1);

        Assertions.assertThat(user2Persisted.getTalks()).isNotEmpty();
        Assertions.assertThat(user2Persisted.getTalks()).hasSize(2);
    }

    private User buildUserWithOffers(String userEmail, List<Offer> userOffers) {
        return User.builder()
                .email(userEmail)
                .observedOffers(userOffers)
                .build();
    }

}
