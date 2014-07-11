package net.examp1e.picoorm.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.examp1e.picoorm.*;

public abstract class AnyType {
	
	public abstract boolean isSet();

	public abstract void bind(PreparedStatement ps, int parameterIndex) throws SQLException;

	public abstract void unbind(ResultSet rs, int parameterIndex) throws SQLException;

	public static abstract class Predicate<Row extends AbstractRow> extends Condition.OrderBy {

		public static class OrderPredicate<Row extends AbstractRow> extends Condition.OrderBy {
			Predicate<Row> predicate;
			boolean isAsc;
			OrderPredicate(Predicate<Row> predicate, boolean isAsc) {
				this.predicate = predicate;
				this.isAsc = isAsc;
			}
			@Override
			public String toOrderBySQL() {
				return this.predicate.fieldName + (isAsc ? " ASC" : " DESC");
			}
		}

		protected TableDefinition<Row> tableDefinition;
		protected String fieldName;
		public final OrderPredicate<Row> asc = new OrderPredicate<Row>(this, true);
		public final OrderPredicate<Row> desc = new OrderPredicate<Row>(this, false);

		protected void _init(TableDefinition<Row> tableDefinition, String fieldName) {
			this.tableDefinition = tableDefinition;
			this.fieldName = fieldName;
			tableDefinition.addColumnDefinition(this);
		}

		public String getFieldName() {
			return fieldName;
		}

		@Override
		public String toOrderBySQL() {
			return this.asc.toOrderBySQL();
		}
	}

}

abstract class AnyTypeImpl<ThisType, ValueType> extends AnyType {

	boolean isSet = false;
	ValueType value;

	@SuppressWarnings("unchecked")
	public ThisType init(ValueType defaultValue) {
		this.value = defaultValue;
		return (ThisType)this;
	}

	@Override
	public boolean isSet() {
		return isSet;
	}

	public ValueType get() {
		return value;
	}

	public void set(ValueType value) {
		this.value = value;
		this.isSet = true;
	}

	public static abstract class Predicate<ThisType, Row extends AbstractRow, ValueType> extends AnyType.Predicate<Row> {
		@SuppressWarnings("unchecked")
		public ThisType init(TableDefinition<Row> tableDefinition, String fieldName) {
			_init(tableDefinition, fieldName);
			return (ThisType)this;
		}
		public Condition<Row> is(ValueType x) {
			return _buildBinaryOp("=", x);
		}
		public Condition<Row> lessThan(ValueType x) {
			return _buildBinaryOp("<", x);
		}
		public Condition<Row> greaterThan(ValueType x) {
			return _buildBinaryOp(">", x);
		}
		public Condition<Row> lessThanOrEqualTo(ValueType x) {
			return _buildBinaryOp("<=", x);
		}
		public Condition<Row> greaterThanOrEqualTo(ValueType x) {
			return _buildBinaryOp(">=", x);
		}
		public Condition<Row> between(ValueType x, ValueType y) {
			return new Condition<Row>(this.tableDefinition, this.fieldName + " BETWEEN ? AND ?", createParameter(x), createParameter(y));
		}
		protected Condition<Row> _buildBinaryOp(String op, ValueType value) {
			return new Condition<Row>(this.tableDefinition, this.fieldName + op + "?", createParameter(value));
		}
		protected abstract AnyType createParameter(ValueType x);
	}

}
