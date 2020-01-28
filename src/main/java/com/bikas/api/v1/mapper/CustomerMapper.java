package com.bikas.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bikas.api.v1.model.CustomerDTO;
import com.bikas.domain.Customer;

@Mapper
public interface CustomerMapper {
	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
	
	CustomerDTO customerToCustomerDTO(Customer customer);
}
