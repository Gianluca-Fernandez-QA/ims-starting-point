package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

public class OrdersDaoMySql implements Dao<Order> {
	public static final Logger LOGGER = Logger.getLogger(CustomerDaoMysql.class);

	private static String jdbcConnectionUrl;
	private static String username;
	private static String password;

	public OrdersDaoMySql(String username, String password) {
		this.jdbcConnectionUrl = "jdbc:mysql://" + Utils.MYSQL_URL + "/ims?serverTimezone=UTC";
		this.username = username;
		this.password = password;
	}

	public static boolean UserChecker(long Id) {

		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("select Customer_id from customer");) {
			while (resultSet.next()) {
				if (Id == resultSet.getInt("Customer_id")) {
					return true;
				} else {
					continue;
				}
			}
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	public static boolean prodChecker(long Id) {

		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("select Product_id from products");) {
			while (resultSet.next()) {
				if (Id == resultSet.getInt("Product_id")) {
					return true;
				} else {
					continue;
				}
			}
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	public static Long order(long custId) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("fk_customer_id, Order from order");) {
			if (!(resultSet.getLong("Order") == 0)) {
				resultSet.last();
				if (resultSet.getLong("fk_customer_id") == custId) {
					return resultSet.getLong("Order");
				} else
					return resultSet.getLong("Order") + 1;
			}
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return 0l;

	}

	public Order readLatest() {
		while (true) {
			try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT * FROM Order ORDER BY id DESC LIMIT 1");) {
				resultSet.next();
				return orderFromResultSet(resultSet);

			} catch (Exception e) {
//           Disabling to make user interface cleaner
				LOGGER.debug(e.getStackTrace());
				LOGGER.error(e.getMessage());

			}
		}
	}

	static Order orderFromResultSet(ResultSet resultSet) throws SQLException {
		Long Order_id = resultSet.getLong("Order_id");
		Long Product_Id = resultSet.getLong("Product_id");
		Long Customer_id = resultSet.getLong("Customer_id");
		Long OrderRef = resultSet.getLong("Order");
		return new Order(Product_Id, Customer_id, OrderRef);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Order> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order create(Order order) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("insert into Order(fk_product_id, fk_customer_id, Order) values('"
					+ order.getProduct_Id() + "','" + order.getCustomer_id() + "','" + order.getOrder() + "')");
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order update(Order t) {
		// TODO Auto-generated method stub
		return null;
	}
}
