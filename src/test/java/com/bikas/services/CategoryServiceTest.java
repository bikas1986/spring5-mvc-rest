package com.bikas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bikas.api.v1.mapper.CategoryMapper;
import com.bikas.api.v1.model.CategoryDTO;
import com.bikas.domain.Category;
import com.bikas.repositories.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
	public static final Long ID = 2L;
    public static final String NAME = "Jimmy";
    
    @Mock
    CategoryRepository categoryRepository;
    
    @Mock
    CategoryMapper categoryMapper;
    //CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
    
    @InjectMocks
    CategoryServiceImpl categoryService;
    
    
	@BeforeEach
	void setUp() throws Exception {
	
	}

	@Test
	final void testGetAllCategories() {

        //given
        List<Category> categoryList = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categoryList);
        
        //when
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
        
        //then
        assertEquals(3, categoryDTOList.size());
	}

	@Test
	final void testGetCategoryByName() {
		//given
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);
        
        when(categoryRepository.findByName(anyString())).thenReturn(category);
        
        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);
        
        //then
        assertEquals(ID, category.getId());
        assertEquals(NAME, category.getName());
        
        
	}

}
