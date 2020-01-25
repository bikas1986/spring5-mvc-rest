package com.bikas.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.bikas.api.v1.model.CategoryDTO;
import com.bikas.domain.Category;

@Mapper
public interface CategoryMapper {
	CategoryMapper INSTANCE =  Mappers.getMapper(CategoryMapper.class);
	
	CategoryDTO categoryToCategoryDTO(Category category);
}
