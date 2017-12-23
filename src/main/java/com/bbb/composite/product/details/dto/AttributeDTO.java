package com.bbb.composite.product.details.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * AttributeDTO attributes specified here.
 *
 * @author psh111
 */
public class AttributeDTO implements Serializable{
	
	

	private static final long serialVersionUID = 2420868931154551513L;

	private String attributeName;

	private String attributeDescription;

	private String imageUrl;

	private String actionUrl;

	private String placeHolder;

	private Integer priority;

	private String skuAttributeId;

	private String intlProdAttr;

	private Boolean hideAttribute;
	
	private Instant startDate;
	
	private Instant endDate;	

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeDescription() {
		return attributeDescription;
	}

	public void setAttributeDescription(String attributeDescription) {
		this.attributeDescription = attributeDescription;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getPlaceHolder() {
		return placeHolder;
	}

	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getSkuAttributeId() {
		return skuAttributeId;
	}

	public void setSkuAttributeId(String skuAttributeId) {
		this.skuAttributeId = skuAttributeId;
	}

	public String getIntlProdAttr() {
		return intlProdAttr;
	}

	public void setIntlProdAttr(String intlProdAttr) {
		this.intlProdAttr = intlProdAttr;
	}

	public Boolean getHideAttribute() {
		return hideAttribute;
	}

	public void setHideAttribute(Boolean hideAttribute) {
		this.hideAttribute = hideAttribute;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "AttributeDTO [attributeName=" + attributeName + ", attributeDescription=" + attributeDescription
				+ ", imageUrl=" + imageUrl + ", actionUrl=" + actionUrl + ", placeHolder=" + placeHolder + ", priority="
				+ priority + ", skuAttributeId=" + skuAttributeId + ", intlProdAttr=" + intlProdAttr
				+ ", hideAttribute=" + hideAttribute + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	
}
