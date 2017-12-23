package com.bbb.composite.product.details.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * SkuPriceCompositeDTO attributes specified here
 * 
 * @author psh111
 *
 */
public class SkuPriceCompositeDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7738597376576851816L;

	private BigDecimal listPrice;
	
	private BigDecimal salePrice;
	
	private Boolean inCartFlag;
	
	private String labelCode;
	
	private BigDecimal inCartPrice;
	

	public BigDecimal getListPrice() {
		return listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
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

}
