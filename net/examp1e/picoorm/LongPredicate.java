package net.examp1e.picoorm;

public class LongPredicate<Table> {
	final TableDefinition<Table> tableDefinition;
	String fieldName;
	public LongPredicate(TableDefinition<Table> tableDefinition, String fieldName) {
		this.tableDefinition = tableDefinition;
		this.fieldName = fieldName;
	}
	public Condition<Table> is(int x) {
		Condition<Table> c = new Condition<Table>(this.tableDefinition, this.fieldName + "=?");
		c.params.add(Integer.toString(x));
		return c;
	}
	public Condition<Table> lessThan(int x) {
		Condition<Table> c = new Condition<Table>(this.tableDefinition, this.fieldName + "<?");
		c.params.add(Integer.toString(x));
		return c;
	}
}
