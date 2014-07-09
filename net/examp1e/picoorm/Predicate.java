package net.examp1e.picoorm;

public abstract class Predicate<Row extends AbstractRow> extends Condition.OrderBy {

	public static class OrderPredicate<Row extends AbstractRow> extends Condition.OrderBy {
		Predicate<Row> predicate;
		boolean isAsc;
		OrderPredicate(Predicate<Row> predicate, boolean isAsc) {
			this.predicate = predicate;
			this.isAsc = isAsc;
		}
		@Override
		String toOrderBySQL() {
			return this.predicate.fieldName + (isAsc ? " ASC" : " DESC");
		}
	}

	protected TableDefinition<Row> tableDefinition;
	protected String fieldName;
	public final OrderPredicate<Row> asc = new OrderPredicate<Row>(this, true);
	public final OrderPredicate<Row> desc = new OrderPredicate<Row>(this, false);

	protected void _init(TableDefinition<Row> tableDefinition, String fieldName) {
		this.tableDefinition = tableDefinition;
		this.fieldName = fieldName;
	}

	@Override
	String toOrderBySQL() {
		return this.asc.toOrderBySQL();
	}
}
