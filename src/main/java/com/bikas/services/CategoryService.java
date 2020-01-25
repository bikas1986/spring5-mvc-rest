package com.bikas.services;

import java.util.List;

import com.bikas.api.v1.model.CategoryDTO;

public interface CategoryService {
	List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
