package net.examp1e.picoorm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public abstract class TableDefinition<Row extends AbstractRow> {

	public abstract String getTableName();
	
	public abstract ArrayList<String> getColumnNames(Row row);
	
	public abstract int bind(Row row, PreparedStatement ps, int index) throws SQLException;
	
	public abstract Row deserialize(ResultSet rs) throws SQLException;
	
	public ArrayList<Row> search(Connection conn, Condition<Row> cond) throws SQLException {
		String sql = "SELECT " + join(getColumnNames(null)) + " FROM " + getTableName() + _buildWhere(cond);
		PreparedStatement ps = conn.prepareStatement(sql);
		this._bindWhere(cond,  ps, 1);
		ResultSet rs = ps.executeQuery();
		ArrayList<Row> ret = new ArrayList<Row>();
		while (rs.next()) {
			ret.add(deserialize(rs));
		}
		return ret;
	}

	public void insert(Connection conn, Row row) throws SQLException {
		ArrayList<String> names = getColumnNames(row);

		String sql = "INSERT INTO " + getTableName() + " (" + join(names) + ") VALUES (";
		for (int i = 0; i != names.size(); ++i) {
			if (i != 0)
				sql += ",";
			sql += "?";
		}
		sql += ")";
		PreparedStatement ps = conn.prepareStatement(sql);
		bind(row, ps, 1);
		ps.execute();
	}

	public void update(Connection conn, Condition<Row> cond, Row changes) throws SQLException {
		ArrayList<String> columnsToChange = getColumnNames(changes);
		if (columnsToChange.size() == 0)
			return;

		String sql = "UPDATE " + getTableName() + " SET ";
		for (String column : columnsToChange) {
			sql += column + "=?";
		}
		sql += _buildWhere(cond);

		PreparedStatement ps = conn.prepareStatement(sql);
		int parameterIndex = 1;
		parameterIndex = bind(changes, ps, parameterIndex);
		parameterIndex = _bindWhere(cond, ps, parameterIndex);

		ps.execute();
	}

	private String _buildWhere(Condition<Row> cond) {
		String clause = " WHERE " + cond.term;
		if (cond.limitCount != -1) {
			clause += " LIMIT " + Long.toString(cond.limitOffset) + "," + Long.toString(cond.limitCount);
		}
		return clause;
	}

	private int _bindWhere(Condition<Row> cond, PreparedStatement ps, int index) throws SQLException {
		for (String value : cond.params) {
			ps.setString(index++, value);
		}
		return index;
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

	public static String join(ArrayList<String> list) {
		String s = "";
		for (String e : list) {
			if (s.length() != 0)
				s += ",";
			s += e;
		}
		return s;
	}
}
