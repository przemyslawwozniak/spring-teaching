package pl.sda.springdemo.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.sda.springdemo.restcontroller.OffersRestApiClient;

@RequiredArgsConstructor
@ShellComponent
public class RestApiClientShell {

    private final OffersRestApiClient offersRestApiClient;

    @ShellMethod("Execute OffersRestApiClient#getOfferById for given id")
    public String getOfferForId(
            @ShellOption Long id
    ) {
        return offersRestApiClient.getOfferById(id).toString();
    }

    @ShellMethod("Execute OffersRestApiClient#getOfferById_v2 for given id")
    public String getOfferForId_v2(
            @ShellOption Long id
    ) {
        return offersRestApiClient.getOfferById_v2(id).toString();
    }

    @ShellMethod("Execute OffersRestApiClient#getOfferById_v3 for given id")
    public String getOfferForId_v3(
            @ShellOption Long id
    ) {
        return offersRestApiClient.getOfferById_v3(id).toString();
    }

}
