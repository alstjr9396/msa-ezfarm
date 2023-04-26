package me.minseok.ezfarmfarm.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Table(name = "farms")
@Entity
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_id")
    private Long id;

    @Column(nullable = false)
    private String userId;

    private String name;

    private String address;

    @Enumerated(value = EnumType.STRING)
    private FarmType farmType;
}
