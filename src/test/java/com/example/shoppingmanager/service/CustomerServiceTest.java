package com.example.shoppingmanager.service;

import com.example.shoppingmanager.dto.request.CustomerRequestDto;
import com.example.shoppingmanager.dto.response.CustomerResponseDto;
import com.example.shoppingmanager.entity.Address;
import com.example.shoppingmanager.entity.Customer;
import com.example.shoppingmanager.exception.CustomerException;
import com.example.shoppingmanager.mapper.CustomerMapper;
import com.example.shoppingmanager.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerMapper mapper;

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerResponseDto customerResponseDto;
    private CustomerRequestDto customerRequestDto;


    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setAddresses(new HashSet<>());

        customerRequestDto = new CustomerRequestDto(
                "John",
                "Doe",
                "john.doe@example.com",
                new HashSet<>());

        customerResponseDto = new CustomerResponseDto(
                1L,
                "John",
                "Doe",
                "john.doe@example.com",
                new HashSet<>());
    }

    @Test
    void addCustomer() throws CustomerException {
        when(mapper.customerRequestDtoToCustomer(customerRequestDto)).thenReturn(customer);
        when(repository.save(customer)).thenReturn(customer);
        when(mapper.customerToCustomerResponseDto(customer)).thenReturn(customerResponseDto);

        CustomerResponseDto response = customerService.addCustomer(customerRequestDto);

        assertNotNull(response);
        assertEquals(customerResponseDto, response);

        verify(mapper, times(1)).customerRequestDtoToCustomer(customerRequestDto);
        verify(repository, times(1)).save(customer);
        verify(mapper, times(1)).customerToCustomerResponseDto(customer);
    }

    @Test
    void removeCustomer() throws CustomerException {
        doNothing().when(repository).removeCustomerByEmail("john.doe@example.com");

        customerService.removeCustomer("john.doe@example.com");

        verify(repository, times(1)).removeCustomerByEmail("john.doe@example.com");

    }

    @Test
    void getCustomerById() throws CustomerException {
        when(repository.findById(1L)).thenReturn(Optional.of(customer));
        when(mapper.customerToCustomerResponseDto(customer)).thenReturn(customerResponseDto);

        CustomerResponseDto response = customerService.getCustomerById(1L);

        assertNotNull(response);
        assertEquals(customerResponseDto, response);

        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).customerToCustomerResponseDto(customer);
    }

    @Test
    void getCustomerByEmail() throws CustomerException {
        when(repository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(customer));
        when(mapper.customerToCustomerResponseDto(customer)).thenReturn(customerResponseDto);

        CustomerResponseDto response = customerService.getCustomerByEmail("john.doe@example.com");

        assertNotNull(response);
        assertEquals(customerResponseDto, response);

        verify(repository, times(1)).findByEmail("john.doe@example.com");
        verify(mapper, times(1)).customerToCustomerResponseDto(customer);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customers = List.of(customer);
        when(repository.findAll()).thenReturn(customers);
        when(mapper.customerToCustomerResponseDto(customer)).thenReturn(customerResponseDto);

        List<CustomerResponseDto> response = customerService.getAllCustomers();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertTrue(response.stream().anyMatch(dto -> dto.equals(customerResponseDto)));

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).customerToCustomerResponseDto(customer);
    }

    @Test
    void updateCustomerByEmail() throws CustomerException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("firstName", "Jane");
        parameters.put("lastName", "Smith");
        parameters.put("email", "jane.smith@example.com");

        when(repository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(customer));
        when(repository.save(customer)).thenReturn(customer);
        when(mapper.customerToCustomerResponseDto(customer)).thenReturn(customerResponseDto);

        CustomerResponseDto response = customerService.updateCustomerByEmail("john.doe@example.com", parameters);

        assertNotNull(response);
        assertEquals(customerResponseDto, response);

        verify(repository, times(1)).findByEmail("john.doe@example.com");
        verify(repository, times(1)).save(customer);
        verify(mapper, times(1)).customerToCustomerResponseDto(customer);
    }
}