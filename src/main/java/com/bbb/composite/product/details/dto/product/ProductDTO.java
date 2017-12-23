package com.bbb.composite.product.details.dto.product;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.bbb.composite.product.details.dto.AttributeDTO;
import com.bbb.composite.product.details.dto.CategorySummaryDTO;
import com.bbb.composite.product.details.dto.ImageDTO;

/**
 * ProductDTO attributes specified here
 * 
 * @author psh111
 *
 */
public class ProductDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1060698755829973405L;

	private String productId;

	private String siteId;
	
	private boolean isProductActive;

	private String name;

	private String description;

	private String longDescription;

	private String prdKeywords;
	
	private ImageDTO productImage;
	
	private VendorDTO vendorDTO;

	private BrandDTO brandDTO;

	
	private Boolean showImagesInCollection;
	
	private Instant startDate;
	
	private Instant endDate;
	
	private Boolean disableForeverPdpFlag;
	
	private Instant enableDate;
	
	private String primaryCategoryIdDefault;
	
	private String scene7Url;
	
	private List<String> altImagesList;
	
	private String swatchImage;
	
	private Integer zoomImageIndex;
	
	private String zoomImage;
	
	private Boolean anywhereZoomAvailable;
	
	private List<TabDTO> productTabs;

	private List<MediaDTO> productMedias;

	private List<AttributeDTO> productAttributes;

	private String seoUrl;

	private String shopGuideId;

	private Boolean ltlProduct;

	private Boolean intlRestricted;

	private String porchServiceFamilyType;

	private List<String> porchServiceFamilyCodes;
	
	private List<ProductDTO> childProductIds;
	
	private List<String> childSKUs;
	
	private Boolean giftCert;
	
	private boolean zoomFlag = true;
	
	private BazaarVoiceProductDTO bazaarVoiceProductDTO;
	
	private List<CategorySummaryDTO> relatedCategories;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Boolean getShowImagesInCollection() {
		return showImagesInCollection;
	}

	public void setShowImagesInCollection(Boolean showImagesInCollection) {
		this.showImagesInCollection = showImagesInCollection;
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

	public Boolean getDisableForeverPdpFlag() {
		return disableForeverPdpFlag;
	}

	public void setDisableForeverPdpFlag(Boolean disableForeverPdpFlag) {
		this.disableForeverPdpFlag = disableForeverPdpFlag;
	}

	public Instant getEnableDate() {
		return enableDate;
	}

	public void setEnableDate(Instant enableDate) {
		this.enableDate = enableDate;
	}

	public String getPrimaryCategoryIdDefault() {
		return primaryCategoryIdDefault;
	}

	public void setPrimaryCategoryIdDefault(String primaryCategoryIdDefault) {
		this.primaryCategoryIdDefault = primaryCategoryIdDefault;
	}

	public String getScene7Url() {
		return scene7Url;
	}

	public void setScene7Url(String scene7Url) {
		this.scene7Url = scene7Url;
	}

	public List<String> getAltImagesList() {
		return altImagesList;
	}

	public void setAltImagesList(List<String> altImagesList) {
		this.altImagesList = altImagesList;
	}

	public String getSwatchImage() {
		return swatchImage;
	}

	public void setSwatchImage(String swatchImage) {
		this.swatchImage = swatchImage;
	}

	public Integer getZoomImageIndex() {
		return zoomImageIndex;
	}

	public void setZoomImageIndex(Integer zoomImageIndex) {
		this.zoomImageIndex = zoomImageIndex;
	}

	public String getZoomImage() {
		return zoomImage;
	}

	public void setZoomImage(String zoomImage) {
		this.zoomImage = zoomImage;
	}

	public Boolean getAnywhereZoomAvailable() {
		return anywhereZoomAvailable;
	}

	public void setAnywhereZoomAvailable(Boolean anywhereZoomAvailable) {
		this.anywhereZoomAvailable = anywhereZoomAvailable;
	}

	public String getSeoUrl() {
		return seoUrl;
	}

	public void setSeoUrl(String seoUrl) {
		this.seoUrl = seoUrl;
	}

	public String getShopGuideId() {
		return shopGuideId;
	}

	public void setShopGuideId(String shopGuideId) {
		this.shopGuideId = shopGuideId;
	}

	public Boolean getLtlProduct() {
		return ltlProduct;
	}

	public void setLtlProduct(Boolean ltlProduct) {
		this.ltlProduct = ltlProduct;
	}

	public Boolean getIntlRestricted() {
		return intlRestricted;
	}

	public void setIntlRestricted(Boolean intlRestricted) {
		this.intlRestricted = intlRestricted;
	}

	public String getPorchServiceFamilyType() {
		return porchServiceFamilyType;
	}

	public void setPorchServiceFamilyType(String porchServiceFamilyType) {
		this.porchServiceFamilyType = porchServiceFamilyType;
	}

	public List<String> getPorchServiceFamilyCodes() {
		return porchServiceFamilyCodes;
	}

	public void setPorchServiceFamilyCodes(List<String> porchServiceFamilyCodes) {
		this.porchServiceFamilyCodes = porchServiceFamilyCodes;
	}

	public List<TabDTO> getProductTabs() {
		return productTabs;
	}

	public void setProductTabs(List<TabDTO> productTabs) {
		this.productTabs = productTabs;
	}

	public List<MediaDTO> getProductMedias() {
		return productMedias;
	}

	public void setProductMedias(List<MediaDTO> productMedias) {
		this.productMedias = productMedias;
	}

	public List<AttributeDTO> getProductAttributes() {
		return productAttributes;
	}

	public void setProductAttributes(List<AttributeDTO> productAttributes) {
		this.productAttributes = productAttributes;
	}

	public boolean isProductActive() {
		return isProductActive;
	}

	public void setProductActive(boolean isProductActive) {
		this.isProductActive = isProductActive;
	}

	public List<ProductDTO> getChildProductIds() {
		return childProductIds;
	}

	public void setChildProductIds(List<ProductDTO> childProductIds) {
		this.childProductIds = childProductIds;
	}

	public List<String> getChildSKUs() {
		return childSKUs;
	}

	public void setChildSKUs(List<String> childSKUs) {
		this.childSKUs = childSKUs;
	}

	public String getPrdKeywords() {
		return prdKeywords;
	}

	public void setPrdKeywords(String prdKeywords) {
		this.prdKeywords = prdKeywords;
	}

	public ImageDTO getProductImage() {
		return productImage;
	}

	public void setProductImage(ImageDTO productImage) {
		this.productImage = productImage;
	}

	public VendorDTO getVendorDTO() {
		return vendorDTO;
	}

	public void setVendorDTO(VendorDTO vendorDTO) {
		this.vendorDTO = vendorDTO;
	}

	public Boolean getGiftCert() {
		return giftCert;
	}

	public void setGiftCert(Boolean giftCert) {
		this.giftCert = giftCert;
	}

	public boolean isZoomFlag() {
		return zoomFlag;
	}

	public void setZoomFlag(boolean zoomFlag) {
		this.zoomFlag = zoomFlag;
	}

	public BazaarVoiceProductDTO getBazaarVoiceProductDTO() {
		return bazaarVoiceProductDTO;
	}

	public void setBazaarVoiceProductDTO(BazaarVoiceProductDTO bazaarVoiceProductDTO) {
		this.bazaarVoiceProductDTO = bazaarVoiceProductDTO;
	}

	public BrandDTO getBrandDTO() {
		return brandDTO;
	}

	public void setBrandDTO(BrandDTO brandDTO) {
		this.brandDTO = brandDTO;
	}

	/**
	 * @return the relatedCategories
	 */
	public final List<CategorySummaryDTO> getRelatedCategories() {
		return relatedCategories;
	}

	/**
	 * @param relatedCategories the relatedCategories to set
	 */
	public final void setRelatedCategories(List<CategorySummaryDTO> relatedCategories) {
		this.relatedCategories = relatedCategories;
	}

	@Override
	public String toString() {
		return "ProductDTO [productId=" + productId + ", siteId=" + siteId + ", isProductActive=" + isProductActive
				+ ", name=" + name + ", description=" + description + ", longDescription=" + longDescription
				+ ", prdKeywords=" + prdKeywords + ", productImage=" + productImage + ", vendorDTO=" + vendorDTO
				+ ", brandDTO=" + brandDTO + ", showImagesInCollection=" + showImagesInCollection + ", startDate="
				+ startDate + ", endDate=" + endDate + ", disableForeverPdpFlag=" + disableForeverPdpFlag
				+ ", enableDate=" + enableDate + ", primaryCategoryIdDefault=" + primaryCategoryIdDefault
				+ ", scene7Url=" + scene7Url + ", altImagesList=" + altImagesList + ", swatchImage=" + swatchImage
				+ ", zoomImageIndex=" + zoomImageIndex + ", zoomImage=" + zoomImage + ", anywhereZoomAvailable="
				+ anywhereZoomAvailable + ", productTabs=" + productTabs + ", productMedias=" + productMedias
				+ ", productAttributes=" + productAttributes + ", seoUrl=" + seoUrl + ", shopGuideId=" + shopGuideId
				+ ", ltlProduct=" + ltlProduct + ", intlRestricted=" + intlRestricted + ", porchServiceFamilyType="
				+ porchServiceFamilyType + ", porchServiceFamilyCodes=" + porchServiceFamilyCodes + ", childProductIds="
				+ childProductIds + ", childSKUs=" + childSKUs + ", giftCert=" + giftCert + ", zoomFlag=" + zoomFlag
				+ ", bazaarVoiceProductDTO=" + bazaarVoiceProductDTO + "]";
	}
	
}
