package com.bikas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bikas.api.v1.mapper.CustomerMapper;
import com.bikas.api.v1.model.CustomerDTO;
import com.bikas.domain.Customer;
import com.bikas.repositories.CustomerRepository;

import lombok.AllArgsConstructor;


//@Slf4j
@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    
	@Override
	public List<CustomerDTO> getAllCustomer() {
		return customerRepository.findAll()
					.stream()
					.map(customer -> {
						CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
						customerDTO.setCustomerUrl("/api/v1/customers/"+customer.getId());
						return customerDTO;
					})
					.collect(Collectors.toList());
		
		
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerRepository.findById(id)
					.map(customer -> {
						CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
						customerDTO.setCustomerUrl("/api/v1/customers/"+customer.getId());
						return customerDTO;
					})
					.orElseThrow(RuntimeException::new);  //todo implement better exception handling
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
		Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
		
		Customer savedCustomer = customerRepository.save(customer);
		
		CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
		
		returnDTO.setCustomerUrl("/api/v1/customers/"+savedCustomer.getId());
		
		return returnDTO;
	}
	
	

}
