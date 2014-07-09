package net.examp1e.picoorm;

public class Predicate<Row extends AbstractRow> {
	
	final TableDefinition<Row> tableDefinition;
	String fieldName;

	public final Predicate<Row> asc;
	public final Predicate<Row> desc;

	protected Predicate(TableDefinition<Row> tableDefinition, String fieldName) {
		this.tableDefinition = tableDefinition;
		this.fieldName = fieldName;
		this.asc = this;
		this.desc = new Predicate<Row>(false, this);
	}

	// used to create descending order-by predicate
	private Predicate(boolean _unused, Predicate<Row> src) {
		this.tableDefinition = src.tableDefinition;
		this.fieldName = src.fieldName;
		this.asc = src;
		this.desc = this;
	}

	boolean orderIsAscending() {
		return this == this.asc;
	}
}
