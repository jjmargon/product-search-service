package com.inditex.productsearchservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("productCategory")
public abstract class ProductOrdering {

	private Long id;
	private final String name;
	private long sales_units;
	private final ProductCategory<?> productCategory;

	public ProductOrdering(String name, ProductCategory<?> productCategory) {
		super();
		this.name = name;
		this.productCategory = productCategory;
	}

	/**
	 * This is just for setting ids in the exercise. In real situations, this method
	 * wouldn't exist
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setSales_units(long sales_units) {
		this.sales_units = sales_units;
	}

	public long getSales_units() {
		return sales_units;
	}

	public String getName() {
		return name;
	}

	public ProductCategory<?> getProductCategory() {
		return productCategory;
	}

}
