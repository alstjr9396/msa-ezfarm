package me.minseok.ezfarmfarm.vo;

import lombok.Builder;
import lombok.Data;
import me.minseok.ezfarmfarm.common.validator.EnumValue;
import me.minseok.ezfarmfarm.domain.FarmType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Data
public class RequestFarm {

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;

    @NotEmpty(message = "Name cannot be empty")
    private String userId;

    @NotEmpty(message = "Address cannot be empty")
    private String address;

    @NotEmpty(message = "FarmType cannot be empty")
    @EnumValue(enumClass = FarmType.class)
    private String farmType;
}
