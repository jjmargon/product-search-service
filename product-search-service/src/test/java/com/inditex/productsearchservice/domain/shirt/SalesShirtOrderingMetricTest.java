package com.inditex.productsearchservice.domain.shirt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SalesShirtOrderingMetricTest {
	
	@Test
	void testCompare() {
		
		ShirtProductOrdering vNechBasic100 = new ShirtProductOrdering("V-NECH BASIC SHIRT 100 Sales");
		vNechBasic100.setId(1L);
		vNechBasic100.setSales_units(100);
		vNechBasic100.getStock().withL(0).withM(1).withS(2);
		
		ShirtProductOrdering vNechBasic50 = new ShirtProductOrdering("V-NECH BASIC SHIRT 50 Sales");
		vNechBasic50.setId(2L);
		vNechBasic50.setSales_units(50);
		vNechBasic50.getStock().withS(1).withM(1).withL(1);
		
		ShirtProductOrdering contrastingFabricBasic50 = new ShirtProductOrdering("CONTRASTING FABRIC BASIC T-SHIRT 50 sales");
		contrastingFabricBasic50.setId(2L);
		contrastingFabricBasic50.setSales_units(50);
		contrastingFabricBasic50.getStock().withS(5).withM(2).withL(1);
		
		SalesShirtOrderingMetric classUnderTest = new SalesShirtOrderingMetric();
		
		// Compare two products with same total stock
		assertEquals(0, classUnderTest.compare(vNechBasic50, contrastingFabricBasic50));
		
		// Compare two products with different stock
		assertTrue(classUnderTest.compare(contrastingFabricBasic50, vNechBasic100) < 0);
		assertTrue(classUnderTest.compare(vNechBasic100, vNechBasic50) > 0);
		
		
	}

}
