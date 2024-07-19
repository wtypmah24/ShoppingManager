package com.example.shoppingmanager.controller;

import com.example.shoppingmanager.dto.request.CustomerRequestDto;
import com.example.shoppingmanager.dto.response.CustomerResponseDto;
import com.example.shoppingmanager.exception.CustomerException;
import com.example.shoppingmanager.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springdoc.api.ErrorMessage;


import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/{id}")
    public CustomerResponseDto getCustomerById(@PathVariable long id) throws CustomerException {
        return service.getCustomerById(id);
    }

    @GetMapping("/email/{email}")
    public CustomerResponseDto getCustomerByEmail(@PathVariable String email) throws CustomerException {

        return service.getCustomerByEmail(email);
    }

    @GetMapping()
    public List<CustomerResponseDto> getAllCustomers() {
        return service.getAllCustomers();
    }

    @PostMapping("/add")
    public CustomerResponseDto addCustomer(@RequestBody CustomerRequestDto customerCandidate) throws CustomerException {

        return service.addCustomer(customerCandidate);
    }

    @DeleteMapping("/{email}")
    public void removeCustomer(@PathVariable String email) {
        service.removeCustomer(email);
    }

    @PutMapping("/update/{email}")
    public CustomerResponseDto updateCustomerByEmail(@PathVariable String email,
                                                     @RequestBody Map<String, Object> parameters)
            throws CustomerException {

        return service.updateCustomerByEmail(email, parameters);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(CustomerException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}