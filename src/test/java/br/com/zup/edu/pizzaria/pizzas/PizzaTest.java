package br.com.zup.edu.pizzaria.pizzas;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PizzaTest {


    @Test
    void deveCalcularCorretamenteOPrecoDaPizza() {

        Ingrediente ingrediente1 = new Ingrediente("Queijo gouda", 80, BigDecimal.valueOf(9.50));
        Ingrediente ingrediente2 = new Ingrediente("Queijo gruyere", 50, BigDecimal.valueOf(12));

        Pizza pizza = new Pizza("Queijos Caros", Arrays.asList(ingrediente1, ingrediente2));

        BigDecimal valorFinal = new BigDecimal(20.0);
        valorFinal = valorFinal.add(ingrediente1.getPreco());
        valorFinal = valorFinal.add(ingrediente2.getPreco());

        assertEquals(valorFinal, pizza.getPreco());
    }
}