package com.bikas.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bikas.api.v1.model.CategoryDTO;
import com.bikas.api.v1.model.CategoryListDTO;
import com.bikas.services.CategoryService;

@Controller
@RequestMapping("/api/v1/categories/")
public class CategoryController {
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}
	
	@GetMapping("{name}")
	public ResponseEntity<CategoryDTO> getCategoryName(@PathVariable String name){
		return new ResponseEntity<CategoryDTO>(
				categoryService.getCategoryByName(name), HttpStatus.OK);
		
	}
	
	@GetMapping
	public ResponseEntity<CategoryListDTO> getAllCategories(){
		return new ResponseEntity<CategoryListDTO>(
				new CategoryListDTO(categoryService.getAllCategories()), HttpStatus.OK);
	}
}
