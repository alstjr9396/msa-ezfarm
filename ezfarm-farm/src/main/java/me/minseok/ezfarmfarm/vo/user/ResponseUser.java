package me.minseok.ezfarmfarm.vo.user;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
}
