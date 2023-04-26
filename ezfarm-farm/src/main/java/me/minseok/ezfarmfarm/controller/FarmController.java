package me.minseok.ezfarmfarm.controller;

import lombok.RequiredArgsConstructor;
import me.minseok.ezfarmfarm.service.FarmService;
import me.minseok.ezfarmfarm.vo.RequestFarm;
import me.minseok.ezfarmfarm.vo.ResponseFarm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class FarmController {

    private final FarmService farmService;

    @PostMapping("/farms")
    public ResponseEntity<ResponseFarm> createUser(@RequestBody @Valid RequestFarm requestFarm) {
        ResponseFarm responseFarm = farmService.createFarm(requestFarm);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseFarm);
    }
}
