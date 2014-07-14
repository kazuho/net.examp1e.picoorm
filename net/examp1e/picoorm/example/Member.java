package net.examp1e.picoorm.example;

import net.examp1e.picoorm.*;
import net.examp1e.picoorm.types.*;

// this class definition should be automatically generated from the DDL
public class Member extends AbstractRow<Member> {
	
	// serdes
	public static final TableDefinition<Member> TABLE_DEFINITION = new TableDefinition<Member>("member") {
		@Override
		public Member createRow() {
			return new Member();
		}
		@Override
		public AnyType[] getColumns(Member row) {
			return new AnyType[]{ row._id, row._name };
		}
	};
	protected TableDefinition<Member> _getTableDefinition() {
		return TABLE_DEFINITION;
	}

	public final static LongType.Predicate<Member> id = new LongType.Predicate<Member>().init(TABLE_DEFINITION, "id");
	private LongType _id = new LongType().init(0L);
	public Member setId(long id) {
		_id.set(id);
		return this;
	}
	public long getId() {
		return _id.get();
	}

	public final static StringType.Predicate<Member> name = new StringType.Predicate<Member>().init(TABLE_DEFINITION, "name");
	private StringType _name = new StringType().init("");
	public Member setName(String name) {
		_name.set(name);
		return this;
	}
	public String getName() {
		return _name.get();
	}
}
