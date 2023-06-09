package com.inditex.productsearchservice.domain.shirt;

import static java.util.stream.Collectors.summingDouble;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

public class Utils {
	
	public static List<ShirtProductOrdering> orderProducts(List<ShirtProductOrdering> products, 
			GlobalWeightedShirtProductOrderingMetric metrics, boolean ascOrder){
		
		Assert.notEmpty(products, "A list of products must contain at least one element to be ordered");
		Assert.notNull(metrics,"The metrics object cannot be null");
		Assert.notEmpty(metrics.getMetrics(),"The metrics object must contain at least one metric");
		
		Comparator<ShirtProductOrdering> productsComparator = (p1,p2) -> {
			double productsCompared = metrics.getMetrics().stream().collect(summingDouble( productMetric -> 
				productMetric.getShirtOrderingMetric().compare(p1, p2) * productMetric.getWeight()
			));
			return productsCompared > 0.0 ? 1 : productsCompared == 0.0 ? 0 : -1;
		};
		
		if(ascOrder) {
			return products.parallelStream().sorted(productsComparator).collect(Collectors.toList());
		} else {
			return products.parallelStream().sorted(productsComparator.reversed()).collect(Collectors.toList());
		}
	}
	

}
