package me.minseok.ezfarmfarm.service;

import lombok.AllArgsConstructor;
import me.minseok.ezfarmfarm.domain.Farm;
import me.minseok.ezfarmfarm.domain.FarmType;
import me.minseok.ezfarmfarm.vo.RequestFarm;
import me.minseok.ezfarmfarm.vo.ResponseFarm;
import me.minseok.ezfarmfarm.repository.FarmRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class FarmService {

    private final ModelMapper modelMapper;
    private final FarmRepository farmRepository;

    public ResponseFarm createFarm(RequestFarm requestFarm) {
        // TODO validate userId exists
        Farm farm = Farm.builder()
                .name(requestFarm.getName())
                .userId(requestFarm.getUserId())
                .address(requestFarm.getAddress())
                .farmType(FarmType.valueOf(requestFarm.getFarmType()))
                .build();

        farmRepository.save(farm);

        return modelMapper.map(farm, ResponseFarm.class);
    }
}
