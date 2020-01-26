package com.bikas.controllers.v1;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bikas.api.v1.model.CategoryDTO;
import com.bikas.services.CategoryService;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
	public static final String NAME = "Jim";
	
	@Mock
	CategoryService categoryService;
	
	@InjectMocks
	CategoryController categoryController;
	
	MockMvc mockMvc;
	
	@BeforeEach
	public void setup() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}
	
	@Test
	final void testGetCategoryName() throws Exception {
		CategoryDTO category1 = new CategoryDTO();
        category1.setId(1l);
        category1.setName(NAME);
        
        when(categoryService.getCategoryByName(NAME)).thenReturn(category1);
        
        mockMvc.perform(get("/api/v1/categories/Jim").accept(MediaType.APPLICATION_JSON))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$.name", equalTo(NAME)));
	}

	@Test
	final void testGetAllCategories() throws Exception {
		CategoryDTO category1 = new CategoryDTO();
        category1.setId(1l);
        category1.setName(NAME);

        CategoryDTO category2 = new CategoryDTO();
        category2.setId(2l);
        category2.setName("Bob");

        List<CategoryDTO> categoryList = Arrays.asList(category1, category2);
        
        when(categoryService.getAllCategories()).thenReturn(categoryList);
        
        mockMvc.perform(get("/api/v1/categories/")
        		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.categories", hasSize(2)));
	}

}
