package br.com.lucasmancan.gap.controllers;

import br.com.lucasmancan.gap.exceptions.AppNotFoundException;
import br.com.lucasmancan.gap.models.dto.BrandDTO;
import br.com.lucasmancan.gap.services.interfaces.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BrandControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService service;

    @Before
    public void init() throws AppNotFoundException {
        BrandDTO brandDTO = new BrandDTO(1L, "Brand Test");
        when(service.findById(1L)).thenReturn(brandDTO);
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/brands/123423")).andExpect(status().isNotFound());
    }
    @Test
    public void shouldGetABrandAndReturnOK() throws Exception {

        mockMvc.perform(get("/brands/1"))
                /*.andDo(print())*/
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Brand Test")));


        verify(service, times(1)).findById(1L);

    }

    @Test
    public void shouldGetAllBrandsAndReturnOK() throws Exception {

        List<BrandDTO> brands = Arrays.asList(
                new BrandDTO(1L, "Brand A"),
                new BrandDTO(2L, "Brand B"));

        when(service.findAll()).thenReturn(brands);

        mockMvc.perform(get("/brands"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Brand A")));

        verify(service, times(1)).findAll();
    }

    @Test
    public void shouldSaveBrandAndReturnCREATED() throws Exception {

        BrandDTO newBrand = new BrandDTO(1L, "Brand A");

        when(service.save(any(BrandDTO.class))).thenReturn(newBrand);

        mockMvc.perform(post("/brands")
                .content(om.writeValueAsString(newBrand))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                /*.andDo(print())*/
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Brand A")));


        verify(service, times(1)).save(any(BrandDTO.class));

    }

    @Test
    public void shouldUpdateBrandAndReturnOK() throws Exception {

        BrandDTO brand = new BrandDTO(1L, "Brand A");

        when(service.save(any(BrandDTO.class))).thenReturn(brand);

        mockMvc.perform(put("/brands/1")
                .content(om.writeValueAsString(brand))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Brand A")));

    }

    @Test
    public void shouldInactivateBrandAndReturnOK() throws Exception {

        doNothing().when(service).inactivate(1L);

        mockMvc.perform(delete("/brands/1"))
                /*.andDo(print())*/
                .andExpect(status().isOk());

        verify(service, times(1)).inactivate(1L);
    }
    
}
