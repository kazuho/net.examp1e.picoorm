package net.examp1e.picoorm.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.examp1e.picoorm.*;

public class Member extends AbstractRow {
	
	// serdes
	public static final String TABLE_NAME = "member";
	static final TableDefinition<Member> TABLE_DEFINITION = new TableDefinition<Member>() {
		public String getTableName() {
			return TABLE_NAME;
		}
		public String[] getColumnNames() {
			return new String[] { "id", "name" };
		}
		public void bind(Member row, PreparedStatement ps) throws SQLException {
			setLong(ps, 1, row._id);
			setString(ps, 2, row._name);
		}
		public Member deserialize(ResultSet rs) throws SQLException {
			return new Member()
				.setId(rs.getLong("id"))
				.setName(rs.getString("name"));
		}
	};
	protected TableDefinition<Member> _getTableDefinition() {
		return TABLE_DEFINITION;
	}

	// for query
	public final static LongPredicate<Member> id = new LongPredicate<Member>(TABLE_DEFINITION, "id");
	public final static StringPredicate<Member> name = new StringPredicate<Member>(TABLE_DEFINITION, "name");

	// for insert,update
	Long _id;
	String _name;
	public Member setId(long id) {
		_id = id;
		return this;
	}
	public long getId() {
		return _id;
	}
	public Member setName(String name) {
		_name = name;
		return this;
	}
	public String getName() {
		return _name;
	}

}
