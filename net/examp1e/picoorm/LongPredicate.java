package net.examp1e.picoorm;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LongPredicate<Row extends AbstractRow> extends Predicate<Row> {

	static class Parameter implements Condition.Parameter {
		long value;
		Parameter(long value) {
			this.value = value;
		}
		public void bindTo(PreparedStatement ps, int parameterIndex) throws SQLException {
			ps.setLong(parameterIndex, value);
		}
	}

	public LongPredicate(TableDefinition<Row> tableDefinition, String fieldName) {
		super(tableDefinition, fieldName);
	}

	public Condition<Row> is(long x) {
		return _buildBinaryOp("=", x);
	}

	public Condition<Row> lessThan(long x) {
		return _buildBinaryOp("<", x);
	}

	private Condition<Row> _buildBinaryOp(String op, long value) {
		Condition<Row> c = new Condition<Row>(this.tableDefinition, this.fieldName + op + "?");
		c.params.add(new Parameter(value));
		return c;
	}
}
