package me.minseok.ezfarmfarm.domain;

public enum FarmType {

    VINYL("비닐"),
    GLASS("유리");

    private final String name;

    FarmType(String name) {
        this.name = name;
    }

}
