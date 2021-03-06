package com.bikas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bikas.api.v1.mapper.CustomerMapper;
import com.bikas.api.v1.model.CustomerDTO;
import com.bikas.controllers.v1.CustomerController;
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
						customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
						return customerDTO;
					})
					.collect(Collectors.toList());
		
		
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerRepository.findById(id)
					.map(customer -> {
						CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
						customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
						return customerDTO;
					})
					.orElseThrow(ResourceNotFoundException::new); 
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
		
		return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));
	}

	private CustomerDTO saveAndReturnDTO(Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);
		
		CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
		
		returnDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
		
		return returnDTO;
	}

	@Override
	public CustomerDTO updateCustomerByDTO(Long id, CustomerDTO customerDTO) {
		Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
		customer.setId(id);
		
		return saveAndReturnDTO(customer);
	}

	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
		return customerRepository.findById(id).map(customer -> {
			
			if(customerDTO.getFirstName() != null) {
				customer.setFirstName(customerDTO.getFirstName());
			}
			
			if(customerDTO.getLastName() != null) {
				customer.setLastName(customerDTO.getLastName());
			}
			
			CustomerDTO returnDto = customerMapper.customerToCustomerDTO(customerRepository.save(customer));

            returnDto.setCustomerUrl(getCustomerUrl(id));

            return returnDto;
		}).orElseThrow(ResourceNotFoundException::new); 
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
	}
	
	
	 private String getCustomerUrl(Long id) {
	        return CustomerController.BASE_URL + "/" + id;
	    }
	

}
