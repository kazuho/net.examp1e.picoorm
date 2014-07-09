package net.examp1e.picoorm.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.examp1e.picoorm.*;

// this class definition should be automatically generated from the DDL
public class Member extends AbstractRow {
	
	// serdes
	public static final String TABLE_NAME = "member";
	static final TableDefinition<Member> TABLE_DEFINITION = new TableDefinition<Member>() {
		public String getTableName() {
			return TABLE_NAME;
		}
		public ArrayList<String> getColumnNames(Member row) {
			ArrayList<String> names = new ArrayList<String>();
			if (row == null || row._id_isset)
				names.add("id");
			if (row == null || row._name_isset)
				names.add("name");
			return names;
		}
		public int bind(Member row, PreparedStatement ps, int index) throws SQLException {
			if (row._id_isset)
				setLong(ps, index++, row._id_value);
			if (row._name_isset)
				setString(ps, index++, row._name_value);
			return index;
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
	Long _id_value;
	boolean _id_isset;
	String _name_value;
	boolean _name_isset;
	public Member setId(long id) {
		_id_value = id;
		_id_isset = true;
		return this;
	}
	public Long getId() {
		return _id_value;
	}
	public Member setName(String name) {
		_name_value = name;
		_name_isset = true;
		return this;
	}
	public String getName() {
		return _name_value;
	}

}
