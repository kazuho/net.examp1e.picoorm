package net.examp1e.picoorm.types;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import net.examp1e.picoorm.*;

public class IntegerType extends AbstractType<IntegerType, Integer> {

	final static Binder<Integer> BINDER = new Binder<Integer>() {
		@Override
		public void bind(PreparedStatement ps, int parameterIndex, Integer value) throws SQLException {
			if (value != null)
				ps.setInt(parameterIndex, value);
			else
				ps.setNull(parameterIndex, Types.INTEGER);
		}
	};

	@Override
	protected Binder<Integer> getBinder() {
		return BINDER;
	}

	public static class Predicate<Row extends AbstractRow> extends AbstractType.Predicate<Predicate<Row>, Row, Integer> {
		@Override
		protected Parameter<Integer> createParameter(Integer value) {
			return new Parameter<Integer>(value) {
				protected Binder<Integer> getBinder() {
					return BINDER;
				}
			};
		}
	}

}
