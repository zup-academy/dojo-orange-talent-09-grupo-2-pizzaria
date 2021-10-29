package br.com.zup.edu.pizzaria.ingredientes.cadastrodeingredientes;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import br.com.zup.edu.pizzaria.ingredientes.IngredienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NovoIngredienteControllerTest {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    void deveCadastrarNovoIngrediente() throws Exception {

        NovoIngredienteRequest body = new NovoIngredienteRequest("Queijo muçarela", new BigDecimal("2.0"), 200);
        MockHttpServletRequestBuilder request = post("/api/ingredientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("/api/ingredientes/{id}"));

    }

    @Test
    void deveRetornar400CasoIngredienteRepetido() throws Exception {

        Ingrediente ingrediente = new Ingrediente(
                "Queijo provolone",
                200,
                new BigDecimal("2.0"));

        ingredienteRepository.save(ingrediente);

        NovoIngredienteRequest body = new NovoIngredienteRequest("Queijo provolone", new BigDecimal("2.0"), 200);
        MockHttpServletRequestBuilder request = post("/api/ingredientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    void deveRetornar400CasoIngredienteTenhaNomeNulo() throws Exception {

        NovoIngredienteRequest body = new NovoIngredienteRequest(null, new BigDecimal("2.0"), 200);
        MockHttpServletRequestBuilder request = post("/api/ingredientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    void deveRetornar400CasoIngredienteTenhaQuantidadeNegativa() throws Exception {

        NovoIngredienteRequest body = new NovoIngredienteRequest("Queijo prato", new BigDecimal("2.0"), -200);
        MockHttpServletRequestBuilder request = post("/api/ingredientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    void deveRetornar400CasoIngredienteTenhaPrecoNegativo() throws Exception {

        NovoIngredienteRequest body = new NovoIngredienteRequest("Queijo gorgonzola", new BigDecimal("-2.0"), 200);
        MockHttpServletRequestBuilder request = post("/api/ingredientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }
}