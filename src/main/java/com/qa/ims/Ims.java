package com.qa.ims;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.qa.ims.controller.Action;
import com.qa.ims.controller.CrudController;
import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.OrderController;
import com.qa.ims.controller.ProductController;
import com.qa.ims.persistence.dao.CustomerDaoMysql;
import com.qa.ims.persistence.dao.OrdersDaoMySql;
import com.qa.ims.persistence.dao.ProductsDaoMysql;
import com.qa.ims.persistence.domain.Domain;
import com.qa.ims.services.CustomerServices;
import com.qa.ims.services.OrderServices;
import com.qa.ims.services.ProductServices;
import com.qa.ims.utils.Utils;

public class Ims {
	String username;
	String password;
	Action action;
	Domain domain;
	public static final Logger LOGGER = Logger.getLogger(Ims.class);

	public void imsSystem() {
		LOGGER.info("What is your username");
		this.username = Utils.getInput();
		LOGGER.info("What is your password");
		this.password = Utils.getInput();

		init(username, password);
		while (true) {

			LOGGER.info("Which entity would you like to use?");
			Domain.printDomains();
			this.domain = Domain.getDomain();
			LOGGER.info("What would you like to do with " + domain.name().toLowerCase() + ":");
			Action.printActions();
			this.action = Action.getAction();
			domain(domain, username, password, action);
		}
	}

	public void doAction(CrudController<?> crudController, Action action) {
		switch (action) {
		case CREATE:
			crudController.create();
			break;
		case READ:
			crudController.readAll();
			break;
		case UPDATE:
			crudController.update();
			break;
		case DELETE:
			crudController.delete();
			break;
		case RETURN:
			break;
		default:
			break;
		}
	}

	/**
	 * To initialise the database schema. DatabaseConnectionUrl will default to
	 * localhost.
	 *
	 * @param username
	 * @param password
	 */
	public void init(String username, String password) {

		init("jdbc:mysql://" + Utils.MYSQL_URL + "/ims?serverTimezone=UTC", username, password,
				"src/main/resources/sql-schema.sql");

	}

	public String readFile(String fileLocation) {
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(fileLocation));) {
			String string;
			while ((string = br.readLine()) != null) {
				stringBuilder.append(string);
				stringBuilder.append("\r\n");
			}
		} catch (IOException e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				LOGGER.debug(ele);
			}
			LOGGER.error(e.getMessage());
		}
		return stringBuilder.toString();
	}

	/**
	 * To initialise the database with the schema needed to run the application
	 */
	public void init(String jdbcConnectionUrl, String username, String password, String fileLocation) {
		while (true) {
			try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
					BufferedReader br = new BufferedReader(new FileReader(fileLocation));) {
				String string;
				while ((string = br.readLine()) != null) {
					try (Statement statement = connection.createStatement();) {
						statement.executeUpdate(string);
					}
				}
				break;
			} catch (SQLException | IOException e) {
//			for (StackTraceElement ele : e.getStackTrace()) {
//				LOGGER.debug(ele);
//			}
				LOGGER.error(e.getMessage());
				LOGGER.info("Enter Username: ");
				username = Utils.getInput();
				this.username = username;
				LOGGER.info("Enter Password: ");
				password = Utils.getInput();
				this.password = password;

				continue;
			}
		}
	}

	public void domain(Domain domain, String username, String password, Action action) {

		switch (domain) {
		case CUSTOMER:
			CustomerController customerController = new CustomerController(
					new CustomerServices(new CustomerDaoMysql(username, password)));
			doAction(customerController, action);
			break;
		case ITEM:
			ProductController test = new ProductController(
					new ProductServices(new ProductsDaoMysql(username, password)));
			doAction(test, action);
			break;
		case ORDER:
			OrderController orderController = new OrderController(
					new OrderServices(new OrdersDaoMySql(username, password)));
			doAction(orderController, action);
			break;
		default:
			break;
		}

	}
}
