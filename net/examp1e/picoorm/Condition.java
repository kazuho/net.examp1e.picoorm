package net.examp1e.picoorm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Condition<Row extends AbstractRow> {
	final TableDefinition<Row> tableDefinition;
	String term;
	ArrayList<String> params = new ArrayList<String>();
	long limitOffset = 0;
	long limitCount = -1;

	Condition(TableDefinition<Row> tableDefinition, String term) {
		this.tableDefinition = tableDefinition;
		this.term = term;
	}

	public Condition<Row> and(Condition<Row> x) {
		term = "(" + term + ") AND (" + x.term + ")";
		params.addAll(x.params);
		return this;
	}

	public Condition<Row> or(Condition<Row> x) {
		term = "(" + term + ") OR (" + x.term + ")";
		params.addAll(x.params);
		return this;
	}

	public Condition<Row> limit(long count) {
		return this.limit(-1, count);
	}

	public Condition<Row> limit(long offset, long count) {
		this.limitOffset = offset;
		this.limitCount = count;
		return this;
	}

	public ArrayList<Row> search(Connection conn) throws SQLException {
		return tableDefinition.search(conn,  this);
	}

	public void update(Connection conn, Row changes) throws SQLException {
		tableDefinition.update(conn, this, changes);
	}
}
