package com.bikas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bikas.api.v1.mapper.CustomerMapper;
import com.bikas.api.v1.model.CustomerDTO;
import com.bikas.domain.Customer;
import com.bikas.repositories.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {	

	
	CustomerMapper customerMapper = CustomerMapper.INSTANCE;
	
	@Mock
    CustomerRepository customerRepository;
	
	
	CustomerService customerService;
	
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		customerService = new CustomerServiceImpl(customerMapper, customerRepository);
	}

	@Test
	void testGetAllCustomer() {
		Customer customer1 = new Customer();
		customer1.setId(1l);
		customer1.setFirstName("Bikas");
		customer1.setLastName("Patro");
		
		Customer customer2 = new Customer();
		customer2.setId(2l);
		customer2.setFirstName("Rohit");
		customer2.setLastName("Patro");
				
		
		when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));
		
		//when
		List<CustomerDTO> customerDTOs = customerService.getAllCustomer();
		
		//then
		assertEquals(2, customerDTOs.size());
	}

	@Test
	void testGetCustomerById() {
		Customer customer = new Customer();
		customer.setId(1l);
		customer.setFirstName("Bikas");
		customer.setLastName("Patro");
		
		when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer));
		
		//when
		CustomerDTO customerDTO = customerService.getCustomerById(1L);
		
		assertEquals("Bikas", customerDTO.getFirstName());
	}

}
