package net.examp1e.picoorm;

public class LongPredicate<Row> {
	final TableDefinition<Row> tableDefinition;
	String fieldName;
	public LongPredicate(TableDefinition<Row> tableDefinition, String fieldName) {
		this.tableDefinition = tableDefinition;
		this.fieldName = fieldName;
	}
	public Condition<Row> is(long x) {
		Condition<Row> c = new Condition<Row>(this.tableDefinition, this.fieldName + "=?");
		c.params.add(Long.toString(x));
		return c;
	}
	public Condition<Row> lessThan(long x) {
		Condition<Row> c = new Condition<Row>(this.tableDefinition, this.fieldName + "<?");
		c.params.add(Long.toString(x));
		return c;
	}
}
