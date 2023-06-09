package com.inditex.productsearchservice.domain.shirt;

import java.util.Comparator;

public abstract class ShirtOrderingMetric implements Comparator<ShirtProductOrdering> {
	
	abstract public String getShortName();

}
