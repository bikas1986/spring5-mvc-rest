package com.bikas.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bikas.api.v1.model.CustomerDTO;
import com.bikas.domain.Customer;

class CustomerMapperTest {

	public static final String FIRSTNAME = "Jimmy";
    public static final String LASTNAME = "Fallon";
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	final void testCustomerToCustomerDTO() {
		//given
        Customer customer = new Customer();
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);
        
        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        
        //then
        assertEquals(FIRSTNAME, customerDTO.getFirstName());
        assertEquals(LASTNAME, customerDTO.getLastName());
	}
	
	@Test
	final void testCustomerDtoToCustomer() {
		//given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName(FIRSTNAME);
		customerDTO.setLastName(LASTNAME);
		
		//when
		Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
		
		//then
		assertEquals(FIRSTNAME, customer.getFirstName());
		assertEquals(LASTNAME, customer.getLastName());
	}

}
