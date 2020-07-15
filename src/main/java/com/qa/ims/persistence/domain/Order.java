package com.qa.ims.persistence.domain;

public class Order {

	private Long Order_id;
	private Long Product_Id;
	private Long Customer_id;
	private Long Order;
	private float Price;
	private float Total_Price;

	public Order(Long order_id, Long product_Id, Long customer_id, Long order, float price) {

		this.Order_id = order_id;
		this.Product_Id = product_Id;
		this.Customer_id = customer_id;
		this.Order = order;
		this.Price = price;
	}

	public Order(Long product_Id, Long customer_id, Long order) {
		this.Product_Id = product_Id;
		this.Customer_id = customer_id;
		this.Order = order;

	}

	public Long getOrder_id() {
		return Order_id;
	}

	public void setOrder_id(Long order_id) {
		Order_id = order_id;
	}

	public Long getProduct_Id() {
		return Product_Id;
	}

	public void setProduct_Id(Long product_Id) {
		Product_Id = product_Id;
	}

	public Long getCustomer_id() {
		return Customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		Customer_id = customer_id;
	}

	public Long getOrder() {
		return Order;
	}

	public void setOrder(Long order) {
		Order = order;
	}

	public float getPrice() {
		return Price;
	}

	public void setPrice(float price) {
		Price = price;
	}

}
