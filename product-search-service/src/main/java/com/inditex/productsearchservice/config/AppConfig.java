package com.inditex.productsearchservice.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.inditex.productsearchservice.domain.shirt.ShirtProductOrdering;

@Configuration
public class AppConfig {

	@Bean
	List<ShirtProductOrdering> shirtsToBeOrdered() {
		List<ShirtProductOrdering> shirtsToBeOrdered = new ArrayList<>();
		shirtsToBeOrdered.add(vNechBasic());
		shirtsToBeOrdered.add(contrastingFabric());
		shirtsToBeOrdered.add(raisedPrint());
		shirtsToBeOrdered.add(plaited());
		shirtsToBeOrdered.add(contrastingLace());
		shirtsToBeOrdered.add(slogan());
		return shirtsToBeOrdered;
	}
	
	private ShirtProductOrdering vNechBasic() {
		ShirtProductOrdering vNechBasic = new ShirtProductOrdering("V-NECH BASIC SHIRT");
		vNechBasic.setId(1L);
		vNechBasic.setSales_units(100);
		vNechBasic.getStock().withL(0).withM(9).withS(4);
		return vNechBasic;
	}

	private ShirtProductOrdering contrastingFabric() {
		ShirtProductOrdering contrastingFabricBasic = new ShirtProductOrdering("CONTRASTING FABRIC BASIC T-SHIRT");
		contrastingFabricBasic.setId(2L);
		contrastingFabricBasic.setSales_units(50);
		contrastingFabricBasic.getStock().withS(35).withM(9).withL(9);
		return contrastingFabricBasic;
	}

	private ShirtProductOrdering raisedPrint() {
		ShirtProductOrdering raisedPrint = new ShirtProductOrdering("RAISED PRINT T-SHIRT");
		raisedPrint.setId(3L);
		raisedPrint.setSales_units(80);
		raisedPrint.getStock().withS(20).withM(2).withL(20);
		return raisedPrint;
	}

	private ShirtProductOrdering plaited() {
		ShirtProductOrdering plaited = new ShirtProductOrdering("PLAITED PRINT T-SHIRT");
		plaited.setId(4L);
		plaited.setSales_units(3);
		plaited.getStock().withS(25).withM(30).withL(10);
		return plaited;
	}

	private ShirtProductOrdering contrastingLace() {
		ShirtProductOrdering contrastingLace = new ShirtProductOrdering("CONTRASTING LACE T-SHIRT");
		contrastingLace.setId(5L);
		contrastingLace.setSales_units(650);
		contrastingLace.getStock().withS(0).withM(1).withL(0);
		return contrastingLace;
	}

	private ShirtProductOrdering slogan() {
		ShirtProductOrdering slogan = new ShirtProductOrdering("SLOGAN T-SHIRT");
		slogan.setId(6L);
		slogan.setSales_units(20);
		slogan.getStock().withS(9).withM(2).withL(5);
		return slogan;
	}

}
