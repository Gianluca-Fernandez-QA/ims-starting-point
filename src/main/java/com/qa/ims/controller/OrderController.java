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
			LOGGER.info(order.toString());
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}
}
