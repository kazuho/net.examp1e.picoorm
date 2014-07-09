package net.examp1e.picoorm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class TableDefinition<Row> {

	public abstract String getTableName();
	
	public abstract String[] getColumnNames();
	
	public abstract void bind(Row row, PreparedStatement ps) throws SQLException;
	
	public abstract Row deserialize(ResultSet rs) throws SQLException;
	
	public ArrayList<Row> search(Connection conn, Condition<Row> cond) throws SQLException {
		// prepare the statement
		PreparedStatement ps = conn.prepareStatement("SELECT " + join(getColumnNames()) + " FROM " + getTableName() + " WHERE " + cond.getTerm());
		// bind params
		int parameterIndex = 1;
		Iterator<String> paramsIter = cond.getParams().iterator();
		while (paramsIter.hasNext()) {
			ps.setString(parameterIndex++, paramsIter.next());
		}
		// execute query
		ResultSet rs = ps.executeQuery();
		ArrayList<Row> ret = new ArrayList<Row>();
		while (rs.next()) {
			ret.add(deserialize(rs));
		}
		return ret;
	}

	public void insert(Connection conn, Row row) throws SQLException {
		String[] names = getColumnNames();

		String sql = "INSERT INTO " + getTableName() + " (" + join(names) + ") VALUES (";
		for (int i = 0; i != names.length; ++i) {
			if (i != 0)
				sql += ",";
			sql += "?";
		}
		sql += ")";
		PreparedStatement ps = conn.prepareStatement(sql);
		bind(row, ps);
		ps.execute();
	}

	public static void setLong(PreparedStatement ps, int parameterIndex, Long value) throws SQLException {
		if (value != null) {
			ps.setLong(parameterIndex, value);
		} else {
			ps.setNull(parameterIndex, Types.BIGINT);
		}
	}

	public static void setString(PreparedStatement ps, int parameterIndex, String value) throws SQLException {
		if (value != null) {
			ps.setString(parameterIndex, value);
		} else {
			ps.setNull(parameterIndex, Types.VARCHAR);
		}
	}

	public static String join(String[] list) {
		String s = "";
		for (int i = 0; i != list.length; ++i) {
			if (i != 0)
				s += ",";
			s += list[i];
		}
		return s;
	}
}
