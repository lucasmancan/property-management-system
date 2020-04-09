package br.com.lucasmancan.gap.controllers;

import br.com.lucasmancan.gap.exceptions.AppNotFoundException;
import br.com.lucasmancan.gap.models.Status;
import br.com.lucasmancan.gap.models.dto.PropertyDTO;
import br.com.lucasmancan.gap.services.interfaces.PropertyService;
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
public class PropertyControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService service;

    @Before
    public void init() throws AppNotFoundException {
        when(service.findById(1L)).thenReturn(getProperty());
    }

    public static PropertyDTO getProperty(){
        PropertyDTO propertyDTO = new PropertyDTO();

        propertyDTO.setId(1L);
        propertyDTO.setBrandId(1L);
        propertyDTO.setBrandName("Brand A");
        propertyDTO.setCode(1233123L);
        propertyDTO.setDescription("TESTE");
        propertyDTO.setName("Property Test");
        propertyDTO.setStatus(Status.active);

        return propertyDTO;
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/properties/123423")).andExpect(status().isNotFound());
    }
    @Test
    public void shouldGetAPropertyAndReturnOK() throws Exception {

        mockMvc.perform(get("/properties/1"))
                /*.andDo(print())*/
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Property Test")))
                .andExpect(jsonPath("$.description", is("TESTE")));


        verify(service, times(1)).findById(1L);

    }

    @Test
    public void shouldGetAllPropertysAndReturnOK() throws Exception {

        List<PropertyDTO> properties = Arrays.asList(getProperty(),getProperty(),getProperty());

        when(service.findAll()).thenReturn(properties);

        mockMvc.perform(get("/properties"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Property A")));

        verify(service, times(1)).findAll();
    }



    @Test
    public void shouldSavePropertyAndReturnCREATED() throws Exception {

        PropertyDTO newProperty = getProperty();

        when(service.save(any(PropertyDTO.class))).thenReturn(newProperty);

        mockMvc.perform(post("/properties")
                .content(om.writeValueAsString(newProperty))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                /*.andDo(print())*/
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Property A")));


        verify(service, times(1)).save(any(PropertyDTO.class));

    }

    @Test
    public void shouldUpdatePropertyAndReturnOK() throws Exception {

        PropertyDTO brand = getProperty();

        when(service.save(any(PropertyDTO.class))).thenReturn(brand);

        mockMvc.perform(put("/properties/1")
                .content(om.writeValueAsString(brand))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Property A")));

    }

    @Test
    public void shouldInactivatePropertyAndReturnOK() throws Exception {

        doNothing().when(service).inactivate(1L);

        mockMvc.perform(delete("/properties/1"))
                /*.andDo(print())*/
                .andExpect(status().isOk());

        verify(service, times(1)).inactivate(1L);
    }
}
