package com.inditex.productsearchservice.domain.shirt;

import com.inditex.productsearchservice.domain.ProductOrdering;

public class ShirtProductOrdering extends ProductOrdering {

	private ShirtStock stock;

	public ShirtProductOrdering(String name) {
		super(name, new ShirtProductCategory());
	}

	public ShirtStock getStock() {
		if (stock == null) {
			synchronized (ShirtStock.class) {
				if (stock == null) {
					stock = new ShirtStock();
				}
			}

		}
		return stock;
	}

	public int getTotalStock() {
		return getStock().getStockS() + getStock().getStockM() + getStock().getStockL();
	}

}
