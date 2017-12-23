package com.bbb.composite.product.details.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ProductPriceCompositeDTO attributes specified here.
 * 
 * @author psh111
 *
 */
public class ProductPriceCompositeDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7738597376576851816L;

	private Boolean inCartFlag;
	
	private String labelCode;
	
	private BigDecimal inCartPrice;
	
	private String priceRangeDesc;
	
	private String salePriceRangeDesc;
		
	public String getSalePriceRangeDesc() {
		return salePriceRangeDesc;
	}

	public void setSalePriceRangeDesc(String salePriceRangeDesc) {
		this.salePriceRangeDesc = salePriceRangeDesc;
	}

	public Boolean getInCartFlag() {
		return inCartFlag;
	}

	public void setInCartFlag(Boolean inCartFlag) {
		this.inCartFlag = inCartFlag;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public BigDecimal getInCartPrice() {
		return inCartPrice;
	}

	public void setInCartPrice(BigDecimal inCartPrice) {
		this.inCartPrice = inCartPrice;
	}

	public String getPriceRangeDesc() {
		return priceRangeDesc;
	}

	public void setPriceRangeDesc(String priceRangeDesc) {
		this.priceRangeDesc = priceRangeDesc;
	}

	
	
}
