package com.qa.ims.utils;

import java.util.Scanner;

public class Utils {

	public static final String MYSQL_URL = "34.89.68.136";
	// public static final String MYSQL_URL = "localhost:3306";
	public static final Scanner SCANNER = new Scanner(System.in);

	public static String getInput() {
		return SCANNER.nextLine();
	}

}
