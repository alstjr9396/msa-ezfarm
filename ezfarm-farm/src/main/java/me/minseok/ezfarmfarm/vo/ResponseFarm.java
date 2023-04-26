package me.minseok.ezfarmfarm.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseFarm {

    private String name;
    private String userId;
    private String address;
    private String farmType;
}
