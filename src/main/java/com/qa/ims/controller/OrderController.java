package com.qa.ims.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.dao.OrdersDaoMySql;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.services.CrudServices;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {
	public static final Logger LOGGER = Logger.getLogger(ProductController.class);
	private CrudServices<Order> orderService;

	public OrderController(CrudServices<Order> orderService) {
		this.orderService = orderService;
	}

	String getInput() {
		return Utils.getInput();
	}

	@Override
	public List<Order> readAll() {
		List<Order> order = orderService.readAll();
		for (Order orders : order) {
			System.out.println(orders.toString());
		}
		return order;
	}

	@Override
	public Order create() {
		LOGGER.info("Enter Your Customer Id");
		long custId = Integer.parseInt(Utils.getInput());
		while (true) {
			if (OrdersDaoMySql.UserChecker(custId) == true) {
				LOGGER.info("Enter Your Product Id");
				long prodId = Integer.parseInt(Utils.getInput());
				if (OrdersDaoMySql.prodChecker(prodId) == true) {
					Long ordaw = OrdersDaoMySql.order(custId);
					Order order = orderService.create(new Order(prodId, custId, ordaw));
					return order;
				} else {
					LOGGER.error("Incorrect Customer Id");
					LOGGER.info("Enter Your Customer Id");
					custId = Integer.parseInt(Utils.getInput());
					continue;
				}
			}
		}
		// TODO Auto-generated method stub

	}

	@Override
	public Order update() {
		LOGGER.info("Please enter the id of the Order you would like to update");
		Long id = Long.valueOf(getInput());
		LOGGER.info("Please enter the id of the Product you would like to update");
		Long prodid = Long.valueOf(getInput());
		Order order = orderService.update(new Order(id, prodid));
		LOGGER.info("Order Updated");
		return order;
	}

	@Override
	public void delete() {
		LOGGER.info("Enter Order Reference Of Order to delete");
		Long id = Long.valueOf(getInput());
		orderService.delete(id);

	}
}
