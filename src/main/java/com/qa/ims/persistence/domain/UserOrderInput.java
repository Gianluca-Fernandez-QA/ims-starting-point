package com.qa.ims.persistence.domain;

import org.apache.log4j.Logger;

import com.qa.ims.utils.Utils;

public class UserOrderInput {
	public static final Logger LOGGER = Logger.getLogger(UserOrderInput.class);

	public static int input() {
		int cust_id;
		while (true) {
			LOGGER.info("Please Enter Customer Id: ");
			try {
				cust_id = Integer.parseInt(Utils.getInput());
				break;
			} catch (IllegalArgumentException e) {
				LOGGER.error("Invalid selection please try again");
			}
		}
		return cust_id;
	}
}
