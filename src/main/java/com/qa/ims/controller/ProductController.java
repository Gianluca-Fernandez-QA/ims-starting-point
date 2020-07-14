package com.qa.ims.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Product;
import com.qa.ims.services.CrudServices;
import com.qa.ims.utils.Utils;

public class ProductController implements CrudController<Product> {

	public static final Logger LOGGER = Logger.getLogger(ProductController.class);

	private CrudServices<Product> productService;

	public ProductController(CrudServices<Product> productService) {
		this.productService = productService;
	}

	String getInput() {
		return Utils.getInput();
	}

	/**
	 * Reads all products to the logger
	 */
	@Override
	public List<Product> readAll() {
		List<Product> product = productService.readAll();
		for (Product products : product) {
			LOGGER.info(product.toString());
		}
		return product;
	}

	/**
	 * Creates a Product by taking in user input
	 */
	@Override
	public Product create() {
		LOGGER.info("Enter a product name");
		String Product_name = getInput();
		LOGGER.info("Enter product's price");
		float Price = Float.parseFloat(getInput());
		Product product = productService.create(new Product(Product_name, Price));
		LOGGER.info("Product created");
		return product;
	}

	/**
	 * Updates an Product customer by taking in user input
	 */
	@Override
	public Product update() {
		LOGGER.info("Please enter the id of the customer you would like to update");
		Long id = Long.valueOf(getInput());
		LOGGER.info("Enter Product Name");
		String Product_name = getInput();
		LOGGER.info("Enter Price");
		float Price = Float.parseFloat(getInput());
		Product product = productService.update(new Product(id, Product_name, Price));
		LOGGER.info("Product Updated");
		return product;
	}

	/**
	 * Deletes an Product customer by the id of the customer
	 */
	@Override
	public void delete() {
		LOGGER.info("Please enter the id of the product you would like to delete");
		Long id = Long.valueOf(getInput());
		productService.delete(id);
	}

}
