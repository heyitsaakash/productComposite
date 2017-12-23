package com.bbb.composite.product.details.dto;

import java.io.Serializable;
/**
 * BrandCompositeDTO attributes specified here.
 *
 * @author psh111
 */
public class BrandCompositeDTO implements Serializable{


	private static final long serialVersionUID = -8275780178068737878L;

	private String brandId;

	private String brandName;

	private String brandDescription;

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

	public String getBrandDescription() {
		return brandDescription;
	}

	public void setBrandDescription(String brandDescription) {
		this.brandDescription = brandDescription;
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
