package com.example.shoppingmanager.controller;

import com.example.shoppingmanager.dto.request.AddressRequestDto;
import com.example.shoppingmanager.dto.request.CustomerRequestDto;
import com.example.shoppingmanager.dto.response.AddressResponseDto;
import com.example.shoppingmanager.dto.response.CustomerResponseDto;
import com.example.shoppingmanager.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CustomerService customerService;

    private CustomerResponseDto customerResponseDto;
    private CustomerRequestDto customerRequestDto;

    @BeforeEach
    void setUp() {
        AddressRequestDto addressRequest = new AddressRequestDto(
                "Main Street",
                "New York",
                123,
                10001);

        Set<AddressRequestDto> addressRequests = new HashSet<>();
        addressRequests.add(addressRequest);

        customerRequestDto = new CustomerRequestDto(
                "John",
                "Doe",
                "john.doe@example.com",
                addressRequests);

        AddressResponseDto addressResponse = new AddressResponseDto(
                "Main Street",
                "New York",
                123,
                10001);
        Set<AddressResponseDto> addressResponses = new HashSet<>();
        addressResponses.add(addressResponse);

        customerResponseDto = new CustomerResponseDto(
                1L,
                "John",
                "Doe",
                "john.doe@example.com",
                addressResponses);
    }

    @Test
    void getCustomerById() throws Exception {
        long customerId = 1L;
        when(customerService.getCustomerById(anyLong())).thenReturn(customerResponseDto);

        mockMvc.perform(get("/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(customerResponseDto));
    }

    @Test
    void getCustomerByEmail() throws Exception {
        String email = "john.doe@example.com";
        when(customerService.getCustomerByEmail(anyString())).thenReturn(customerResponseDto);

        mockMvc.perform(get("/customers/email/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(customerResponseDto));
    }

    @Test
    void getAllCustomers() throws Exception {
        List<CustomerResponseDto> customers = List.of(customerResponseDto);
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(customers));
    }

    @Test
    void addCustomer() throws Exception {
        when(customerService.addCustomer(any(CustomerRequestDto.class))).thenReturn(customerResponseDto);

        mockMvc.perform(post("/customers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customerRequestDto)))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(customerResponseDto));
    }

    @Test
    void removeCustomer() throws Exception {
        String email = "john.doe@example.com";
        doNothing().when(customerService).removeCustomer(anyString());

        mockMvc.perform(delete("/customers/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateCustomerByEmail() throws Exception {
        String email = "john.doe@example.com";
        Map<String, Object> parameters = Map.of(
                "firstName", "John",
                "lastName", "Doe",
                "addresses", List.of(new AddressRequestDto("Main Street", "New York", 123, 10001))
        );

        when(customerService.updateCustomerByEmail(anyString(), any(Map.class))).thenReturn(customerResponseDto);

        mockMvc.perform(put("/customers/update/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(parameters)))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals(customerResponseDto));
    }
}