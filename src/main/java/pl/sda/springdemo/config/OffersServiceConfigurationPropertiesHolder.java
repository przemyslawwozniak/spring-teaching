package pl.sda.springdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "offers")
@Data
public class OffersServiceConfigurationPropertiesHolder {

    private Page page;

    @Data
    public static class Page {
        private int size;
    }

}
