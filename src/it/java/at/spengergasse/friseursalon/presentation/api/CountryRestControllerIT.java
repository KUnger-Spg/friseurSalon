package at.spengergasse.friseursalon.presentation.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest

public class CountryRestControllerIT {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        assumeThat(mockMvc).isNotNull();
    }

    @Test
    void ensureGetCountriesIsNotEmpty() throws Exception {
        //given
        var request = get ("/api/countries").accept(MediaType.APPLICATION_JSON);

        //expect
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Deutschland"))
                .andExpect(jsonPath("$[0].iso2Code").value("DE"))
                .andExpect(jsonPath("$[1].name").value("Schweiz"))
                .andExpect(jsonPath("$[1].iso2Code").value("CH"))
        ;

    }
}
