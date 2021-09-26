package pl.sda.springdemo.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Subcategory {

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
