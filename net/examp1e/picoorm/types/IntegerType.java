package net.examp1e.picoorm.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import net.examp1e.picoorm.*;

public class IntegerType extends AnyTypeImpl<IntegerType, Integer> {

	@Override
	public void bind(PreparedStatement ps, int parameterIndex) throws SQLException {
		if (value != null)
			ps.setInt(parameterIndex, value);
		else
			ps.setNull(parameterIndex, Types.INTEGER);
	}

	@Override
	public void unbind(ResultSet rs, int parameterIndex) throws SQLException {
		value = rs.getInt(parameterIndex);
		if (rs.wasNull())
			value = null;
	}

	public static class Predicate<Row extends AbstractRow<Row>> extends AnyTypeImpl.Predicate<Predicate<Row>, Row, Integer> {
		@Override
		protected IntegerType createParameter(Integer value) {
			return new IntegerType().init(value);
		}
	}

}
