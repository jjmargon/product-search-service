package com.inditex.productsearchservice.domain.shirt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GlobalWeightedShirtProductOrderingMetrictTest {
	
	@Test
	void testMetricWithSumWeightMoreThanOneFails() {
		WeightedShirtProductOrderingMetric metricWeighted1 = new WeightedShirtProductOrderingMetric(0.6, new StockAvailableShirtOrderingMetric());
		WeightedShirtProductOrderingMetric metricWeighted2 = new WeightedShirtProductOrderingMetric(0.6, new StockAvailableShirtOrderingMetric());
		
		List<WeightedShirtProductOrderingMetric> metrics = List.of(metricWeighted1, metricWeighted2);
		
		GlobalWeightedShirtProductOrderingMetric classUnderTest = new GlobalWeightedShirtProductOrderingMetric();
		
		assertThrows(IllegalArgumentException.class, () -> {
			classUnderTest.setMetrics(metrics);
        });
	}
	
	@Test
	void testOneMetricWithSumWeightMoreThanOneFails() {
		WeightedShirtProductOrderingMetric metricWeighted1 = new WeightedShirtProductOrderingMetric(1.1, new StockAvailableShirtOrderingMetric());
		List<WeightedShirtProductOrderingMetric> metrics = List.of(metricWeighted1);
		
		GlobalWeightedShirtProductOrderingMetric classUnderTest = new GlobalWeightedShirtProductOrderingMetric();
		
		assertThrows(IllegalArgumentException.class, () -> {
			classUnderTest.setMetrics(metrics);
        });
	}
	
	@Test
	void testMetricListCannotBeEmpty() {
		GlobalWeightedShirtProductOrderingMetric classUnderTest = new GlobalWeightedShirtProductOrderingMetric();
		
		assertThrows(IllegalArgumentException.class, () -> {
			classUnderTest.setMetrics(Collections.emptyList());
        });
	}
	
	@Test
	void testMetricListCannotBeNull() {
		GlobalWeightedShirtProductOrderingMetric classUnderTest = new GlobalWeightedShirtProductOrderingMetric();
		
		assertThrows(IllegalArgumentException.class, () -> {
			classUnderTest.setMetrics(null);
        });
	}
	
	@Test
	void testAdjustLastMetric() {
		WeightedShirtProductOrderingMetric metricWeighted1 = new WeightedShirtProductOrderingMetric(0.6, new StockAvailableShirtOrderingMetric());
		WeightedShirtProductOrderingMetric metricWeighted2 = new WeightedShirtProductOrderingMetric(new StockAvailableShirtOrderingMetric());
		List<WeightedShirtProductOrderingMetric> metrics = List.of(metricWeighted1, metricWeighted2);
		
		GlobalWeightedShirtProductOrderingMetric classUnderTest = new GlobalWeightedShirtProductOrderingMetric();
		
		classUnderTest.setMetrics(metrics);
		
		List<WeightedShirtProductOrderingMetric> metricsAdjusted = classUnderTest.getMetrics();
		assertEquals(0.6, metricsAdjusted.get(0).getWeight());
		assertEquals(0.4, metricsAdjusted.get(1).getWeight());
	}
	
}
