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
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.Ims;
import com.qa.ims.persistence.dao.CustomerDaoMysql;
import com.qa.ims.persistence.domain.Customer;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerDaoMysqlTest {
	/**
	 * The thing I want to fake functionality for
	 */
//	@Mock
//	private CustomerServices customerServices;

	/**
	 * Spy is used because i want to mock some methods inside the item I'm testing
	 * InjectMocks uses dependency injection to insert the mock into the customer
	 * controller
	 */
//	@Spy
//	@InjectMocks
//	private CustomerController customerController;

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
		CustomerDaoMysql customerDaoMysql = new CustomerDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		String firstName = "Vinesh";
		String surname = "Ghela";
		Long id = 1l;
		Customer customer = new Customer(firstName, surname);
		Customer customer1 = new Customer(id, firstName, surname);
		String firstName2 = "James";
		String surname2 = "Peach";
		Long id2 = 2l;
		Customer customer2 = new Customer(firstName2, surname2);
		Customer customer21 = new Customer(id2, firstName2, surname2);
		String firstName3 = "Bob";
		String surname3 = "Perry";
		Long id3 = 3l;
		Customer customer3 = new Customer(firstName3, surname3);
		Customer customer31 = new Customer(id3, firstName3, surname3);

		String test = (customer1.toString());
		String test1 = (customerDaoMysql.create(customer).toString());
		assertEquals(test, test1);
		assertEquals(customer21, customerDaoMysql.create(customer2));
		assertEquals(customer31, customerDaoMysql.create(customer3));
	}

	@Test
	public void cReadAllTest() {
		CustomerDaoMysql customerDaoMysql = new CustomerDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(1L, "Vinesh", "Ghela"));
		customers.add(new Customer(2L, "James", "Peach"));
		customers.add(new Customer(3L, "Bob", "Perry"));

		assertEquals(customers, customerDaoMysql.readAll());
	}

	@Test
	public void dReadLatestTest() {
		CustomerDaoMysql customerDaoMysql = new CustomerDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Customer customer = new Customer(3L, "Bob", "Perry");
		assertEquals(customer, customerDaoMysql.readLatest());
	}

	@Test
	public void eReadCustomerTest() {
		CustomerDaoMysql customerDaoMysql = new CustomerDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Customer customer = new Customer(2L, "James", "Peach");
		System.out.println(customerDaoMysql.readCustomer(2L));
		assertEquals(customer, customerDaoMysql.readCustomer(2L));
	}

//
//	/**
//	 * 
//	 */
	@Test
	public void fUpdateTest() {
		CustomerDaoMysql customerDaoMysql = new CustomerDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Long id = 1L;
		String firstName = "Vinesh";
		String surname = "Ghela";
		Customer customer = new Customer((id), firstName, surname);
		assertEquals(customer, customerDaoMysql.update(customer));
	}

//	/**
//	 * makes sure that after you delete, the entry is no longer in the database.
//	 */
	@Test
	public void gDeleteTest() {
		CustomerDaoMysql customerDaoMysql = new CustomerDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		String id = "3";
		customerDaoMysql.delete(Long.parseLong(id));
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(3L, "Bob", "Perry"));
		System.out.println(customers.toString());
		assertNotEquals(customers, customerDaoMysql.readAll());
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