package pl.sda.springdemo.janusz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.springdemo.janusz.model.CarOffer;
import pl.sda.springdemo.janusz.repository.CarOffersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarOffersService {

    private final CarOffersRepository carOffersRepository;

    public List<CarOffer> getAll() {
        return carOffersRepository.findAll();
    }

}
