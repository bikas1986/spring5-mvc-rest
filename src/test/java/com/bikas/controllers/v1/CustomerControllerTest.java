package com.bikas.controllers.v1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.bikas.controllers.RestResponseEntityExceptionHandler;
import com.bikas.services.CustomerService;
import com.bikas.services.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest extends AbstractRestControllerTest{
	@Mock
	CustomerService customerService;
	
	@InjectMocks
	CustomerController customerController;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(customerController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	@Test
	void testListOfCustomer() throws Exception {
		CustomerDTO customer1  = new CustomerDTO();
		customer1.setFirstName("Bikas");
		customer1.setLastName("Patro");
		customer1.setCustomerUrl(CustomerController.BASE_URL+"/1");
		
		
		CustomerDTO customer2  = new CustomerDTO();
		customer2.setFirstName("Rohit");
		customer2.setLastName("Patro");
		customer2.setCustomerUrl("/api/v1/customers/2");
		
		when(customerService.getAllCustomer()).thenReturn(Arrays.asList(customer1, customer2));
		
		
		mockMvc.perform(get(CustomerController.BASE_URL+"/")
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
		customer1.setCustomerUrl(CustomerController.BASE_URL+"/1");
		
		when(customerService.getCustomerById(anyLong())).thenReturn(customer1);
		
		mockMvc.perform(get("/api/v1/customers/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo("Bikas")));
		
		
	}
	
	@Test
	void testCreateNewCustomer() throws Exception {
		//given
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstName("Bikas");
		customer.setLastName("Patro");
		
		CustomerDTO returnCustomerDTO = new CustomerDTO();
		returnCustomerDTO.setFirstName(customer.getFirstName());
		returnCustomerDTO.setLastName(customer.getLastName());
		returnCustomerDTO.setCustomerUrl(CustomerController.BASE_URL+"/1");
		
		when(customerService.createNewCustomer(customer)).thenReturn(returnCustomerDTO);
		
		//when/then
		mockMvc.perform(post("/api/v1/customers/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", equalTo("Bikas")))
				.andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL+"/1")));
	}
	
	@Test
	void testUpdateCustomer() throws Exception {
		//given
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstName("Bikas");
		customer.setLastName("Patro");
		
		CustomerDTO returnCustomerDTO = new CustomerDTO();
		returnCustomerDTO.setFirstName(customer.getFirstName());
		returnCustomerDTO.setLastName(customer.getLastName());
		returnCustomerDTO.setCustomerUrl(CustomerController.BASE_URL+"/1");
		
		when(customerService.updateCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnCustomerDTO);
		
		//when/then
		mockMvc.perform(put(CustomerController.BASE_URL+"/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo("Bikas")))
				.andExpect(jsonPath("$.lastName", equalTo("Patro")))
				.andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));
	}
	
	@Test
    public void testPatchCustomer() throws Exception {

        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Rahul");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName("Patro");
        returnDTO.setCustomerUrl(CustomerController.BASE_URL+"/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(CustomerController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Rahul")))
                .andExpect(jsonPath("$.lastName", equalTo("Patro")))
                .andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));
    }
	
	@Test
    public void testDeleteCustomer() throws Exception {

        mockMvc.perform(delete(CustomerController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }
	
	@Test
    public void testNotFoundException() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
