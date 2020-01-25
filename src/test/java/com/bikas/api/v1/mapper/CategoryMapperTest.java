package com.bikas.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.bikas.api.v1.model.CategoryDTO;
import com.bikas.domain.Category;

class CategoryMapperTest {
	public static final String NAME = "Joe";
    public static final long ID = 1L;
    
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
	@Test
	void testCategoryToCategoryDTO() {
		//given
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);
        
        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
        
        
        //then
        assertEquals(NAME, categoryDTO.getName());
        assertEquals(ID, categoryDTO.getId());
	}

}
