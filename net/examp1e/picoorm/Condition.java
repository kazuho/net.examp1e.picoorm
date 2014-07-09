package net.examp1e.picoorm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Condition<Row extends AbstractRow, RowOrder> {
	final TableDefinition<Row, RowOrder> tableDefinition;
	String term;
	ArrayList<String> params = new ArrayList<String>();
	ArrayList<String> orders = new ArrayList<String>();
	long limitOffset = 0;
	long limitCount = -1;

	public RowOrder orderBy;

	Condition(TableDefinition<Row, RowOrder> tableDefinition, String term) {
		this.tableDefinition = tableDefinition;
		this.term = term;
		orderBy = tableDefinition.createRowOrder(this); 
	}

	public Condition<Row, RowOrder> and(Condition<Row, RowOrder> x) {
		term = "(" + term + ") AND (" + x.term + ")";
		params.addAll(x.params);
		return this;
	}

	public Condition<Row, RowOrder> or(Condition<Row, RowOrder> x) {
		term = "(" + term + ") OR (" + x.term + ")";
		params.addAll(x.params);
		return this;
	}

	public Condition<Row, RowOrder> limit(long count) {
		return this.limit(-1, count);
	}

	public Condition<Row, RowOrder> limit(long offset, long count) {
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
