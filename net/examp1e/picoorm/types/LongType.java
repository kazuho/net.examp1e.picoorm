package net.examp1e.picoorm.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import net.examp1e.picoorm.*;

public class LongType extends AnyTypeImpl<LongType, Long> {

	@Override
	public void bind(PreparedStatement ps, int parameterIndex) throws SQLException {
		if (value != null)
			ps.setLong(parameterIndex, value);
		else
			ps.setNull(parameterIndex, Types.BIGINT);
	}

	@Override
	public void unbind(ResultSet rs, int parameterIndex) throws SQLException {
		value = rs.getLong(parameterIndex);
		if (rs.wasNull())
			value = null;
	}

	public static class Predicate<Row extends AbstractRow<Row>> extends AnyTypeImpl.Predicate<Predicate<Row>, Row, Long> {
		@Override
		protected LongType createParameter(Long value) {
			return new LongType().init(value);
		}
	}

}
