package com.bbb.composite.product.details.dto.product;

import java.io.Serializable;

/**
 * BrandDTO attributes specified here.
 * 
 * @author psh111
 *
 */
public class BrandDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8275780178068737878L;

	private String brandId;

	private String brandName;

	private String brandDesc;

	private String brandImage;

	private Boolean displayFlag;
	
	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandDesc() {
		return brandDesc;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}

	public String getBrandImage() {
		return brandImage;
	}

	public void setBrandImage(String brandImage) {
		this.brandImage = brandImage;
	}

	public Boolean getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(Boolean displayFlag) {
		this.displayFlag = displayFlag;
	}
}
