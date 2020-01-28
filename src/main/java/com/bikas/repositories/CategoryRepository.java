package com.bikas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bikas.domain.Category;

//@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByName(String name);
}
