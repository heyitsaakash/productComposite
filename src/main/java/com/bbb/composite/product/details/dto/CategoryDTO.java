/**
 * 
 */
package com.bbb.composite.product.details.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author nbisht
 *
 */
public class CategoryDTO implements Serializable {
	
	
	private static final long serialVersionUID = -8142777338465884695L;

	private String categoryId;
	
	private String siteId;
	
	private String catalogId;
	
	private String displayName;
	
	private String description;
	
	private String longDescription;
	
	private Integer categoryType;
	
	private Boolean isRootCategory;
	
	private Instant creationDate;
	
	private Instant startDate;
	
	private Instant endDate;
	
	private Boolean isCollege;
	
	private String nodeType;
	
	private Boolean isPhantomCategory;
	
	private Boolean isDisabled;
	
	private String shopGuideId;
	
	private String smallImage;
	
	private String rank;
	
	private Integer gsImageOrientation;
	
	private SeoDTO seoInfo;
	
	private Boolean isActive;
	
	
	/**
	 * @return the categoryId
	 */
	public final String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public final void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the catalogId
	 */
	public final String getCatalogId() {
		return catalogId;
	}
	/**
	 * @param catalogId the catalogId to set
	 */
	public final void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	/**
	 * @return the displayName
	 */
	public final String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public final void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public final void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the longDescription
	 */
	public final String getLongDescription() {
		return longDescription;
	}
	/**
	 * @param longDescription the longDescription to set
	 */
	public final void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	/**
	 * @return the categoryType
	 */
	public final Integer getCategoryType() {
		return categoryType;
	}
	/**
	 * @param categoryType the categoryType to set
	 */
	public final void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}
	/**
	 * @return the isRootCategory
	 */
	public final Boolean getIsRootCategory() {
		return isRootCategory;
	}
	/**
	 * @param isRootCategory the isRootCategory to set
	 */
	public final void setIsRootCategory(Boolean isRootCategory) {
		this.isRootCategory = isRootCategory;
	}
	/**
	 * @return the creationDate
	 */
	public final Instant getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public final void setCreationDate(Instant creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the startDate
	 */
	public final Instant getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public final void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public final Instant getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public final void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the isCollege
	 */
	public final Boolean getIsCollege() {
		return isCollege;
	}
	/**
	 * @param isCollege the isCollege to set
	 */
	public final void setIsCollege(Boolean isCollege) {
		this.isCollege = isCollege;
	}
	/**
	 * @return the nodeType
	 */
	public final String getNodeType() {
		return nodeType;
	}
	/**
	 * @param nodeType the nodeType to set
	 */
	public final void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	/**
	 * @return the isPhantomCategory
	 */
	public final Boolean getIsPhantomCategory() {
		return isPhantomCategory;
	}
	/**
	 * @param isPhantomCategory the isPhantomCategory to set
	 */
	public final void setIsPhantomCategory(Boolean isPhantomCategory) {
		this.isPhantomCategory = isPhantomCategory;
	}
	/**
	 * @return the isDisabled
	 */
	public final Boolean getIsDisabled() {
		return isDisabled;
	}
	/**
	 * @param isDisabled the isDisabled to set
	 */
	public final void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
	/**
	 * @return the shopGuideId
	 */
	public final String getShopGuideId() {
		return shopGuideId;
	}
	/**
	 * @param shopGuideId the shopGuideId to set
	 */
	public final void setShopGuideId(String shopGuideId) {
		this.shopGuideId = shopGuideId;
	}
	/**
	 * @return the smallImage
	 */
	public final String getSmallImage() {
		return smallImage;
	}
	/**
	 * @param smallImage the smallImage to set
	 */
	public final void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}
	/**
	 * @return the rank
	 */
	public final String getRank() {
		return rank;
	}
	/**
	 * @param rank the rank to set
	 */
	public final void setRank(String rank) {
		this.rank = rank;
	}
	/**
	 * @return the gsImageOrientation
	 */
	public final Integer getGsImageOrientation() {
		return gsImageOrientation;
	}
	/**
	 * @param gsImageOrientation the gsImageOrientation to set
	 */
	public final void setGsImageOrientation(Integer gsImageOrientation) {
		this.gsImageOrientation = gsImageOrientation;
	}
	/**
	 * @return the siteId
	 */
	public final String getSiteId() {
		return siteId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public final void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	/**
	 * @return the seoInfo
	 */
	public final SeoDTO getSeoInfo() {
		return seoInfo;
	}
	/**
	 * @param seoInfo the seoInfo to set
	 */
	public final void setSeoInfo(SeoDTO seoInfo) {
		this.seoInfo = seoInfo;
	}
	/**
	 * @return the isActive
	 */
	public final Boolean getIsActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public final void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
