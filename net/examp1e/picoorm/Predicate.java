package net.examp1e.picoorm;

public abstract class Predicate<Row extends AbstractRow> {
	
	final TableDefinition<Row> tableDefinition;
	String fieldName;

	public Predicate(TableDefinition<Row> tableDefinition, String fieldName) {
		this.tableDefinition = tableDefinition;
		this.fieldName = fieldName;
	}

}
