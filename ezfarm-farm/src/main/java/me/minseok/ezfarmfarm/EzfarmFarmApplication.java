package me.minseok.ezfarmfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EzfarmFarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(EzfarmFarmApplication.class, args);
    }

}
