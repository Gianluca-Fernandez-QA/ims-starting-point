package com.qa.ims.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.domain.Product;
import com.qa.ims.services.ProductServices;

@RunWith(MockitoJUnitRunner.class)

public class ProductControllerTest {
	@Mock
	private ProductServices productServices;

	@Spy
	@InjectMocks
	private ProductController productController;

	@Test
	public void readAllTest() {
		System.out.println(productServices);
		ProductController productController = new ProductController(productServices);
		List<Product> products = new ArrayList<>();
		products.add(new Product("Chris", 10f));
		products.add(new Product("Rhys", 23f));
		products.add(new Product("Nic", 32f));
		Mockito.when(productServices.readAll()).thenReturn(products);
		assertEquals(products, productController.readAll());
	}

	@Test
	public void createTest() {
		String product_name = "Chris";
		float price = 10f;

		Mockito.doReturn(price).when(productController).getInputF();
//		Mockito.doReturn(product_name).when(productController).getInput();
		Product product = new Product(product_name, price);
		Product savedProduct = new Product(1l, "Chris", price);
		System.out.println(savedProduct);
		Mockito.when(productServices.create(product)).thenReturn(savedProduct);
		System.out.println(savedProduct);
		System.out.println(product);
		assertEquals(savedProduct, productController.create());
	}

}
