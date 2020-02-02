package com.bikas.services.integrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bikas.api.v1.mapper.CustomerMapper;
import com.bikas.api.v1.model.CustomerDTO;
import com.bikas.bootstrap.Bootstrap;
import com.bikas.domain.Customer;
import com.bikas.repositories.CategoryRepository;
import com.bikas.repositories.CustomerRepository;
import com.bikas.services.CustomerService;
import com.bikas.services.CustomerServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
@DataJpaTest
class CustomerServiceIT {
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	CustomerService customerService;
	
	@BeforeEach
	void setUp() throws Exception {
		log.info("LOADING CUSTOMER DATA");
		log.info("Customer size :: "+customerRepository.findAll().size());
		
		//setup data for testing
		Bootstrap  bootstrap = new Bootstrap(categoryRepository, customerRepository);
		bootstrap.loadCategory();
		bootstrap.loadCustomerData();
		
		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
	}

	@Test
	void patchCustomerUpdateFirstName() throws Exception{
		String updatedName = "UpdatedName";
		Long id = getCustomerIdValue();
		
		Customer originalCustomer = customerRepository.getOne(id);
		//save original first name
		String originalFirstName = originalCustomer.getFirstName();
		String originalLastName = originalCustomer.getLastName();
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName(updatedName);
		
		
		customerService.patchCustomer(id, customerDTO);
		
		Customer updatedCustomer = customerRepository.findById(id).get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getFirstName());
		assertNotEquals(originalFirstName, updatedCustomer.getFirstName());
		
	}
	
	@Test
    public void patchCustomerUpdateLastName() throws Exception {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        //save original first/last name
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastName());
        assertEquals(originalFirstName, updatedCustomer.getFirstName());
        assertNotEquals(originalLastName, updatedCustomer.getLastName());
    }
	
	private Long getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();

        log.info("Customers Found: " + customers.size());

        //return first id
        return customers.get(0).getId();
    }

}
