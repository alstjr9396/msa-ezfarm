package me.minseok.ezfarmfarm.service;

import me.minseok.ezfarmfarm.common.faignClient.UserServiceClient;
import me.minseok.ezfarmfarm.domain.Farm;
import me.minseok.ezfarmfarm.domain.FarmType;
import me.minseok.ezfarmfarm.repository.FarmRepository;
import me.minseok.ezfarmfarm.vo.RequestFarm;
import me.minseok.ezfarmfarm.vo.ResponseFarm;
import me.minseok.ezfarmfarm.vo.user.ResponseUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
@DisplayName("농장 서비스 테스트")
class FarmServiceTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserServiceClient userServiceClient;

    private FarmService farmService;

    private Farm farm;

    private UUID uuid = UUID.randomUUID();

    @BeforeEach
    void before() {
        farmService = new FarmService(modelMapper, farmRepository, userServiceClient);
        farm = Farm.builder()
                .name("테스트 농장")
                .userId(uuid.toString())
                .address("테스트 주소")
                .farmType(FarmType.VINYL)
                .build();
    }

    @DisplayName("농장을 생성한다.")
    @Test
    void createUser() {
        RequestFarm requestFarm = RequestFarm.builder()
                .name("테스트 농장")
                .userId(uuid.toString())
                .address("테스트 주소")
                .farmType(FarmType.VINYL.name())
                .build();

        ResponseFarm responseFarm = ResponseFarm.builder()
                .name("테스트 농장")
                .userId(uuid.toString())
                .address("테스트 주소")
                .farmType(FarmType.VINYL.name())
                .build();

        ResponseUser responseUser = ResponseUser.builder()
                .userId(uuid.toString())
                .build();

        given(userServiceClient.getUser(anyString(), anyString())).willReturn(responseUser);
        given(farmRepository.save(any())).willReturn(farm);
        given(modelMapper.map(any(), any())).willReturn(responseFarm);

        ResponseFarm returnValue = farmService.createFarm("token", requestFarm);

        Assertions.assertAll(
                () -> assertThat(returnValue).isNotNull(),
                () -> assertThat(returnValue.getName()).isEqualTo(requestFarm.getName()),
                () -> assertThat(returnValue.getAddress()).isEqualTo(requestFarm.getAddress()),
                () -> assertThat(returnValue.getFarmType()).isEqualTo(requestFarm.getFarmType())
        );
    }
}