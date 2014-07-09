package net.examp1e.picoorm.types;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import net.examp1e.picoorm.*;

public class LongType extends AbstractType<LongType, Long> {

	final static Binder<Long> BINDER = new Binder<Long>() {
		@Override
		public void bind(PreparedStatement ps, int parameterIndex, Long value) throws SQLException {
			if (value != null)
				ps.setLong(parameterIndex, value);
			else
				ps.setNull(parameterIndex, Types.BIGINT);
		}
	};

	@Override
	protected Binder<Long> getBinder() {
		return BINDER;
	}

	public static class Predicate<Row extends AbstractRow> extends AbstractType.Predicate<Predicate<Row>, Row, Long> {
		@Override
		protected Parameter<Long> createParameter(Long value) {
			return new Parameter<Long>(value) {
				protected Binder<Long> getBinder() {
					return BINDER;
				}
			};
		}
	}

}
