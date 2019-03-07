package model;

import lombok.Getter;

import static java.util.Arrays.asList;

public enum Cardapio {

    XBACON(Lanche.builder().ingredientes(asList(Ingrediente.BACON, Ingrediente.HAMBURGER, Ingrediente.QUEIJO)).name("X-BACON").build()),
    XBURGER(Lanche.builder().ingredientes(asList(Ingrediente.HAMBURGER, Ingrediente.QUEIJO)).name("X-BURGER").build()),
    XEGG(Lanche.builder().ingredientes(asList(Ingrediente.OVO, Ingrediente.HAMBURGER, Ingrediente.QUEIJO)).name("X-EGG").build()),
    XEGGBACON(Lanche.builder().ingredientes(asList(Ingrediente.OVO, Ingrediente.BACON, Ingrediente.HAMBURGER, Ingrediente.QUEIJO)).name("X-EGG-BACON").build());

    @Getter
    private Lanche lanche;

    Cardapio(Lanche lanche) {
        this.lanche = lanche;
    }
}
