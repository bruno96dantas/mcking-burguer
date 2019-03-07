package model;

import lombok.Getter;

import java.math.BigDecimal;

public enum Ingrediente {

    HAMBURGER(BigDecimal.valueOf(3.0), "HAMBURGER"),
    ALFACE(BigDecimal.valueOf(0.4), "ALFACE"),
    BACON(BigDecimal.valueOf(2.0), "BACON"),
    OVO(BigDecimal.valueOf(0.8), "OVO"),
    QUEIJO(BigDecimal.valueOf(1.5), "QUEIJO");

    @Getter
    private BigDecimal price;
    @Getter
    private String name;

    Ingrediente(BigDecimal price, String name) {
        this.price = price;
        this.name = name;
    }

    public static Ingrediente create(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        for (Ingrediente v : values()) {
            if (value.equals(v.getName())) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return name;
    }
}
