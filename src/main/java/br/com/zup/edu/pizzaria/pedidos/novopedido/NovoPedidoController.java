package br.com.zup.edu.pizzaria.pedidos.novopedido;

import br.com.zup.edu.pizzaria.pedidos.Pedido;
import br.com.zup.edu.pizzaria.pizzas.PizzaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/pedidos")
public class NovoPedidoController {


    private final PedidoRepository pedidoRepository;
    private final PizzaRepository pizzaRepository;

    public NovoPedidoController(PedidoRepository pedidoRepository,
                                PizzaRepository pizzaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pizzaRepository = pizzaRepository;
    }


    @PostMapping
    public ResponseEntity<?> novoPedido(@RequestBody @Valid NovoPedidoRequest request) {

        try {
            Pedido pedido = request.paraPedido(pizzaRepository);
            Pedido salvo = pedidoRepository.save(pedido);

            URI location = UriComponentsBuilder.fromUriString("/api/pedidos/{id}")
                    .build(salvo.getId());

            return ResponseEntity.created(location).build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
