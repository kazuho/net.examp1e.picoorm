package net.examp1e.picoorm;

public class LongPredicate<Row extends AbstractRow, RowOrder> {
	final TableDefinition<Row, RowOrder> tableDefinition;
	String fieldName;
	public LongPredicate(TableDefinition<Row, RowOrder> tableDefinition, String fieldName) {
		this.tableDefinition = tableDefinition;
		this.fieldName = fieldName;
	}
	public Condition<Row, RowOrder> is(long x) {
		Condition<Row, RowOrder> c = new Condition<Row, RowOrder>(this.tableDefinition, this.fieldName + "=?");
		c.params.add(Long.toString(x));
		return c;
	}
	public Condition<Row, RowOrder> lessThan(long x) {
		Condition<Row, RowOrder> c = new Condition<Row, RowOrder>(this.tableDefinition, this.fieldName + "<?");
		c.params.add(Long.toString(x));
		return c;
	}
}
