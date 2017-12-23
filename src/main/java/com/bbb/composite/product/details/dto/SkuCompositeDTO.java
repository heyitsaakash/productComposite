package com.bbb.composite.product.details.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * SkuCompositeDTO attributes specified here.
 * 
 * @author psh111
 *
 */
public class SkuCompositeDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2976947635574226724L;
	private String productId;
	private String id;
	private String shortDescription;
	private String longDescription;
	private Boolean activeFlag;
	private Boolean intlRestricted;
	private Boolean shippingRestricted;
	private String displayShipMsg;
	private ImageDTO skuImages;
	private SkuPriceCompositeDTO price;
	private Map <String,List<AttributeDTO>> skuAttributes;
	private SKUCompositeVariantValuesDTO variantValues;
	
	public Boolean getIntlRestricted() {
		return intlRestricted;
	}
	public void setIntlRestricted(Boolean intlRestricted) {
		this.intlRestricted = intlRestricted;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getLongDescription() {
		return longDescription;
	}
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, List<AttributeDTO>> getSkuAttributes() {
		return skuAttributes;
	}
	public void setSkuAttributes(Map<String, List<AttributeDTO>> skuAttributes) {
		this.skuAttributes = skuAttributes;
	}
	public ImageDTO getSkuImages() {
		return skuImages;
	}
	public void setSkuImages(ImageDTO skuImages) {
		this.skuImages = skuImages;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public Boolean getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	public String getDisplayShipMsg() {
		return displayShipMsg;
	}
	public void setDisplayShipMsg(String displayShipMsg) {
		this.displayShipMsg = displayShipMsg;
	}
	public SkuPriceCompositeDTO getPrice() {
		return price;
	}
	public void setPrice(SkuPriceCompositeDTO price) {
		this.price = price;
	}
	public Boolean getShippingRestricted() {
		return shippingRestricted;
	}
	public void setShippingRestricted(Boolean shippingRestricted) {
		this.shippingRestricted = shippingRestricted;
	}
	public SKUCompositeVariantValuesDTO getVariantValues() {
		return variantValues;
	}
	public void setVariantValues(SKUCompositeVariantValuesDTO variantValues) {
		this.variantValues = variantValues;
	}


}
