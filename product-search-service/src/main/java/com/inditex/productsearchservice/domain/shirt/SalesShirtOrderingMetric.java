package com.inditex.productsearchservice.domain.shirt;

public class SalesShirtOrderingMetric extends ShirtOrderingMetric {

	@Override
	public int compare(ShirtProductOrdering o1, ShirtProductOrdering o2) {
		return o1.getSales_units() > o2.getSales_units() ? 1 : o1.getSales_units() < o2.getSales_units() ? -1 : 0;
	}

	@Override
	public String getShortName() {
		return "Sales";
	}
}
