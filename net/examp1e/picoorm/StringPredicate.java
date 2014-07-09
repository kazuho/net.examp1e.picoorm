package net.examp1e.picoorm;

public class StringPredicate<Row extends AbstractRow, RowOrder> {
	final TableDefinition<Row, RowOrder> tableDefinition;
	String fieldName;
	public StringPredicate(TableDefinition<Row, RowOrder> tableDefinition, String fieldName) {
		this.tableDefinition = tableDefinition;
		this.fieldName = fieldName;
	}
	public Condition<Row, RowOrder> is(String x) {
		Condition<Row, RowOrder> c = new Condition<Row, RowOrder>(this.tableDefinition, this.fieldName + "=?");
		c.params.add(x);
		return c;
	}
}
