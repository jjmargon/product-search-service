package com.inditex.productsearchservice.domain.shirt;

import com.inditex.productsearchservice.domain.ProductCategory;

public class ShirtProductCategory implements ProductCategory<ShirtStock> {

	private ShirtStock stock; 
	
	@Override
	public String getName() {
		return "SHIRTS";
	}

	@Override
	public ShirtStock getStock() {
		if(stock == null) {
			synchronized(ShirtStock.class) {
				if(stock == null) {
					stock = new ShirtStock();
				}
			}
		}
		return stock;
	}

}
