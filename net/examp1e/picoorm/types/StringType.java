package net.examp1e.picoorm.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import net.examp1e.picoorm.*;

public class StringType extends AnyTypeImpl<StringType, String> {

	@Override
	public void bind(PreparedStatement ps, int parameterIndex) throws SQLException {
		if (value != null)
			ps.setString(parameterIndex, value);
		else
			ps.setNull(parameterIndex, Types.VARCHAR);
	}

	@Override
	public void unbind(ResultSet rs, int parameterIndex) throws SQLException {
		value = rs.getString(parameterIndex);
	}

	public static class Predicate<Row extends AbstractRow> extends AnyTypeImpl.Predicate<Predicate<Row>, Row, String> {
		@Override
		protected StringType createParameter(String value) {
			return new StringType().init(value);
		}
	}

}
