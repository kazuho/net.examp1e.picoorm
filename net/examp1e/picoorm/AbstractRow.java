package net.examp1e.picoorm;

import java.sql.Connection;
import java.sql.SQLException;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractRow {

	public void insert(Connection conn) throws SQLException {
		_getTableDefinition().insert(conn, this);
	}

	protected abstract TableDefinition _getTableDefinition();
}
