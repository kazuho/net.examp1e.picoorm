package net.examp1e.picoorm;

public class StringPredicate<Row extends AbstractRow> extends Predicate<Row> {
	public StringPredicate(TableDefinition<Row> tableDefinition, String fieldName) {
		super(tableDefinition, fieldName);
	}
	public Condition<Row> is(String x) {
		Condition<Row> c = new Condition<Row>(this.tableDefinition, this.fieldName + "=?");
		c.params.add(x);
		return c;
	}
}
