package pl.sda.springdemo.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)  //konstruktor bezargumentowy wymagany przez Hibernate
public class Subcategory {

    @Id
    @GeneratedValue
    private final String id;

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
