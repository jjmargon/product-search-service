package com.inditex.productsearchservice.domain.shirt;

import com.inditex.productsearchservice.domain.ProductOrderingStock;

public class ShirtStock implements ProductOrderingStock{
	
	private int stockS;
    private int stockM;
    private int stockL;
    
	public int getStockS() {
		return stockS;
	}
	public void setStockS(int stockS) {
		this.stockS = stockS;
	}
	public int getStockM() {
		return stockM;
	}
	public void setStockM(int stockM) {
		this.stockM = stockM;
	}
	public int getStockL() {
		return stockL;
	}
	public void setStockL(int stockL) {
		this.stockL = stockL;
	}
	public ShirtStock withS(int stockS) {
		this.setStockS(stockS);
		return this;
	}
	public ShirtStock withM(int stockM) {
		this.setStockM(stockM);
		return this;
	}
	public ShirtStock withL(int stockL) {
		this.setStockL(stockL);
		return this;
	}


}
