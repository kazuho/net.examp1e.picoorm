package net.examp1e.picoorm;

public class StringPredicate<Row extends AbstractRow> {
	final TableDefinition<Row> tableDefinition;
	String fieldName;
	public StringPredicate(TableDefinition<Row> tableDefinition, String fieldName) {
		this.tableDefinition = tableDefinition;
		this.fieldName = fieldName;
	}
	public Condition<Row> is(String x) {
		Condition<Row> c = new Condition<Row>(this.tableDefinition, this.fieldName + "=?");
		c.params.add(x);
		return c;
	}
}
