package net.examp1e.picoorm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import net.examp1e.picoorm.types.AnyType;

public class Condition<Row extends AbstractRow<Row>> {

	public static abstract class OrderBy {
		public abstract String getFieldName();
	}

	public static interface OrderByIsDescending {
	}

	final TableDefinition<Row> tableDefinition;
	String term;
	ArrayList<AnyType> params = new ArrayList<AnyType>();
	ArrayList<String> orderBy = new ArrayList<String>();
	long limitOffset = 0;
	long limitCount = -1;

	public Condition(TableDefinition<Row> tableDefinition, String term, AnyType... params) {
		this.tableDefinition = tableDefinition;
		this.term = term;
		for (AnyType param : params) {
			this.params.add(param);
		}
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

	public Condition<Row> orderBy(OrderBy... orders) {
		for (OrderBy order : orders) {
			orderBy.add(order.getFieldName() + (order instanceof OrderByIsDescending ? " DESC" : " ASC"));
		}
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

	public ArrayList<Row> select(Connection conn) throws SQLException {
		return tableDefinition.select(conn,  this);
	}

	public void update(Connection conn, Row changes) throws SQLException {
		tableDefinition.update(conn, this, changes);
	}
}
