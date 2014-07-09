package net.examp1e.picoorm.types;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.examp1e.picoorm.*;

public abstract class AbstractType<ThisType, ValueType> {

	public static interface Binder<T> {
		void bind(PreparedStatement ps, int parameterIndex, T value) throws SQLException;
	}

	ValueType value;
	boolean isSet;

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

	@SuppressWarnings("unchecked")
	public ThisType init(ValueType defaultValue) {
		this.value = defaultValue;
		return (ThisType)this;
	}

	public int bindTo(PreparedStatement ps, int parameterIndex) throws SQLException {
		if (isSet())
			getBinder().bind(ps, parameterIndex, value);
		return parameterIndex;
	}

	protected abstract Binder<ValueType> getBinder();

	static abstract class Parameter<ValueType> implements Condition.Parameter {
		ValueType value;
		Parameter(ValueType value) {
			this.value = value;
		}
		public void bindTo(PreparedStatement ps, int parameterIndex) throws SQLException {
			getBinder().bind(ps, parameterIndex, value);
		}
		protected abstract Binder<ValueType> getBinder();
	}

	public static abstract class Predicate<ThisType, Row extends AbstractRow, ValueType> extends net.examp1e.picoorm.Predicate<Row> {
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
		protected abstract Parameter<ValueType> createParameter(ValueType x);
	}

}
