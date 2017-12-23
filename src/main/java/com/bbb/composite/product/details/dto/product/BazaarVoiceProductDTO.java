package com.bbb.composite.product.details.dto.product;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * BazaarVoiceProductDTO attributes specified here
 * 
 * @author psh111
 *
 */
public class BazaarVoiceProductDTO implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2650089833728307097L;

	private BigDecimal averageOverallRating;

	private Integer totalReviewCount;

	private String externalId;
	
	private boolean ratingAvailable;
	
	public BigDecimal getAverageOverallRating() {
		return averageOverallRating;
	}

	public void setAverageOverallRating(BigDecimal averageOverallRating) {
		if (averageOverallRating != null) {
			this.setRatingAvailable(true);
		}
		this.averageOverallRating = averageOverallRating;
	}

	public Integer getTotalReviewCount() {
		return totalReviewCount;
	}

	public void setTotalReviewCount(Integer totalReviewCount) {
		this.totalReviewCount = totalReviewCount;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public boolean isRatingAvailable() {
		return ratingAvailable;
	}

	public void setRatingAvailable(boolean ratingAvailable) {
		this.ratingAvailable = ratingAvailable;
	}
}
