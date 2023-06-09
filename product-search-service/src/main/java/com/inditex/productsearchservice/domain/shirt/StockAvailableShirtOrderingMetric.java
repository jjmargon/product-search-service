package com.inditex.productsearchservice.domain.shirt;

public class StockAvailableShirtOrderingMetric extends ShirtOrderingMetric {

	@Override
	public int compare(ShirtProductOrdering o1, ShirtProductOrdering o2) {
		return o1.getTotalStock() > o2.getTotalStock() ? 1 : o1.getTotalStock() < o2.getTotalStock() ? -1 : 0;
	}

	@Override
	public String getShortName() {
		return "TotalStock";
	}
}
