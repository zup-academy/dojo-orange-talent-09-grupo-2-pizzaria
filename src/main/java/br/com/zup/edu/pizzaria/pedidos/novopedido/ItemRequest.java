package br.com.zup.edu.pizzaria.pedidos.novopedido;

import br.com.zup.edu.pizzaria.pedidos.Item;
import br.com.zup.edu.pizzaria.pedidos.Pedido;
import br.com.zup.edu.pizzaria.pedidos.TipoDeBorda;
import br.com.zup.edu.pizzaria.pizzas.Pizza;
import br.com.zup.edu.pizzaria.pizzas.PizzaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class ItemRequest {

    private Long pizzaId;

    private TipoDeBorda borda;

    public ItemRequest(Long pizzaId, TipoDeBorda borda) {
        this.pizzaId = pizzaId;
        this.borda = borda;
    }

    public Item paraItem(Pedido pedido, PizzaRepository repository) {

        Pizza possivelPizza = repository.findById(pizzaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pizza não encontrada"));

        return new Item(pedido, borda, possivelPizza);
    }

    public Long getPizzaId() {
        return pizzaId;
    }

    public TipoDeBorda getBorda() {
        return borda;
    }
}
