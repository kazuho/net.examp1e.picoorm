package net.examp1e.picoorm.types;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import net.examp1e.picoorm.*;

public class StringType extends AbstractType<StringType, String> {

	final static Binder<String> BINDER = new Binder<String>() {
		@Override
		public void bind(PreparedStatement ps, int parameterIndex, String value) throws SQLException {
			if (value != null)
				ps.setString(parameterIndex, value);
			else
				ps.setNull(parameterIndex, Types.VARCHAR);
		}
	};

	@Override
	protected Binder<String> getBinder() {
		return BINDER;
	}

	public static class Predicate<Row extends AbstractRow> extends AbstractType.Predicate<Predicate<Row>, Row, String> {
		public Condition<Row> like(String x) {
			return _buildBinaryOp(" LIKE ", x);
		}
		@Override
		protected Parameter<String> createParameter(String value) {
			return new Parameter<String>(value) {
				protected Binder<String> getBinder() {
					return BINDER;
				}
			};
		}
	}

}
