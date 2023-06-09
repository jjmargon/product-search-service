package com.inditex.productsearchservice.domain.shirt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StockAvailableShirtOrderingMetricTest {
	
	@Test
	void testCompare() {
		
		ShirtProductOrdering vNechBasic3 = new ShirtProductOrdering("V-NECH BASIC SHIRT 3 Stock");
		vNechBasic3.setId(1L);
		vNechBasic3.setSales_units(100);
		vNechBasic3.getStock().withL(0).withM(1).withS(2);
		
		ShirtProductOrdering contrastingFabricBasic3 = new ShirtProductOrdering("CONTRASTING FABRIC BASIC T-SHIRT 3 Stock");
		contrastingFabricBasic3.setId(2L);
		contrastingFabricBasic3.setSales_units(50);
		contrastingFabricBasic3.getStock().withS(1).withM(1).withL(1);
		
		ShirtProductOrdering contrastingFabricBasic5 = new ShirtProductOrdering("CONTRASTING FABRIC BASIC T-SHIRT 5 Stock");
		contrastingFabricBasic5.setId(2L);
		contrastingFabricBasic5.setSales_units(50);
		contrastingFabricBasic5.getStock().withS(5).withM(2).withL(1);
		
		StockAvailableShirtOrderingMetric classUnderTest = new StockAvailableShirtOrderingMetric();
		
		// Compare two products with same total stock
		assertEquals(0, classUnderTest.compare(vNechBasic3, contrastingFabricBasic3));
		
		// Compare two products with different stock
		assertTrue(classUnderTest.compare(vNechBasic3, contrastingFabricBasic5) < 0);
		assertTrue(classUnderTest.compare(contrastingFabricBasic5, vNechBasic3) > 0);
		
		
	}

}
