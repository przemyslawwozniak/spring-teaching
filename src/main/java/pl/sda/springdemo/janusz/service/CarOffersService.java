package pl.sda.springdemo.janusz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.sda.springdemo.janusz.dto.CarOfferSort;
import pl.sda.springdemo.janusz.dto.CarOfferSpecification;
import pl.sda.springdemo.janusz.dto.CarOfferTileDto;
import pl.sda.springdemo.janusz.mapper.CarOffersMapper;
import pl.sda.springdemo.janusz.model.CarOffer_;
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

    public List<CarOfferTileDto> getAllSorted(CarOfferSort carOfferSort) {
        return carOffersMapper.map(carOffersRepository.findAll(
                Sort.by(carOfferSort.getDirection(), carOfferSort.getFieldName())));
    }

    public List<CarOfferTileDto> getAllByDealerId(Long dealerId) {
        return carOffersMapper.map(carOffersRepository.findByDealer_DealerId(dealerId));
    }

    public List<CarOfferTileDto> getAllBySpecification(CarOfferSpecification specification) {
        return carOffersMapper.map(carOffersRepository.findWithSpecification(specification));
    }

}
