package net.examp1e.picoorm;

public abstract class Predicate<Row extends AbstractRow> {
	
	final TableDefinition<Row> tableDefinition;
	String fieldName;

	protected Predicate(TableDefinition<Row> tableDefinition, String fieldName) {
		this.tableDefinition = tableDefinition;
		this.fieldName = fieldName;
	}

	protected Predicate(Predicate<Row> src) {
		this.tableDefinition = src.tableDefinition;
		this.fieldName = src.fieldName;
	}

	public Predicate<Row> asc() {
		return new OrderPredicate<Row>(this, true);
	}

	public Predicate<Row> desc() {
		return new OrderPredicate<Row>(this, false);
	}

}
