package pl.sda.springdemo.janusz.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)  //konstruktor bezargumentowy wymagany przez Hibernate
@Builder
@EqualsAndHashCode
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    private final String name;
    private final MainCategory mainCategory;

    public enum MainCategory {

        MOTO("Automotive"),
        REAL_ESTATE("Real Estate"),
        JOB("Job"),
        HOME_GARDEN("Home & Garden"),
        ELECTRONICS("Electronics"),
        FASHION("Fashion"),
        ANIMALS("Animals"),
        BABY("Baby"),
        SPORTS_HOBBY("Sports & Hobby"),
        MUSIC_EDU("Music & Education");

        private final String displayName;

        MainCategory(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return this.displayName;
        }

    }

}
