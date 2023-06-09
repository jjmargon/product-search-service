package com.inditex.productsearchservice.domain.shirt;

public class WeightedShirtProductOrderingMetric {

	private double weight;
	private final ShirtOrderingMetric shirtOrderingMetric;
	
	public WeightedShirtProductOrderingMetric(double weight, ShirtOrderingMetric shirtOrderingMetric) {
		super();
		this.weight = weight;
		this.shirtOrderingMetric = shirtOrderingMetric;
	}

	public WeightedShirtProductOrderingMetric(ShirtOrderingMetric shirtOrderingMetric) {
		super();
		this.shirtOrderingMetric = shirtOrderingMetric;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public ShirtOrderingMetric getShirtOrderingMetric() {
		return shirtOrderingMetric;
	}
}
