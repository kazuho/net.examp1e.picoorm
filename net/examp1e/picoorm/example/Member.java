package net.examp1e.picoorm.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import net.examp1e.picoorm.*;
import net.examp1e.picoorm.types.*;

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
			if (row == null || row._id.isSet())
				names.add("id");
			if (row == null || row._name.isSet())
				names.add("name");
			return names;
		}
		public int bind(Member row, PreparedStatement ps, int index) throws SQLException {
			index = row._id.bindTo(ps, index);
			index = row._name.bindTo(ps, index);
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
	public final static LongType.Predicate<Member> id = new LongType.Predicate<Member>().init(TABLE_DEFINITION, "id");
	public final static StringType.Predicate<Member> name = new StringType.Predicate<Member>().init(TABLE_DEFINITION, "name");

	// for insert,update
	LongType _id = new LongType().init(0L);
	StringType _name = new StringType().init("");
	public Member setId(long id) {
		_id.set(id);
		return this;
	}
	public long getId() {
		return _id.get();
	}
	public Member setName(String name) {
		_name.set(name);
		return this;
	}
	public String getName() {
		return _name.get();
	}

}
