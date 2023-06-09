package com.inditex.productsearchservice.domain.shirt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UtilsTest {
	
	@Autowired
	List<ShirtProductOrdering> productsToBeOrdered;
	
	@Test
	void testStockAvailableDescendingOrdering() {
		WeightedShirtProductOrderingMetric metricWeighted1 = new WeightedShirtProductOrderingMetric(0.9, new StockAvailableShirtOrderingMetric());
		WeightedShirtProductOrderingMetric metricWeighted2 = new WeightedShirtProductOrderingMetric(new SalesShirtOrderingMetric());
		
		List<WeightedShirtProductOrderingMetric> metricsList = List.of(metricWeighted1,metricWeighted2);
		
		GlobalWeightedShirtProductOrderingMetric metrics = new GlobalWeightedShirtProductOrderingMetric();
		
		metrics.setMetrics(metricsList);
		
		List<ShirtProductOrdering> productsOrdered = Utils.orderProducts(productsToBeOrdered, metrics, false);
		
		assertEquals(productsToBeOrdered.size(), productsOrdered.size());
		
		assertEquals(4L, productsOrdered.get(0).getId());
		assertEquals(2L, productsOrdered.get(1).getId());
		assertEquals(3L, productsOrdered.get(2).getId());
		assertEquals(6L, productsOrdered.get(3).getId());
		assertEquals(1L, productsOrdered.get(4).getId());
		assertEquals(5L, productsOrdered.get(5).getId());
		
	}
	
	@Test
	void testOnySalesStockAvailableAscendingOrdering() {
		WeightedShirtProductOrderingMetric metricWeighted = new WeightedShirtProductOrderingMetric(new SalesShirtOrderingMetric());
		
		List<WeightedShirtProductOrderingMetric> metricsList = List.of(metricWeighted);
		
		GlobalWeightedShirtProductOrderingMetric metrics = new GlobalWeightedShirtProductOrderingMetric();
		
		metrics.setMetrics(metricsList);
		
		List<ShirtProductOrdering> productsOrdered = Utils.orderProducts(productsToBeOrdered, metrics, true);
		
		assertEquals(productsToBeOrdered.size(), productsOrdered.size());
		
		assertEquals(4L, productsOrdered.get(0).getId());
		assertEquals(6L, productsOrdered.get(1).getId());
		assertEquals(2L, productsOrdered.get(2).getId());
		assertEquals(3L, productsOrdered.get(3).getId());
		assertEquals(1L, productsOrdered.get(4).getId());
		assertEquals(5L, productsOrdered.get(5).getId());
	}
	
}
