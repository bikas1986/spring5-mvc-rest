package com.bikas.bootstrap;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.bikas.domain.Category;
import com.bikas.domain.Customer;
import com.bikas.repositories.CategoryRepository;
import com.bikas.repositories.CustomerRepository;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {
	private CategoryRepository categoryRepository;
	private CustomerRepository customerRepository;
	
	


	public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadCategory();
		loadCustomerData();
	}


	private void loadCategory() {
		Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);


        System.out.println("Data Loaded = " + categoryRepository.count() );
	}
	
	private void loadCustomerData() {
		Customer customer1 = new Customer();
		customer1.setFirstName("Bikas");
		customer1.setLastName("Patro");
		
		Customer customer2 = new Customer();
		customer2.setFirstName("Rohit");
		customer2.setLastName("Patro");
		
		customerRepository.save(customer1);
		customerRepository.save(customer2);
		
	}

}
