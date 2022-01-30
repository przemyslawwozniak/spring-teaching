package pl.sda.springdemo.janusz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.springdemo.janusz.dto.DealerTileDto;
import pl.sda.springdemo.janusz.mapper.DealersMapper;
import pl.sda.springdemo.janusz.repository.DealersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealersService {

    private final DealersRepository dealersRepository;
    private final DealersMapper dealersMapper;

    public List<DealerTileDto> getAll() {
        return dealersMapper.map(dealersRepository.findAll());
    }

}
