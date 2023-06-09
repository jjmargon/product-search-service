package com.inditex.productsearchservice.domain.shirt;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class GlobalWeightedShirtProductOrderingMetric {
	
	private List<WeightedShirtProductOrderingMetric> metrics;
	
	public void setMetrics(List<WeightedShirtProductOrderingMetric> inputMetrics){
		Assert.notEmpty(inputMetrics, "List of inputMetrics cannot be null or empty");
		
		double sumWeights = getMetricsWeightSum(inputMetrics);
		
		if(sumWeights > 1.0) {
			throw new IllegalArgumentException("Weighted metrics sum more than 1.0");
		}
		
		adjustLastMetricWeight(inputMetrics.get(inputMetrics.size()-1), sumWeights);
		
		if(CollectionUtils.isEmpty(metrics)) {
			metrics = new ArrayList<>();
			metrics.addAll(inputMetrics);
		}
	}
	
	protected void adjustLastMetricWeight(WeightedShirtProductOrderingMetric lastWeightedShirtProductOrderingMetric, double sumWeights) {
		// If there is no weight in last metric it will be (1.0-sum(metricsWeight))
		double lastMetricWeight = lastWeightedShirtProductOrderingMetric.getWeight();
		if(lastMetricWeight == 0.0) {
			lastWeightedShirtProductOrderingMetric.setWeight(1.0 - sumWeights);
		}
	}

	protected double getMetricsWeightSum(List<WeightedShirtProductOrderingMetric> inputMetrics) {
		return inputMetrics.stream().collect(Collectors.summingDouble(WeightedShirtProductOrderingMetric::getWeight)).doubleValue();
	}

	public List<WeightedShirtProductOrderingMetric> getMetrics() {
		return Collections.unmodifiableList(metrics);
	}


}
