package com.example.shoppingmanager.service;

import com.example.shoppingmanager.dto.request.CustomerRequestDto;
import com.example.shoppingmanager.dto.response.CustomerResponseDto;
import com.example.shoppingmanager.entity.Customer;
import com.example.shoppingmanager.exception.CustomerException;
import com.example.shoppingmanager.mapper.CustomerMapper;
import com.example.shoppingmanager.repository.CustomerRepository;
import com.example.shoppingmanager.utils.LogMessages;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
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
                .orElseThrow(() -> {
                    log.error(LogMessages.CUSTOMER_NOT_FOUND_ID, id);
                    return new CustomerException(LogMessages.CUSTOMER_NOT_FOUND_ID + id);
                });
    }

    public CustomerResponseDto getCustomerByEmail(String email) throws CustomerException {

        return repository
                .findByEmail(email)
                .map(mapper::customerToCustomerResponseDto)
                .orElseThrow(() -> {
                    log.error(LogMessages.CUSTOMER_NOT_FOUND_EMAIL, email);
                    return new CustomerException(LogMessages.CUSTOMER_NOT_FOUND_EMAIL + email);
                });
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
                .orElseThrow(() -> {
                    log.error(LogMessages.CUSTOMER_NOT_FOUND_EMAIL, email);
                    return new CustomerException(LogMessages.CUSTOMER_NOT_FOUND_EMAIL + email);
                });

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

        if (customerRequestDto == null) {
            log.error(LogMessages.WRONG_CUSTOMER_CANDIDATE);
            throw new CustomerException(LogMessages.WRONG_CUSTOMER_CANDIDATE);
        }

        if (
                customerRequestDto.firstName().isBlank() ||
                        customerRequestDto.lastName().isBlank() ||
                        customerRequestDto.email().isBlank()
        ) {
            log.error(LogMessages.WRONG_CUSTOMER_DATA);
            throw new CustomerException(LogMessages.WRONG_CUSTOMER_DATA);
        }
    }
}
