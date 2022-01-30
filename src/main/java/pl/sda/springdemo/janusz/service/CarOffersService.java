package pl.sda.springdemo.janusz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.springdemo.janusz.dto.CarOfferTileDto;
import pl.sda.springdemo.janusz.mapper.CarOffersMapper;
import pl.sda.springdemo.janusz.repository.CarOffersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarOffersService {

    private final CarOffersRepository carOffersRepository;
    private final CarOffersMapper carOffersMapper;

    public List<CarOfferTileDto> getAll() {
        return carOffersMapper.map(carOffersRepository.findAll());
    }

}
