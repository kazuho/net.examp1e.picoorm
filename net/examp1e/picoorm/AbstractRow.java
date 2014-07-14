package net.examp1e.picoorm;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractRow<Row extends AbstractRow<Row>> {

	@SuppressWarnings("unchecked")
	public void insert(Connection conn) throws SQLException {
		_getTableDefinition().insert(conn, (Row)this);
	}

	protected abstract TableDefinition<Row> _getTableDefinition();
}
