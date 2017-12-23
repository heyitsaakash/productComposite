package com.bbb.composite.product.details.dto.sku;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import com.bbb.composite.product.details.dto.AttributeDTO;
import com.bbb.composite.product.details.dto.ImageDTO;

/**
 * SkuDTO attributes here
 * @author psh111
 *
 */
public class SkuDTO implements Serializable {

	private static final long serialVersionUID = -8945167078834730838L;
	
	private String productId;
	private String siteId;
	private String skuId;
	private Boolean isDisable;
	private Boolean isWebOffered;
	private String displayName;
	private String description;
	private String longDescription;
	private Boolean assemblyOffered;
	private Double assemblyTime;
	private String color;
	private String customizableCodes;
	private Boolean isCustomizationRequired;
	private Boolean isCustomizationOffered;
	private String ecomFulfillment;
	private Set<ShippingMethodDTO> eligibleShipMethods;
	private Boolean isEmailStockAlertsEnabled;
	private Boolean isLtl;
	private String size;
	private List<AttributeDTO> skuAttributes;
	private Boolean isSkuBelowLine;
	private Boolean isIntlRestricted;
	private Set<String> parentProductIds;
	private String personalizationType;
	private String upc;
	private String vdcShipMessage;
	private Boolean isVdcSku;
	private Integer shippingCutoffOffset;
	private Instant startDate;
	private Instant endDate;
	private Boolean anywhereZoom;
	private String image;
	private Boolean isBopusAllowed;
	private Set<RebateDTO> eligibaleRebateInfo;
	private Boolean isGiftWrapEligible;
	private Boolean isSddAttribute;
	private Boolean isEcoFeeEligible;
	private Boolean isSkuGiftCard;
	private String vendorId;
	private Boolean isStoreSku;
	private Boolean isSkuActive;
	private Boolean hasRebate;
	private Boolean isShippingRestricted; 
	private ImageDTO skuImages;
	private String scene7URL;
	private boolean zoomAvailable;
	private SKUVariantValuesDTO skuVariantValues;

	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public Boolean getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Boolean isDisable) {
		this.isDisable = isDisable;
	}
	public Boolean getIsWebOffered() {
		return isWebOffered;
	}
	public void setIsWebOffered(Boolean isWebOffered) {
		this.isWebOffered = isWebOffered;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLongDescription() {
		return longDescription;
	}
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	public Boolean getAssemblyOffered() {
		return assemblyOffered;
	}
	public void setAssemblyOffered(Boolean assemblyOffered) {
		this.assemblyOffered = assemblyOffered;
	}
	public Double getAssemblyTime() {
		return assemblyTime;
	}
	public void setAssemblyTime(Double assemblyTime) {
		this.assemblyTime = assemblyTime;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCustomizableCodes() {
		return customizableCodes;
	}
	public void setCustomizableCodes(String customizableCodes) {
		this.customizableCodes = customizableCodes;
	}
	public Boolean getIsCustomizationRequired() {
		return isCustomizationRequired;
	}
	public void setIsCustomizationRequired(Boolean isCustomizationRequired) {
		this.isCustomizationRequired = isCustomizationRequired;
	}
	public Boolean getIsCustomizationOffered() {
		return isCustomizationOffered;
	}
	public void setIsCustomizationOffered(Boolean isCustomizationOffered) {
		this.isCustomizationOffered = isCustomizationOffered;
	}
	public String getEcomFulfillment() {
		return ecomFulfillment;
	}
	public void setEcomFulfillment(String ecomFulfillment) {
		this.ecomFulfillment = ecomFulfillment;
	}

	public Boolean getIsEmailStockAlertsEnabled() {
		return isEmailStockAlertsEnabled;
	}
	public void setIsEmailStockAlertsEnabled(Boolean isEmailStockAlertsEnabled) {
		this.isEmailStockAlertsEnabled = isEmailStockAlertsEnabled;
	}
	public Boolean getIsLtl() {
		return isLtl;
	}
	public void setIsLtl(Boolean isLtl) {
		this.isLtl = isLtl;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Boolean getIsSkuBelowLine() {
		return isSkuBelowLine;
	}
	public void setIsSkuBelowLine(Boolean isSkuBelowLine) {
		this.isSkuBelowLine = isSkuBelowLine;
	}
	public Boolean getIsIntlRestricted() {
		return isIntlRestricted;
	}
	public void setIsIntlRestricted(Boolean isIntlRestricted) {
		this.isIntlRestricted = isIntlRestricted;
	}
	public Set<String> getParentProductIds() {
		return parentProductIds;
	}
	public void setParentProductIds(Set<String> parentProductIds) {
		this.parentProductIds = parentProductIds;
	}
	public String getPersonalizationType() {
		return personalizationType;
	}
	public void setPersonalizationType(String personalizationType) {
		this.personalizationType = personalizationType;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getVdcShipMessage() {
		return vdcShipMessage;
	}
	public void setVdcShipMessage(String vdcShipMessage) {
		this.vdcShipMessage = vdcShipMessage;
	}
	public Boolean getIsVdcSku() {
		return isVdcSku;
	}
	public void setIsVdcSku(Boolean isVdcSku) {
		this.isVdcSku = isVdcSku;
	}
	public Integer getShippingCutoffOffset() {
		return shippingCutoffOffset;
	}
	public void setShippingCutoffOffset(Integer shippingCutoffOffset) {
		this.shippingCutoffOffset = shippingCutoffOffset;
	}

	public Boolean getAnywhereZoom() {
		return anywhereZoom;
	}
	public void setAnywhereZoom(Boolean anywhereZoom) {
		this.anywhereZoom = anywhereZoom;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Boolean getIsBopusAllowed() {
		return isBopusAllowed;
	}
	public void setIsBopusAllowed(Boolean isBopusAllowed) {
		this.isBopusAllowed = isBopusAllowed;
	}
	public Boolean getIsGiftWrapEligible() {
		return isGiftWrapEligible;
	}
	public void setIsGiftWrapEligible(Boolean isGiftWrapEligible) {
		this.isGiftWrapEligible = isGiftWrapEligible;
	}
	public Boolean getIsSddAttribute() {
		return isSddAttribute;
	}
	public void setIsSddAttribute(Boolean isSddAttribute) {
		this.isSddAttribute = isSddAttribute;
	}
	public Boolean getIsEcoFeeEligible() {
		return isEcoFeeEligible;
	}
	public void setIsEcoFeeEligible(Boolean isEcoFeeEligible) {
		this.isEcoFeeEligible = isEcoFeeEligible;
	}
	public Boolean getIsSkuGiftCard() {
		return isSkuGiftCard;
	}
	public void setIsSkuGiftCard(Boolean isSkuGiftCard) {
		this.isSkuGiftCard = isSkuGiftCard;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public Boolean getIsStoreSku() {
		return isStoreSku;
	}
	public void setIsStoreSku(Boolean isStoreSku) {
		this.isStoreSku = isStoreSku;
	}
	public Boolean getIsSkuActive() {
		return isSkuActive;
	}
	public void setIsSkuActive(Boolean isSkuActive) {
		this.isSkuActive = isSkuActive;
	}
	public Boolean getHasRebate() {
		return hasRebate;
	}
	public void setHasRebate(Boolean hasRebate) {
		this.hasRebate = hasRebate;
	}
	public Set<RebateDTO> getEligibaleRebateInfo() {
		return eligibaleRebateInfo;
	}
	public void setEligibaleRebateInfo(Set<RebateDTO> eligibaleRebateInfo) {
		this.eligibaleRebateInfo = eligibaleRebateInfo;
	}

	public Set<ShippingMethodDTO> getEligibleShipMethods() {
		return eligibleShipMethods;
	}
	public void setEligibleShipMethods(Set<ShippingMethodDTO> eligibleShipMethods) {
		this.eligibleShipMethods = eligibleShipMethods;
	}
	
	public List<AttributeDTO> getSkuAttributes() {
		return skuAttributes;
	}
	public void setSkuAttributes(List<AttributeDTO> skuAttributes) {
		this.skuAttributes = skuAttributes;
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
	public Boolean getIsShippingRestricted() {
		return isShippingRestricted;
	}
	public void setIsShippingRestricted(Boolean isShippingRestricted) {
		this.isShippingRestricted = isShippingRestricted;
	}
	public ImageDTO getSkuImages() {
		return skuImages;
	}
	public void setSkuImages(ImageDTO skuImages) {
		this.skuImages = skuImages;
	}
	public boolean isZoomAvailable() {
		return zoomAvailable;
	}
	public void setZoomAvailable(boolean zoomAvailable) {
		this.zoomAvailable = zoomAvailable;
	}
	
	public SKUVariantValuesDTO getSkuVariantValues() {
		return skuVariantValues;
	}
	public void setSkuVariantValues(SKUVariantValuesDTO skuVariantValues) {
		this.skuVariantValues = skuVariantValues;
	}
	public String getScene7URL() {
		return scene7URL;
	}
	public void setScene7URL(String scene7url) {
		scene7URL = scene7url;
	}
	@Override
	public String toString() {
		return "SkuDTO [productId=" + productId + ", siteId=" + siteId + ", skuId=" + skuId + ", isDisable=" + isDisable
				+ ", isWebOffered=" + isWebOffered + ", displayName=" + displayName + ", description=" + description
				+ ", longDescription=" + longDescription + ", assemblyOffered=" + assemblyOffered + ", assemblyTime="
				+ assemblyTime + ", color=" + color + ", customizableCodes=" + customizableCodes
				+ ", isCustomizationRequired=" + isCustomizationRequired + ", isCustomizationOffered="
				+ isCustomizationOffered + ", ecomFulfillment=" + ecomFulfillment + ", eligibleShipMethods="
				+ eligibleShipMethods + ", isEmailStockAlertsEnabled=" + isEmailStockAlertsEnabled + ", isLtl=" + isLtl
				+ ", size=" + size + ", skuAttributesMap=" + skuAttributes + ", isSkuBelowLine=" + isSkuBelowLine
				+ ", isIntlRestricted=" + isIntlRestricted + ", parentProductIds=" + parentProductIds
				+ ", personalizationType=" + personalizationType + ", upc=" + upc + ", vdcShipMessage=" + vdcShipMessage
				+ ", isVdcSku=" + isVdcSku + ", shippingCutoffOffset=" + shippingCutoffOffset + ", startDate="
				+ startDate + ", endDate=" + endDate + ", anywhereZoom=" + anywhereZoom + ", image=" + image
				+ ", isBopusAllowed=" + isBopusAllowed + ", eligibaleRebateInfo=" + eligibaleRebateInfo
				+ ", isGiftWrapEligible=" + isGiftWrapEligible + ", isSddAttribute=" + isSddAttribute
				+ ", isEcoFeeEligible=" + isEcoFeeEligible + ", isSkuGiftCard=" + isSkuGiftCard + ", vendorId="
				+ vendorId + ", isStoreSku=" + isStoreSku + ", isSkuActive=" + isSkuActive + ", hasRebate=" + hasRebate
				+ ", isShippingRestricted=" + isShippingRestricted + ", skuImages=" + skuImages + ", scene7URL="
				+ scene7URL + ", zoomAvailable=" + zoomAvailable + ", skuVariantValues=" + skuVariantValues + "]";
	}
	

}
