package net.examp1e.picoorm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.examp1e.picoorm.types.AnyType;

public abstract class TableDefinition<Row extends AbstractRow<Row>> {

	public final String TABLE_NAME;
	private ArrayList<AnyType.Predicate<Row>> columnDefinitions = new ArrayList<AnyType.Predicate<Row>>();

	protected TableDefinition(String tableName) {
		TABLE_NAME = tableName;
	}
	
	public abstract Row createRow();

	public abstract AnyType[] getColumns(Row row);

	public ArrayList<Row> select(Connection conn, Condition<Row> cond) throws SQLException {
		String sql = "SELECT " + _join(_getColumnNames(null)) + " FROM " + TABLE_NAME + _buildWhere(cond);
		PreparedStatement ps = conn.prepareStatement(sql);
		this._bindWhere(cond,  ps, 1);
		ResultSet rs = ps.executeQuery();
		ArrayList<Row> ret = new ArrayList<Row>();
		while (rs.next()) {
			Row row = createRow();
			AnyType[] columns = getColumns(row);
			for (int i = 0; i != columns.length; ++i) {
				columns[i].unbind(rs,  i + 1);
			}
			ret.add(row);
		}
		return ret;
	}

	public void addColumnDefinition(AnyType.Predicate<Row> columnDefinition) {
		this.columnDefinitions.add(columnDefinition);
	}

	public void insert(Connection conn, Row row) throws SQLException {
		ArrayList<String> names = _getColumnNames(row);

		String sql = "INSERT INTO " + TABLE_NAME + " (" + _join(names) + ") VALUES (";
		for (int i = 0; i != names.size(); ++i) {
			if (i != 0)
				sql += ",";
			sql += "?";
		}
		sql += ")";
		PreparedStatement ps = conn.prepareStatement(sql);
		_bindColumnsBeingSet(row, ps, 1);
		ps.execute();
	}

	public void update(Connection conn, Condition<Row> cond, Row changes) throws SQLException {
		ArrayList<String> columnsToChange = _getColumnNames(changes);
		if (columnsToChange.size() == 0)
			return;

		String sql = "UPDATE " + TABLE_NAME + " SET ";
		for (String column : columnsToChange) {
			sql += column + "=?";
		}
		sql += _buildWhere(cond);

		PreparedStatement ps = conn.prepareStatement(sql);
		int parameterIndex = 1;
		parameterIndex = _bindColumnsBeingSet(changes, ps, parameterIndex);
		parameterIndex = _bindWhere(cond, ps, parameterIndex);

		ps.execute();
	}

	public void delete(Connection conn, Condition<Row> cond) throws SQLException {
		String sql = "DELETE FROM " + TABLE_NAME + _buildWhere(cond);
		PreparedStatement ps = conn.prepareStatement(sql);
		_bindWhere(cond, ps, 1);
		ps.execute();
	}

	private ArrayList<String> _getColumnNames(Row row) {
		ArrayList<String> columnNames = new ArrayList<String>();
		if (row == null) {
			for (AnyType.Predicate<Row> c : columnDefinitions)
				columnNames.add(c.getFieldName());
		} else {
			// only name the columns that are being set
			AnyType[] columns = getColumns(row);
			int columnIndex = 0;
			for (AnyType.Predicate<Row> c : columnDefinitions) {
				if (columns[columnIndex++].isSet())
					columnNames.add(c.getFieldName());
			}
		}
		return columnNames;
	}

	private int _bindColumnsBeingSet(Row row, PreparedStatement ps, int parameterIndex) throws SQLException {
		AnyType[] columns = getColumns(row);
		for (AnyType column : columns) {
			if (column.isSet())
				column.bind(ps, parameterIndex++);
		}
		return parameterIndex;
	}

	private String _buildWhere(Condition<Row> cond) {
		String clause = " WHERE " + cond.term;
		if (cond.orderBy.size() != 0) {
			clause += " ORDER BY " + _join(cond.orderBy);
		}
		if (cond.limitCount != -1) {
			clause += " LIMIT " + Long.toString(cond.limitOffset) + "," + Long.toString(cond.limitCount);
		}
		return clause;
	}

	private int _bindWhere(Condition<Row> cond, PreparedStatement ps, int index) throws SQLException {
		for (AnyType p : cond.params) {
			p.bind(ps,  index++);
		}
		return index;
	}

	private static String _join(ArrayList<String> list) {
		String s = "";
		for (String e : list) {
			if (s.length() != 0)
				s += ",";
			s += e;
		}
		return s;
	}
}
