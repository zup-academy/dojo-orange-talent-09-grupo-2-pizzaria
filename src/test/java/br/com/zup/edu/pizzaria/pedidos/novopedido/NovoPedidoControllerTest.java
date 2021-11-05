package br.com.zup.edu.pizzaria.pedidos.novopedido;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import br.com.zup.edu.pizzaria.ingredientes.IngredienteRepository;
import br.com.zup.edu.pizzaria.pedidos.Endereco;
import br.com.zup.edu.pizzaria.pizzas.Pizza;
import br.com.zup.edu.pizzaria.pizzas.PizzaRepository;
import br.com.zup.edu.pizzaria.pizzas.cadastropizza.NovaPizzaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NovoPedidoControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private IngredienteRepository ingredienteRepository;
    @Autowired
    private PizzaRepository pizzaRepository;
    private Pizza pizza;

    @BeforeEach
    void setUpBeforeClass() {
        List<Ingrediente> ingredientes;
        Ingrediente ingrediente1 = new Ingrediente("Calabresa", 60, BigDecimal.valueOf(2.5));
        Ingrediente ingrediente2 = new Ingrediente("Queijo parmesão", 120, BigDecimal.valueOf(3.5));
        Ingrediente ingrediente3 = new Ingrediente("Molho de tomate", 90, BigDecimal.valueOf(1));
        ingredientes = Arrays.asList(ingrediente1,ingrediente2,ingrediente3);
        ingredienteRepository.saveAll(ingredientes);
        pizza = new Pizza("Calabresa com Queijo",ingredientes);
        pizzaRepository.save(pizza);
    }

    /*
        Verificar a classe ItemRequest, esta recebendo um pedido no .toPedido, pode ser
        que esteja errado
     */
    @Test
    void deveCadastrarUmNovoPedidoERetornarStatus201() throws Exception {
        Endereco endereco = new Endereco("Rua A","10","Sem Complemento","0580-030");
        ItemRequest item1 = new ItemRequest();
        item1.paraItem();
        NovoPedidoRequest body = new NovoPedidoRequest(endereco,);
        MockHttpServletRequestBuilder request = post("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("/api/pizzas/{id}"));
    }

}