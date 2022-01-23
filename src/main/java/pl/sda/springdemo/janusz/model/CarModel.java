package pl.sda.springdemo.janusz.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
class CarModel {

    @Id
    @GeneratedValue
    private Long carModelId;

    private final String name;

    @Enumerated(EnumType.STRING)
    private final CarBrand brand;   //kontroluje na poziomie struktury danych dopasowanie brand - model

    enum CarBrand {     //rzadko sie zmienia
        MERCEDES("Mercedes"),
        AUDI("Audi"),
        VOLKSWAGEN("Volkswagen"),
        BMW("BMW"),
        PORSCHE("Porsche"),
        BENTLEY("Bentley"),
        FIAT("Fiat"),
        CITROEN("Citroen"),
        PEUGOT("Peugot");

        private final String brandName;

        CarBrand(final String displayName) {
            this.brandName = displayName;
        }

        String getBrandName() {
            return brandName;
        }
    }

}
