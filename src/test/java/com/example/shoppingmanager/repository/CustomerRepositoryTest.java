package com.example.shoppingmanager.repository;

import com.example.shoppingmanager.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
    }

    @Test
    void findByEmail() {
        customerRepository.save(customer);
        assert customerRepository.findByEmail("john.doe@example.com").isPresent();
    }

    @Test
    void removeCustomerByEmail() {
        customerRepository.save(customer);
        customerRepository.removeCustomerByEmail("john.doe@example.com");
        assert customerRepository.findByEmail("john.doe@example.com").isEmpty();
    }

    @Test
    void findAll() {
        customerRepository.save(customer);
        assert customerRepository.findAll().contains(customer);
    }
}
