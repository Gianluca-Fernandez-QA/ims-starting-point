package com.qa.ims.persistence.domain;

public class Product {
	private Long Product_id;
	private String Product_name;
	private float Price;

	public Product(String Product_name, float Price) {
		this.Product_name = Product_name;
		this.Price = Price;
	}

	public Product(Long Product_id, String Product_name, float Price) {
		this.Product_id = Product_id;
		this.Product_name = Product_name;
		this.Price = Price;
	}

	public Long getProduct_id() {
		return Product_id;
	}

	public void setProduct_id(Long product_id) {
		Product_id = product_id;
	}

	public String getProduct_name() {
		return Product_name;
	}

	public void setProduct_name(String product_name) {
		Product_name = product_name;
	}

	public float getPrice() {
		return Price;
	}

	public void setPrice(float price) {
		Price = price;
	}

}
