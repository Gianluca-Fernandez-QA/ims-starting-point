package com.qa.ims.persistence.domain;

public class Product {
	private Long Product_id;
	private String Product_name;
	private Float Price;
	private String Price1;

	public Product(String Product_name, String price2) {
		this.Product_name = Product_name;
		this.Price1 = price2;
	}

	public Product(String product_name, Float price) {
		this.Product_name = product_name;
		this.Price = price;
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

	@Override
	public String toString() {
		return "Product Id: " + Product_id + "Name: " + Product_name + "Price " + Price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Product_id == null) ? 0 : Product_id.hashCode());
		result = prime * result + ((Product_name == null) ? 0 : Product_name.hashCode());
		result = prime * result + ((Price == null) ? 0 : Price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (Product_id == null) {
			if (other.Product_id != null)
				return false;
		} else if (!Product_id.equals(other.Product_id))
			return false;
		if (Product_name == null) {
			if (other.Product_name != null)
				return false;
		} else if (!Product_name.equals(other.Product_name))
			return false;
		if (Price == null) {
			if (other.Price != null)
				return false;
		} else if (!Price.equals(other.Price))
			return false;
		return true;
	}

}
