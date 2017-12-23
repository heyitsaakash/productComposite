package com.bbb.composite.product.details.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bbb.composite.product.details.dto.product.BazaarVoiceProductDTO;
import com.bbb.composite.product.details.dto.product.MediaDTO;
import com.bbb.composite.product.details.dto.product.TabDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * ProductCompositeDTO attributes specified here.
 * 
 * @author psh111
 *
 */
public class ProductCompositeDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2749573216411206218L;

	private String productId;
	
	private String name;
	
	private BrandCompositeDTO productBrand;

	private String shortDescription;

	private String longDescription;
	
	private String seoURL;
	
	private String siteId;

	private ImageDTO productImages;
	
	private List<MediaDTO> productMedia;
	
	private ProductPriceCompositeDTO price;
	
	private Map<String, List<AttributeDTO>> attributesList;
	
	private List<TabDTO> productTabs;

	private String shopGuideId;

	private Boolean intlRestricted;

	private String displayShipMsg;

	private List<SkuCompositeDTO> skus;

	private BazaarVoiceProductDTO reviews;
	
	private VariantOptionsDTO variantOptions;
	
	private List<String> altImagesList;
	
	private List<CategorySummaryDTO> categoryList;

	@JsonIgnore
	private List<String> childSKUs;
	
	@JsonIgnore
	private boolean isFallBackCalled;
	
	public VariantOptionsDTO getVariantOptions() {
		return variantOptions;
	}

	public void setVariantOptions(VariantOptionsDTO variantOptions) {
		this.variantOptions = variantOptions;
	}

	public boolean isFallBackCalled() {
		return isFallBackCalled;
	}

	public void setFallBackCalled(boolean isFallBackCalled) {
		this.isFallBackCalled = isFallBackCalled;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getSeoURL() {
		return seoURL;
	}

	public void setSeoURL(String seoURL) {
		this.seoURL = seoURL;
	}

	public String getShopGuideId() {
		return shopGuideId;
	}

	public void setShopGuideId(String shopGuideId) {
		this.shopGuideId = shopGuideId;
	}

	public Boolean getIntlRestricted() {
		return intlRestricted;
	}

	public void setIntlRestricted(Boolean intlRestricted) {
		this.intlRestricted = intlRestricted;
	}

	public List<TabDTO> getProductTabs() {
		return productTabs;
	}

	public void setProductTabs(List<TabDTO> productTabs) {
		this.productTabs = productTabs;
	}

	public List<MediaDTO> getProductMedia() {
		return productMedia;
	}

	public void setProductMedia(List<MediaDTO> productMedia) {
		this.productMedia = productMedia;
	}

	public Map<String, List<AttributeDTO>> getAttributesList() {
		return attributesList;
	}

	public void setAttributesList(Map<String, List<AttributeDTO>> attributesList) {
		this.attributesList = attributesList;
	}

	public ImageDTO getProductImages() {
		return productImages;
	}

	public void setProductImages(ImageDTO productImages) {
		this.productImages = productImages;
	}

	public BazaarVoiceProductDTO getReviews() {
		return reviews;
	}

	public void setReviews(BazaarVoiceProductDTO reviews) {
		this.reviews = reviews;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public ProductPriceCompositeDTO getPrice() {
		return price;
	}

	public void setPrice(ProductPriceCompositeDTO price) {
		this.price = price;
	}

	public List<SkuCompositeDTO> getSkus() {
		return skus;
	}

	public void setSkus(List<SkuCompositeDTO> skus) {
		this.skus = skus;
	}

	public List<String> getChildSKUs() {
		return childSKUs;
	}

	public void setChildSKUs(List<String> childSKUs) {
		this.childSKUs = childSKUs;
	}

	public String getDisplayShipMsg() {
		return displayShipMsg;
	}

	public void setDisplayShipMsg(String displayShipMsg) {
		this.displayShipMsg = displayShipMsg;
	}
	
	public BrandCompositeDTO getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(BrandCompositeDTO productBrand) {
		this.productBrand = productBrand;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	public List<String> getAltImagesList() {
		return altImagesList;
	}

	public void setAltImagesList(List<String> altImagesList) {
		this.altImagesList = altImagesList;
	}

	/**
	 * @return the categoryList
	 */
	public final List<CategorySummaryDTO> getCategoryList() {
		return categoryList;
	}

	/**
	 * @param categoryList the categoryList to set
	 */
	public final void setCategoryList(List<CategorySummaryDTO> categoryList) {
		this.categoryList = categoryList;
	}

}
