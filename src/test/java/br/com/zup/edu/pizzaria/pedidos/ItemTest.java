package br.com.zup.edu.pizzaria.pedidos;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import br.com.zup.edu.pizzaria.pizzas.Pizza;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


class ItemTest {

    @Test
    void deveCalcularCorretamenteOSubTotalDoItem() {

        Ingrediente ingrediente1 = new Ingrediente("Queijo gouda", 80, BigDecimal.valueOf(9.50));
        Ingrediente ingrediente2 = new Ingrediente("Queijo gruyere", 50, BigDecimal.valueOf(12));

        Pizza pizza = new Pizza("Queijos Caros", Arrays.asList(ingrediente1, ingrediente2));
        Endereco endereco = new Endereco("Rua A","10","Sem Complemento","0580-030");
        Pedido pedido = new Pedido(endereco);
        BigDecimal valorFinal = TipoDeBorda.RECHEADA_CATUPIRY.getPreco();
        valorFinal = valorFinal.add(pizza.getPreco());
        Item item = new Item(pedido,TipoDeBorda.RECHEADA_CATUPIRY,pizza);
        assertEquals(valorFinal, item.getSubtotal());
    }

}