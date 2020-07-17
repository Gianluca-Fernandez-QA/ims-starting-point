package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
		OrdersDaoMySql.jdbcConnectionUrl = "jdbc:mysql://" + Utils.MYSQL_URL + "/ims?serverTimezone=UTC";
		OrdersDaoMySql.username = username;
		OrdersDaoMySql.password = password;
	}

	public OrdersDaoMySql(String jdbcConnectionUrl, String username, String password) {
		this.jdbcConnectionUrl = jdbcConnectionUrl;
		this.username = username;
		this.password = password;
	}

	public static boolean UserChecker(long Id) {

		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("select id from customers");) {
			while (resultSet.next()) {
				if (Id == resultSet.getInt("id")) {
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
				ResultSet resultSet = statement.executeQuery("select fk_cust_id, orRef from Basket");) {
			resultSet.last();

			if (resultSet.getLong("fk_cust_id") == custId) {
				return resultSet.getLong("orRef");
			} else
				return resultSet.getLong("orRef") + 1;

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
					ResultSet resultSet = statement
							.executeQuery("SELECT * FROM Basket ORDER BY Order_id DESC LIMIT 1");) {
				resultSet.next();
				return orderFromResultSet0(resultSet);

			} catch (Exception e) {
//           Disabling to make user interface cleaner
				LOGGER.debug(e.getStackTrace());
				LOGGER.error(e.getMessage());

			}
		}
	}

	static Order orderFromResultSet0(ResultSet resultSet) throws SQLException {
		long Product_Id = resultSet.getInt("fk_product_id");
		long Customer_id = resultSet.getInt("fk_cust_id");
		long Order = resultSet.getInt("orRef");
		return new Order(Product_Id, Customer_id, Order);
	}

	static Order orderFromResultSet1(ResultSet resultSet) throws SQLException {
		String first_name = resultSet.getString("first_name");
		String surname = resultSet.getString("surname");
		Long OrderRef = resultSet.getLong("orRef");
		String Product_name = resultSet.getString("Product_name");
		float Price = resultSet.getFloat("Price");
		return new Order(first_name, surname, OrderRef, Product_name, Price);
	}

	@Override

	public void delete(long id) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("delete from Basket where orRef = " + id);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		} finally {
			LOGGER.info("Order deleted");
		}
	}

	@Override
	public List<Order> readAll() {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(
						"select first_name, surname, orRef, Product_name, Price FROM Basket B INNER JOIN customers C ON C.id=B.fk_cust_id INNER JOIN products P ON B.fk_product_id=P.Product_id;");) {
			ArrayList<Order> order = new ArrayList<>();
//			resultSet.next();
			while (resultSet.next()) {
				order.add(orderFromResultSet1(resultSet));

			}

			return order;
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;

	}

	@Override
	public Order create(Order order) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("insert into Basket(fk_product_id, fk_cust_id, orRef) values('"
					+ order.getProduct_Id() + "','" + order.getCustomer_id() + "','" + order.getOrder() + "')");

		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		LOGGER.info("Order Created");
		return readLatest();
	}

	@Override
	public Order update(Order order) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("update Basket set fk_product_id ='" + Order.getProduct_Id() + "' where Order_id ="
					+ Order.getOrder_id());
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}
}
