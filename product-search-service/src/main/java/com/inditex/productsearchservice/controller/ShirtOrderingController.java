package com.inditex.productsearchservice.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.inditex.productsearchservice.domain.ProductOrdering;
import com.inditex.productsearchservice.domain.shirt.GlobalWeightedShirtProductOrderingMetric;
import com.inditex.productsearchservice.domain.shirt.SalesShirtOrderingMetric;
import com.inditex.productsearchservice.domain.shirt.ShirtOrderingMetric;
import com.inditex.productsearchservice.domain.shirt.ShirtProductOrdering;
import com.inditex.productsearchservice.domain.shirt.StockAvailableShirtOrderingMetric;
import com.inditex.productsearchservice.domain.shirt.Utils;
import com.inditex.productsearchservice.domain.shirt.WeightedShirtProductOrderingMetric;

import io.micrometer.common.util.StringUtils;

@RestController
@RequestMapping("/shirts")
public class ShirtOrderingController {
	
	@Autowired
	List<ShirtProductOrdering> products;
	
	Map<String, ShirtOrderingMetric> metricsMap = getMetricsMap();
	
	@GetMapping
	public List<? extends ProductOrdering> orderShirts(@RequestParam String om,@RequestParam(required = false) String o) throws MissingServletRequestParameterException{
		
		GlobalWeightedShirtProductOrderingMetric productOrdering = new GlobalWeightedShirtProductOrderingMetric();
		
		if(StringUtils.isEmpty(om)) {
			throw new MissingServletRequestParameterException("om", "String");
		}
		try {
			if(om.indexOf(",") == -1) {
				ShirtOrderingMetric metric = getOrderingMetric(om);
				WeightedShirtProductOrderingMetric weightedMetric = new WeightedShirtProductOrderingMetric(1.0, metric);
				productOrdering.setMetrics(List.of(weightedMetric));
			}else {
				List<WeightedShirtProductOrderingMetric> weightedMetrics = Arrays.stream(om.split(",")).map(s -> {
					if(s.indexOf("-") == -1) {
						ShirtOrderingMetric metric = getOrderingMetric(s);
						return new WeightedShirtProductOrderingMetric(metric);
					}
					String[] parts = s.split("-");
					String metricName = parts[0];
					double weight = Double.parseDouble(parts[1]);
					ShirtOrderingMetric metric = getOrderingMetric(metricName);
					return new WeightedShirtProductOrderingMetric(weight, metric);
				}).collect(Collectors.toList());
				productOrdering.setMetrics(weightedMetrics);
			}
			
			
		}catch(Exception e) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "om parameter could not be mapped", e);
		}
		
		return Utils.orderProducts(products, productOrdering, Boolean.parseBoolean(o));
		
	}
	
	private Map<String, ShirtOrderingMetric> getMetricsMap(){
		Map<String, ShirtOrderingMetric> metricsMap = new ConcurrentHashMap<>();
		SalesShirtOrderingMetric salesOrderingMetric = new SalesShirtOrderingMetric();
		StockAvailableShirtOrderingMetric stockAvailableOrderingMetric = new StockAvailableShirtOrderingMetric();
		metricsMap.put(salesOrderingMetric.getShortName().toLowerCase(), salesOrderingMetric);
		metricsMap.put(stockAvailableOrderingMetric.getShortName().toLowerCase(), stockAvailableOrderingMetric);
		return Collections.unmodifiableMap(metricsMap);
	}
	
	
	
	private ShirtOrderingMetric getOrderingMetric(String metricName){
		if(!metricsMap.containsKey(metricName.toLowerCase())) {
			throw new IllegalArgumentException("Metric name not accepted");
		}
		return metricsMap.get(metricName.toLowerCase());
	}

	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<Object> handleException(Exception ex) {
        return ResponseEntity.badRequest().build();
    }

}
