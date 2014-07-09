package net.examp1e.picoorm;

public class OrderPredicate<Row extends AbstractRow> extends Predicate<Row> {

	boolean isAsc = true;

	OrderPredicate(Predicate<Row> src, boolean isAsc) {
		super(src);
		this.isAsc = isAsc;
	}

}
