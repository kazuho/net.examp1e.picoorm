package net.examp1e.picoorm;

public class StringPredicate<Table> {
	final TableDefinition<Table> tableDefinition;
	String fieldName;
	public StringPredicate(TableDefinition<Table> tableDefinition, String fieldName) {
		this.tableDefinition = tableDefinition;
		this.fieldName = fieldName;
	}
	public Condition<Table> is(String x) {
		Condition<Table> c = new Condition<Table>(this.tableDefinition, this.fieldName + "=?");
		c.params.add(x);
		return c;
	}
}
