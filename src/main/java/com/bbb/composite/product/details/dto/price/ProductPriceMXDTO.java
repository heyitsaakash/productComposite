/**
 * 
 */
package com.bbb.composite.product.details.dto.price;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @author atiw14
 *
 */

public class ProductPriceMXDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6549597662171376273L;

	private String productId;
	
	private String siteId;

	private String priceRangeDescription;
	
	private BigDecimal productLowSalePrice;
	
	private BigDecimal productHighSalePrice;
	
	private BigDecimal productLowListPrice;
	
	private BigDecimal productHighListPrice;
	
	private Boolean productInCartFlag;
	
	private String productLabelCode;
	
	private BigDecimal productInCartPrice;
	
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the siteId
	 */
	public String getSiteId() {
		return siteId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	/**
	 * @return the priceRangeDescription
	 */
	public String getPriceRangeDescription() {
		return priceRangeDescription;
	}
	/**
	 * @param priceRangeDescription the priceRangeDescription to set
	 */
	public void setPriceRangeDescription(String priceRangeDescription) {
		this.priceRangeDescription = priceRangeDescription;
	}

	
	/**
	 * @return the productLowSalePrice
	 */
	public BigDecimal getProductLowSalePrice() {
		return productLowSalePrice;
	}
	/**
	 * @param productLowSalePrice the productLowSalePrice to set
	 */
	public void setProductLowSalePrice(BigDecimal productLowSalePrice) {
		this.productLowSalePrice = productLowSalePrice;
	}
	/**
	 * @return the productHighSalePrice
	 */
	public BigDecimal getProductHighSalePrice() {
		return productHighSalePrice;
	}
	/**
	 * @param productHighSalePrice the productHighSalePrice to set
	 */
	public void setProductHighSalePrice(BigDecimal productHighSalePrice) {
		this.productHighSalePrice = productHighSalePrice;
	}
	/**
	 * @return the productLowListPrice
	 */
	public BigDecimal getProductLowListPrice() {
		return productLowListPrice;
	}
	/**
	 * @param productLowListPrice the productLowListPrice to set
	 */
	public void setProductLowListPrice(BigDecimal productLowListPrice) {
		this.productLowListPrice = productLowListPrice;
	}
	/**
	 * @return the productHighListPrice
	 */
	public BigDecimal getProductHighListPrice() {
		return productHighListPrice;
	}
	/**
	 * @param productHighListPrice the productHighListPrice to set
	 */
	public void setProductHighListPrice(BigDecimal productHighListPrice) {
		this.productHighListPrice = productHighListPrice;
	}
	/**
	 * @return the productInCartFlag
	 */
	public Boolean getProductInCartFlag() {
		return productInCartFlag;
	}
	
	/**
	 * @param productInCartFlag the productInCartFlag to set
	 */
	public void setProductInCartFlag(Boolean productInCartFlag) {
		this.productInCartFlag = productInCartFlag;
	}
	/**
	 * @return the productLabelCode
	 */
	public String getProductLabelCode() {
		return productLabelCode;
	}
	/**
	 * @param productLabelCode the productLabelCode to set
	 */
	public void setProductLabelCode(String productLabelCode) {
		this.productLabelCode = productLabelCode;
	}
	/**
	 * @return the productInCartPrice
	 */
	public BigDecimal getProductInCartPrice() {
		return productInCartPrice;
	}
	/**
	 * @param productInCartPrice the productInCartPrice to set
	 */
	public void setProductInCartPrice(BigDecimal productInCartPrice) {
		this.productInCartPrice = productInCartPrice;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((productId == null) ? 0 : productId.hashCode());
		result = PRIME * result + ((siteId == null) ? 0 : siteId.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("squid:S3776")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductPriceMXDTO other = (ProductPriceMXDTO) obj;
		if (priceRangeDescription == null) {
			if (other.priceRangeDescription != null)
				return false;
		} else if (!priceRangeDescription.equals(other.priceRangeDescription))
			return false;
		if (productHighListPrice == null) {
			if (other.productHighListPrice != null)
				return false;
		} else if (!productHighListPrice.equals(other.productHighListPrice))
			return false;
		if (productHighSalePrice == null) {
			if (other.productHighSalePrice != null)
				return false;
		} else if (!productHighSalePrice.equals(other.productHighSalePrice))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productInCartFlag == null) {
			if (other.productInCartFlag != null)
				return false;
		} else if (!productInCartFlag.equals(other.productInCartFlag))
			return false;
		if (productInCartPrice == null) {
			if (other.productInCartPrice != null)
				return false;
		} else if (!productInCartPrice.equals(other.productInCartPrice))
			return false;
		if (productLabelCode == null) {
			if (other.productLabelCode != null)
				return false;
		} else if (!productLabelCode.equals(other.productLabelCode))
			return false;
		if (productLowListPrice == null) {
			if (other.productLowListPrice != null)
				return false;
		} else if (!productLowListPrice.equals(other.productLowListPrice))
			return false;
		if (productLowSalePrice == null) {
			if (other.productLowSalePrice != null)
				return false;
		} else if (!productLowSalePrice.equals(other.productLowSalePrice))
			return false;
		if (siteId == null) {
			if (other.siteId != null)
				return false;
		} else if (!siteId.equals(other.siteId))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductPriceMXEntity [productId=");
		builder.append(productId);
		builder.append(", siteId=");
		builder.append(siteId);
		builder.append(", priceRangeDescription=");
		builder.append(priceRangeDescription);
		builder.append(", productLowSalePrice=");
		builder.append(productLowSalePrice);
		builder.append(", productHighSalePrice=");
		builder.append(productHighSalePrice);
		builder.append(", productLowListPrice=");
		builder.append(productLowListPrice);
		builder.append(", productHighListPrice=");
		builder.append(productHighListPrice);
		builder.append(", productInCartFlag=");
		builder.append(productInCartFlag);
		builder.append(", productLabelCode=");
		builder.append(productLabelCode);
		builder.append(", productInCartPrice=");
		builder.append(productInCartPrice);
		builder.append("]");
		return builder.toString();
	}
	

}
