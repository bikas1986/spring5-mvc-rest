package com.bikas.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bikas.api.v1.model.CustomerDTO;
import com.bikas.api.v1.model.CustomerListDTO;
import com.bikas.services.CustomerService;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}
	
	@GetMapping("/")
	public ResponseEntity<CustomerListDTO> getListOfCustomers(){
		return new ResponseEntity<CustomerListDTO>(new CustomerListDTO(customerService.getAllCustomer()),
				HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
		return new ResponseEntity<CustomerDTO>(customerService.getCustomerById(id),
				HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<CustomerDTO>(customerService.createNewCustomer(customerDTO),
				HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CustomerDTO> updateCustomerById(@PathVariable Long id
			, @RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<CustomerDTO>(customerService.updateCustomerByDTO(id, customerDTO)
				, HttpStatus.OK);
	}
	
	@PatchMapping({"/{id}"})
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<CustomerDTO>(customerService.patchCustomer(id, customerDTO),
                HttpStatus.OK);
    }
}
