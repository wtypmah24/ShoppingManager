package com.example.shoppingmanager.service;

import com.example.shoppingmanager.dto.request.CustomerRequestDto;
import com.example.shoppingmanager.dto.response.CustomerResponseDto;
import com.example.shoppingmanager.entity.Customer;
import com.example.shoppingmanager.exception.CustomerException;
import com.example.shoppingmanager.mapper.CustomerMapper;
import com.example.shoppingmanager.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper mapper;
    private final CustomerRepository repository;

    @Transactional
    public CustomerResponseDto addCustomer(CustomerRequestDto customerCandidate) throws CustomerException {

        checkCustomerCandidates(customerCandidate);
        return mapper
                .customerToCustomerResponseDto(repository
                        .save(mapper.customerRequestDtoToCustomer(customerCandidate)));
    }

    @Transactional
    public void removeCustomer(String email) {
        repository.removeCustomerByEmail(email);
    }

    public CustomerResponseDto getCustomerById(long id) throws CustomerException {

        return repository
                .findById(id)
                .map(mapper::customerToCustomerResponseDto)
                .orElseThrow(() -> new CustomerException("There is no customer with id: " + id));
    }

    public CustomerResponseDto getCustomerByEmail(String email) throws CustomerException {

        return repository
                .findByEmail(email)
                .map(mapper::customerToCustomerResponseDto)
                .orElseThrow(() -> new CustomerException("There is no customer with email: " + email));
    }

    public List<CustomerResponseDto> getAllCustomers() {

        return repository
                .findAll()
                .stream()
                .map(mapper::customerToCustomerResponseDto)
                .toList();
    }

    @Transactional
    public CustomerResponseDto updateCustomerByEmail(String email, Map<String, Object> parameters)
            throws CustomerException {

        Customer customer = repository
                .findByEmail(email)
                .orElseThrow(() -> new CustomerException("There is no customer with email " + email));

        parameters.forEach((key, value) -> {
            switch (key) {
                case "firstName" -> customer.setFirstName((String) value);
                case "lastName" -> customer.setLastName((String) value);
                case "email" -> customer.setEmail((String) value);
            }
        });

        Customer updatedCustomer = repository.save(customer);

        return mapper.customerToCustomerResponseDto(updatedCustomer);
    }

    private void checkCustomerCandidates(CustomerRequestDto customerRequestDto) throws CustomerException {

        if (customerRequestDto == null) throw new CustomerException("You didn't provide a customer request!");

        if (
                customerRequestDto.firstName().isBlank() ||
                customerRequestDto.addresses().isEmpty() ||
                customerRequestDto.lastName().isBlank() ||
                customerRequestDto.email().isBlank()
        ) throw new CustomerException("You didn't provide a full customer information!");
    }
}
