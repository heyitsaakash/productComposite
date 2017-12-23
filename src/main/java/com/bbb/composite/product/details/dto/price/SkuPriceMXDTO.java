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

public class SkuPriceMXDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5949809060315772043L;

	private String skuId;
	
	private String siteId;

	private BigDecimal skuListPrice;
	
	private BigDecimal skuSalePrice;
	
	private Boolean skuInCartFlag;
	
	private String skuLabelCode;
	
	private BigDecimal skuInCartPrice;
	
	/**
	 * @return the skuSalePrice
	 */
	public BigDecimal getSkuSalePrice() {
		return skuSalePrice;
	}

	/**
	 * @param skuSalePrice the skuSalePrice to set
	 */
	public void setSkuSalePrice(BigDecimal skuSalePrice) {
		this.skuSalePrice = skuSalePrice;
	}

	/**
	 * @return the skuInCartFlag
	 */
	public Boolean getSkuInCartFlag() {
		return skuInCartFlag;
	}

	/**
	 * @param skuInCartFlag the skuInCartFlag to set
	 */
	public void setSkuInCartFlag(Boolean skuInCartFlag) {
		this.skuInCartFlag = skuInCartFlag;
	}

	/**
	 * @return the skuLabelCode
	 */
	public String getSkuLabelCode() {
		return skuLabelCode;
	}

	/**
	 * @param skuLabelCode the skuLabelCode to set
	 */
	public void setSkuLabelCode(String skuLabelCode) {
		this.skuLabelCode = skuLabelCode;
	}

	/**
	 * @return the skuInCartPrice
	 */
	public BigDecimal getSkuInCartPrice() {
		return skuInCartPrice;
	}

	/**
	 * @param skuInCartPrice the skuInCartPrice to set
	 */
	public void setSkuInCartPrice(BigDecimal skuInCartPrice) {
		this.skuInCartPrice = skuInCartPrice;
	}

	/**
	 * @param skuListPrice the skuListPrice to set
	 */
	public void setSkuListPrice(BigDecimal skuListPrice) {
		this.skuListPrice = skuListPrice;
	}
	
	/**
	 * @return the skuId
	 */
	public String getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId the skuId to set
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((siteId == null) ? 0 : siteId.hashCode());
		result = PRIME * result + ((skuId == null) ? 0 : skuId.hashCode());
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
		SkuPriceMXDTO other = (SkuPriceMXDTO) obj;
		if (siteId == null) {
			if (other.siteId != null)
				return false;
		} else if (!siteId.equals(other.siteId))
			return false;
		if (skuId == null) {
			if (other.skuId != null)
				return false;
		} else if (!skuId.equals(other.skuId))
			return false;
		if (skuInCartFlag == null) {
			if (other.skuInCartFlag != null)
				return false;
		} else if (!skuInCartFlag.equals(other.skuInCartFlag))
			return false;
		if (skuInCartPrice == null) {
			if (other.skuInCartPrice != null)
				return false;
		} else if (!skuInCartPrice.equals(other.skuInCartPrice))
			return false;
		if (skuLabelCode == null) {
			if (other.skuLabelCode != null)
				return false;
		} else if (!skuLabelCode.equals(other.skuLabelCode))
			return false;
		if (skuListPrice == null) {
			if (other.skuListPrice != null)
				return false;
		} else if (!skuListPrice.equals(other.skuListPrice))
			return false;
		if (skuSalePrice == null) {
			if (other.skuSalePrice != null)
				return false;
		} else if (!skuSalePrice.equals(other.skuSalePrice))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SkuPriceMXEntity [skuId=");
		builder.append(skuId);
		builder.append(", siteId=");
		builder.append(siteId);
		builder.append(", skuListPrice=");
		builder.append(skuListPrice);
		builder.append(", skuSalePrice=");
		builder.append(skuSalePrice);
		builder.append(", skuInCartFlag=");
		builder.append(skuInCartFlag);
		builder.append(", skuLabelCode=");
		builder.append(skuLabelCode);
		builder.append(", skuInCartPrice=");
		builder.append(skuInCartPrice);
		builder.append("]");
		return builder.toString();
	}
	
	
}
