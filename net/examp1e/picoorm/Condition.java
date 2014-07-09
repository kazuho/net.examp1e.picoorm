package net.examp1e.picoorm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Condition<Table> {
	final TableDefinition<Table> tableDefinition;
	String term;
	ArrayList<String> params = new ArrayList<String>();

	Condition(TableDefinition<Table> tableDefinition, String term) {
		this.tableDefinition = tableDefinition;
		this.term = term;
	}
	public Condition<Table> and(Condition<Table> x) {
		term = "(" + term + ") AND (" + x.term + ")";
		params.addAll(x.params);
		return this;
	}
	public Condition<Table> or(Condition<Table> x) {
		term = "(" + term + ") OR (" + x.term + ")";
		params.addAll(x.params);
		return this;
	}
	public ArrayList<Table> search(Connection conn) throws SQLException {
		return tableDefinition.search(conn,  this);
	}

	String getTerm() {
		return term;
	}
	ArrayList<String> getParams() {
		return params;
	}
}
