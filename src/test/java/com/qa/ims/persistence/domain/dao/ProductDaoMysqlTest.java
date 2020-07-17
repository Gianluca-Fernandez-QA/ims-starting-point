package com.qa.ims.persistence.domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.Ims;
import com.qa.ims.controller.CustomerController;
import com.qa.ims.persistence.dao.ProductsDaoMysql;
import com.qa.ims.persistence.domain.Product;
import com.qa.ims.services.CustomerServices;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoMysqlTest {
	/**
	 * The thing I want to fake functionality for
	 */
	@Mock
	private CustomerServices customerServices;

	/**
	 * Spy is used because i want to mock some methods inside the item I'm testing
	 * InjectMocks uses dependency injection to insert the mock into the customer
	 * controller
	 */
	@Spy
	@InjectMocks
	private CustomerController customerController;

	public static final Logger LOGGER = Logger.getLogger(Ims.class);
	static String jdbcurl = "jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC";
	static String username = "root";
	static String password = "root";

//	@BeforeClass
//	public static void setup() {
//		try {
//			Connection connection = DriverManager.getConnection(jdbcurl, username, password);
//			Statement statement = connection.createStatement();
//			statement.executeUpdate("Drop database ims_test");
//
//		} catch (Exception e) {
//			LOGGER.debug(e.getStackTrace());
//			LOGGER.error(e.getMessage());
//		}
//	}

	@BeforeClass
	public static void aInit() {
		Ims ims = new Ims();
		ims.init("jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root",
				"src/test/resources/sql-schema.sql");
	}

	@Test
	public void bCreateTest() {
		ProductsDaoMysql productDaoMysql = new ProductsDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		String product_name = "Vinesh goes Postal";
		float price = 10.23f;
		Product product = new Product(product_name, price);

		String product_name2 = "Allan goes Postal";
		float price2 = 13.23f;
		Product product2 = new Product(product_name2, price2);

		String product_name3 = "Johnathan goes Postal";
		float price3 = 19.99f;
		Long id3 = 3l;
		Product product3 = new Product(product_name3, price3);

		assertEquals(product, productDaoMysql.create(product));
		assertEquals(product2, productDaoMysql.create(product2));
		assertEquals(product3, productDaoMysql.create(product3));
	}

	@Test
	public void cReadAllTest() {
		ProductsDaoMysql productdao = new ProductsDaoMysql("jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC",
				"root", "root");
		List<Product> customers = new ArrayList<>();
		customers.add(new Product("Vinesh goes Postal", 10.23f));
		customers.add(new Product("Allan goes Postal", 13.23f));
		customers.add(new Product("Johnathan goes Postal", 19.99f));

		assertEquals(customers, productdao.readAll());
	}

//
	@Test
	public void dReadLatestTest() {
		ProductsDaoMysql productDaoMysql = new ProductsDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Product product = new Product("Johnathan goes Postal", 19.99f);
		assertEquals(product, productDaoMysql.readLatest());
	}

//
	@Test
	public void eReadCustomerTest() {
		ProductsDaoMysql productDaoMysql = new ProductsDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Product product = new Product("Allan goes Postal", 13.23f);
		System.out.println(productDaoMysql.readProduct(2L));
		assertEquals(product, productDaoMysql.readProduct(2L));
	}

////
////	/**
////	 * 
////	 */
	@Test
	public void fUpdateTest() {
		ProductsDaoMysql productDaoMysql = new ProductsDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		String firstName = "Vinesh goes Clubbing";
		float surname = 31.23f;
		Product product = new Product((1l), firstName, surname);
		System.out.println(product);
		System.out.println(productDaoMysql.readProduct(1L));
		assertNotEquals(product, productDaoMysql.update(product));
	}

//	/**
//	 * makes sure that after you delete, the entry is no longer in the database.
//	 */
	@Test
	public void gDeleteTest() {
		ProductsDaoMysql a = new ProductsDaoMysql("jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root",
				"root");
		String id = "3";
		a.delete(Long.parseLong(id));
		List<Product> customers = new ArrayList<>();
		customers.add(new Product("Johnathan goes Postal", 19.99f));
		System.out.println(customers.toString());
		assertNotEquals(customers, a.readAll());
	}

	@Test(expected = Exception.class)
	public void readAllErrorTest() throws Exception {
		ProductsDaoMysql productdao = new ProductsDaoMysql("jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC",
				"roo", "root");
		productdao.readAll();
	}

	@Test(expected = Exception.class)
	public void CreateAllErrorTest() throws Exception {
		ProductsDaoMysql productdao = new ProductsDaoMysql("jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC",
				"roo", "root");
		String product_name2 = "Allan goes Postal";
		float price2 = 13.23f;
		Product product2 = new Product(product_name2, price2);
		productdao.create(product2);
	}

	@Test(expected = Exception.class)
	public void readErrorTest() throws Exception {
		ProductsDaoMysql productDaoMysql = new ProductsDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "roo", "root");
		Product product = new Product("Johnathan goes Postal", 19.99f);
	}

	@Test(expected = Exception.class)
	public void readlatestErrorTest() throws Exception {
		ProductsDaoMysql productDaoMysql = new ProductsDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "roo", "root");
		Product product = new Product("Johnathan goes Postal", 19.99f);
	}

	@AfterClass
	public static void cleanDB() {

		try (Connection connection = DriverManager.getConnection(jdbcurl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("drop table basket");
			statement.executeUpdate("drop table customers");

		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

}