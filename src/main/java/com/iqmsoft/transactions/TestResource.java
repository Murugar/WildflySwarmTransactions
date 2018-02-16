package com.iqmsoft.transactions;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


import javax.transaction.UserTransaction;

import javax.naming.InitialContext;

@Path("/")

public class TestResource {
	@GET
	@Produces("text/plain")
	public String init() throws Exception {
		return "Active";
	}

	@Path("begincommit")
	@GET
	@Produces("text/plain")
	public String beginCommit() throws Exception {
		UserTransaction txn = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		String value = "Transaction ";

		try {
			txn.begin();

			value += "begun ok";

			try {
				txn.commit();

				value += " and committed ok";
			} catch (final Throwable ex) {
				value += " but failed to commit";
			}
		} catch (final Throwable ex) {
			value += "failed to begin: " + ex.toString();
		}

		return value;
	}

	@Path("beginrollback")
	@GET
	@Produces("text/plain")
	public String beginRollback() throws Exception {
		UserTransaction txn = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		String value = "Transaction ";

		try {
			txn.begin();

			value += "begun ok";

			try {
				txn.rollback();

				value += " and rolled back ok";
			} catch (final Throwable ex) {
				value += " but failed to rollback " + ex.toString();
			}
		} catch (final Throwable ex) {
			value += "failed to begin: " + ex.toString();
		}

		return value;
	}

	@Path("nested")
	@GET
	@Produces("text/plain")
	public String nested() throws Exception {
		UserTransaction txn = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		String value = "Nested transaction ";

		try {
			txn.begin();
			txn.begin();

			value += "support appears to be enabled!";

			try {
				txn.commit();
				txn.commit();
			} catch (final Throwable ex) {
			}
		} catch (final Throwable ex) {
			value += "support is not enabled!";

			try {
				txn.commit();
			} catch (Exception e) {
				value += "Nested Transaction Not Committed!";
			}
		}

		return value;
	}
}
