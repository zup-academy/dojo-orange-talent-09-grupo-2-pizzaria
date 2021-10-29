package br.com.zup.edu.pizzaria.pizzas.cadastropizza;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import br.com.zup.edu.pizzaria.ingredientes.IngredienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NovaPizzaControllerTest {

    private List<Ingrediente> ingredientes;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUpBeforeClass() {
        Ingrediente ingrediente1 = new Ingrediente("Calabresa", 60, BigDecimal.valueOf(2.5));
        Ingrediente ingrediente2 = new Ingrediente("Queijo parmesão", 120, BigDecimal.valueOf(3.5));
        Ingrediente ingrediente3 = new Ingrediente("Molho de tomate", 90, BigDecimal.valueOf(1));
        ingredientes = Arrays.asList(ingrediente1,ingrediente2,ingrediente3);
        ingredienteRepository.saveAll(ingredientes);
    }

    @Test
    void deveCadastrarUmaNovaPizzaERetornarStatus201() throws Exception {
        NovaPizzaRequest body = new NovaPizzaRequest("Calabresa",List.of(0L,2L));
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
