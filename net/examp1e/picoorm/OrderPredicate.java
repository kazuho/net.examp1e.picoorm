package net.examp1e.picoorm;

public class OrderPredicate<Row extends AbstractRow, RowOrder> {

	Condition<Row, RowOrder> cond;
	String fieldName;

	public OrderPredicate(Condition<Row, RowOrder> cond, String fieldName) {
		this.cond = cond;
		this.fieldName = fieldName;
	}

	public Condition<Row, RowOrder> asc() {
		this.cond.orders.add(fieldName + " asc");
		return this.cond;
	}

	public Condition<Row, RowOrder> desc() {
		this.cond.orders.add(fieldName + " desc");
		return this.cond;
	}
}
