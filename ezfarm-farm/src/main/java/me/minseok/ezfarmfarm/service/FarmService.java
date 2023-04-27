package me.minseok.ezfarmfarm.service;

import lombok.RequiredArgsConstructor;
import me.minseok.ezfarmfarm.common.faignClient.UserServiceClient;
import me.minseok.ezfarmfarm.domain.Farm;
import me.minseok.ezfarmfarm.domain.FarmType;
import me.minseok.ezfarmfarm.vo.RequestFarm;
import me.minseok.ezfarmfarm.vo.ResponseFarm;
import me.minseok.ezfarmfarm.repository.FarmRepository;
import me.minseok.ezfarmfarm.vo.user.ResponseUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class FarmService {

    private final ModelMapper modelMapper;
    private final FarmRepository farmRepository;
    private final UserServiceClient userServiceClient;

    public ResponseFarm createFarm(String token, RequestFarm requestFarm) {
        ResponseUser responseUser = userServiceClient.getUser(token, requestFarm.getUserId());

        Farm farm = Farm.builder()
                .name(requestFarm.getName())
                .userId(responseUser.getUserId())
                .address(requestFarm.getAddress())
                .farmType(FarmType.valueOf(requestFarm.getFarmType()))
                .build();

        farmRepository.save(farm);

        return modelMapper.map(farm, ResponseFarm.class);
    }
}
