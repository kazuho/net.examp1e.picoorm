package net.examp1e.picoorm;

public abstract class Predicate<Row extends AbstractRow> {
	
	public static class OrderPredicate<Row extends AbstractRow> extends Predicate<Row> {
		boolean isAsc = true;
		OrderPredicate(Predicate<Row> src, boolean isAsc) {
			super(src);
			this.isAsc = isAsc;
		}
		@Override
		boolean orderIsAscending() {
			return this.isAsc;
		}
	}

	final TableDefinition<Row> tableDefinition;
	String fieldName;

	public final OrderPredicate<Row> asc;
	public final OrderPredicate<Row> desc;

	protected Predicate(TableDefinition<Row> tableDefinition, String fieldName) {
		this.tableDefinition = tableDefinition;
		this.fieldName = fieldName;
		this.asc = new OrderPredicate<Row>(this, true);
		this.desc = new OrderPredicate<Row>(this, false);
	}

	protected Predicate(Predicate<Row> src) {
		this.tableDefinition = src.tableDefinition;
		this.fieldName = src.fieldName;
		this.asc = src.asc;
		this.desc = src.desc;
	}

	boolean orderIsAscending() {
		return true;
	}
}
