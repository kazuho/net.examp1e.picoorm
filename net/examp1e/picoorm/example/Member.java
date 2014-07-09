package net.examp1e.picoorm.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.examp1e.picoorm.*;

// this class definition should be automatically generated from the DDL
public class Member extends AbstractRow {

	public static class RowOrder {
		OrderPredicate<Member, RowOrder> id;
		OrderPredicate<Member, RowOrder> name;
		RowOrder(Condition<Member, RowOrder> cond) {
			id = new OrderPredicate<Member, RowOrder>(cond, "id");
			name = new OrderPredicate<Member, RowOrder>(cond, "order");
		}
	}

	// serdes
	public static final String TABLE_NAME = "member";
	static final TableDefinition<Member, RowOrder> TABLE_DEFINITION = new TableDefinition<Member, RowOrder>() {
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
		public RowOrder createRowOrder(Condition<Member, RowOrder> cond) {
			return new RowOrder(cond);
		}
	};
	protected TableDefinition<Member, RowOrder> _getTableDefinition() {
		return TABLE_DEFINITION;
	}

	// for query
	public final static LongPredicate<Member, RowOrder> id = new LongPredicate<Member, RowOrder>(TABLE_DEFINITION, "id");
	public final static StringPredicate<Member, RowOrder> name = new StringPredicate<Member, RowOrder>(TABLE_DEFINITION, "name");

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
