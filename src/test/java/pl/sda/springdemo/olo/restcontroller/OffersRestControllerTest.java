package pl.sda.springdemo.olo.restcontroller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.springdemo.olo.model.Offer;
import pl.sda.springdemo.olo.model.Subcategory;
import pl.sda.springdemo.olo.service.OffersService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(OffersRestController.class)
//@Import(MapStructOffersMapperImpl.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//uzywamy tych dwoch nizej zamiast dwoch wyzej (zakomentowanych) - wyjasnienie ponizej
@SpringBootTest
@AutoConfigureMockMvc
public class OffersRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OffersService offersServiceMock;    //wprowadzamy interface aby nie musieć wstrzykiwać zależności które pojawiają się w impl (dyskusja na temat "automatycznego" tworzenia interfejsów przez programistów)

    private List<Offer> testOffers;
//@WebMvcTest konfiguruje tylko warstwę web, przez co musimy ręcznie konfigurować zależności (wygodniej jest to robić via @TestConfiguration)
//nasz kontroler korzysta (oprocz OffersService) z OffersMapper a ten z SubcategoriesRepository (Spring Data) co znacząco utrudnia konfigurację jako generowaną - a ten moduł jest wyłączony przy selektywnym @WebMvcTest
/*
    @MockBean
    private OffersRepository offersRepositoryMock;  //wymagane przez SpringDemoApplication
    @Autowired
    private OffersMapper offersMapper;  //wymagane przez OffersRestController - ale chcemy wstrzyknąć właściwą impl, bo używamy tego na testowanej ścieżce
    @Autowired
    private SubcategoriesRepository subcategoriesRepository;
*/

    @BeforeAll
    void setup() {
        testOffers = getTestOffers();
    }

    @Test
    public void returns_recent_offers() throws Exception {
        //given
        when(offersServiceMock.getRecentOffers()).thenReturn(testOffers);

        //when
        mvc.perform(get("/offers/recent"))

        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    public void returns_recent_offers_for_subcategory() throws Exception {
        //given
        when(offersServiceMock.getRecentOffers("Books")).thenReturn(testOffers.subList(2, 3));

        //when
        mvc.perform(get("/offers/recent/Books"))

        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Książka \"Wojna w kosmosie\" Jacek Bartosiak")));
    }

    private List<Offer> getTestOffers() {
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

        return Arrays.asList(offer1, offer2, offer3, offer4, offer5, offer6);
    }

}
