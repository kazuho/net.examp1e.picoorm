package net.examp1e.picoorm;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StringPredicate<Row extends AbstractRow> extends Predicate<Row> {

	static class Parameter implements Condition.Parameter {
		String value;
		Parameter(String value) {
			this.value = value;
		}
		public void bindTo(PreparedStatement ps, int parameterIndex) throws SQLException {
			ps.setString(parameterIndex, value);
		}
	}

	public StringPredicate(TableDefinition<Row> tableDefinition, String fieldName) {
		super(tableDefinition, fieldName);
	}

	public Condition<Row> is(String x) {
		Condition<Row> c = new Condition<Row>(this.tableDefinition, this.fieldName + "=?");
		c.params.add(new Parameter(x));
		return c;
	}
}
