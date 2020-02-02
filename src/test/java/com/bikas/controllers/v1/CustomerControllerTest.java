package com.bikas.controllers.v1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bikas.api.v1.model.CustomerDTO;
import com.bikas.services.CustomerService;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
	@Mock
	CustomerService customerService;
	
	@InjectMocks
	CustomerController customerController;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	void testListOfCustomer() throws Exception {
		CustomerDTO customer1  = new CustomerDTO();
		customer1.setFirstName("Bikas");
		customer1.setLastName("Patro");
		customer1.setCustomerUrl("/api/v1/customers/1");
		
		
		CustomerDTO customer2  = new CustomerDTO();
		customer2.setFirstName("Rohit");
		customer2.setLastName("Patro");
		customer2.setCustomerUrl("/api/v1/customers/2");
		
		when(customerService.getAllCustomer()).thenReturn(Arrays.asList(customer1, customer2));
		
		
		mockMvc.perform(get("/api/v1/customers/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
		/*
		String response = mockMvc.perform(get("/api/v1/customers/")
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		
		
		System.out.println("RESPONSE :: "+ response);
		*/
	}

	
	@Test
	void testGetCustomerById() throws Exception {
		CustomerDTO customer1  = new CustomerDTO();
		customer1.setFirstName("Bikas");
		customer1.setLastName("Patro");
		customer1.setCustomerUrl("/api/v1/customers/1");
		
		when(customerService.getCustomerById(anyLong())).thenReturn(customer1);
		
		mockMvc.perform(get("/api/v1/customers/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo("Bikas")));
		
		
	}
}
