package net.examp1e.picoorm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Condition<Row extends AbstractRow> {

	static interface Parameter {
		public void bindTo(PreparedStatement ps, int parameterIndex) throws SQLException;
	}

	final TableDefinition<Row> tableDefinition;
	String term;
	ArrayList<Parameter> params = new ArrayList<Parameter>();
	ArrayList<String> orderBy = new ArrayList<String>();
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

	@SuppressWarnings("varargs")
	public Condition<Row> orderBy(Predicate<Row>... predicates) {
		for (Predicate<Row> predicate : predicates) {
			orderBy.add(predicate.fieldName + (predicate.orderIsAscending() ? " ASC" : " DESC"));
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

	public ArrayList<Row> search(Connection conn) throws SQLException {
		return tableDefinition.search(conn,  this);
	}

	public void update(Connection conn, Row changes) throws SQLException {
		tableDefinition.update(conn, this, changes);
	}
}
