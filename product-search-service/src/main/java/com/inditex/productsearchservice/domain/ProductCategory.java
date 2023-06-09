package com.inditex.productsearchservice.domain;

public interface ProductCategory<T> {
	
	String getName();
	T getStock();

}
