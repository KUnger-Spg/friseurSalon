package at.spengergasse.friseursalon.presentation.api;


import at.spengergasse.friseursalon.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static at.spengergasse.friseursalon.TestFixtures.austria;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CountryRestController.class)

public class CountryRestControllerTest {

    @MockBean
    private CountryService countryService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        assumeThat(countryService).isNotNull();
        assumeThat(mockMvc).isNotNull();
    }

    @Test
    void ensureGetCountriesIsNotEmpty() throws Exception {
        //given
        var request = get ("/api/countries").accept(MediaType.APPLICATION_JSON);
        when (countryService.getAllCountries()).thenReturn(List.of(austria));

        //expect
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Österreich"))
                .andExpect(jsonPath("$[0].iso2Code").value("AT"))
                .andDo(print())
        ;

    }
}
