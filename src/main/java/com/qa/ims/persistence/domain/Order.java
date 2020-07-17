package com.qa.ims.persistence.domain;

public class Order {

	private static Long Order_id;
	private static Long Product_Id;
	private Long Customer_id;
	private Long Order;
	private String first_name;
	private String surname;
	private Long orderRef;
	private String product_name;
	private float price;

	public Order(Long order_id, Long product_Id, Long customer_id, Long order) {

		this.Order_id = order_id;
		this.Product_Id = product_Id;
		this.Customer_id = customer_id;
		this.Order = order;

	}

	public Order(Long product_Id, Long customer_id, Long order) {
		this.Product_Id = product_Id;
		this.Customer_id = customer_id;
		this.Order = order;

	}

	public Order(Long order_id, Long product_Id) {
		Order_id = order_id;
		Product_Id = product_Id;
	}

	public Order(String first_name, String surname, Long orderRef, String product_name, float price) {
		this.first_name = first_name;
		this.surname = surname;
		this.orderRef = orderRef;
		this.product_name = product_name;
		this.price = price;
	}

	@Override
	public String toString() {
		return "First Name " + first_name + " Surname: " + surname + " Order# " + orderRef + " Product Name: "
				+ product_name + " Price " + price;
	}

	public static Long getOrder_id() {
		return Order_id;
	}

	public void setOrder_id(Long order_id) {
		Order_id = order_id;
	}

	public static Long getProduct_Id() {
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

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Long getOrderRef() {
		return orderRef;
	}

	public void setOrderRef(Long orderRef) {
		this.orderRef = orderRef;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
