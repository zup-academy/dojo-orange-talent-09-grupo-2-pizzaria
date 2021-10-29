package br.com.zup.edu.pizzaria.pizzas.cadastropizza;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import br.com.zup.edu.pizzaria.ingredientes.IngredienteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NovaPizzaControllerTest {

    private static List<Ingrediente> ingredientes;

    @Autowired
    private static  IngredienteRepository ingredienteRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setUpBeforeClass() {
        Ingrediente ingrediente1 = new Ingrediente("Calabresa", 60, BigDecimal.valueOf(2.5));
        Ingrediente ingrediente2 = new Ingrediente("Queijo parmesão", 120, BigDecimal.valueOf(3.5));
        Ingrediente ingrediente3 = new Ingrediente("Molho de tomate", 90, BigDecimal.valueOf(1));
        ingredientes = Arrays.asList(ingrediente1, ingrediente2, ingrediente3);
        ingredienteRepository.saveAll(ingredientes);
    }

    @Test
    void deveCadastrarUmaNovaPizzaERetornarStatus201() {

    }
}