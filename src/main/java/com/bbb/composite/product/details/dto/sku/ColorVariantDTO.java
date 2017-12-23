package com.bbb.composite.product.details.dto.sku;

import java.io.Serializable;

/**
 * ColorVariantDTO attributes specified here.
 *
 * @author psh111
 */
public class ColorVariantDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8062240487664678099L;
	private String displayName;
	private String colorCode;
	private String swatchImagePath;
	private String smallImagePath;
	private String thumbnailImagePath;
	private String largeImagePath;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getSwatchImagePath() {
		return swatchImagePath;
	}
	public void setSwatchImagePath(String swatchImagePath) {
		this.swatchImagePath = swatchImagePath;
	}
	public String getSmallImagePath() {
		return smallImagePath;
	}
	public void setSmallImagePath(String smallImagePath) {
		this.smallImagePath = smallImagePath;
	}
	public String getThumbnailImagePath() {
		return thumbnailImagePath;
	}
	public void setThumbnailImagePath(String thumbnailImagePath) {
		this.thumbnailImagePath = thumbnailImagePath;
	}
	public String getLargeImagePath() {
		return largeImagePath;
	}
	public void setLargeImagePath(String largeImagePath) {
		this.largeImagePath = largeImagePath;
	}
	@Override
	public String toString() {
		return "ColorVariantDTO [displayName=" + displayName + ", colorCode=" + colorCode + ", swatchImagePath="
				+ swatchImagePath + ", smallImagePath=" + smallImagePath + ", thumbnailImagePath=" + thumbnailImagePath
				+ ", largeImagePath=" + largeImagePath + "]";
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((colorCode == null) ? 0 : colorCode.hashCode());
		result = PRIME * result + ((displayName == null) ? 0 : displayName.hashCode());
		result = PRIME * result + ((largeImagePath == null) ? 0 : largeImagePath.hashCode());
		result = PRIME * result + ((smallImagePath == null) ? 0 : smallImagePath.hashCode());
		result = PRIME * result + ((swatchImagePath == null) ? 0 : swatchImagePath.hashCode());
		result = PRIME * result + ((thumbnailImagePath == null) ? 0 : thumbnailImagePath.hashCode());
		return result;
	}
	@SuppressWarnings("squid:S3776")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColorVariantDTO other = (ColorVariantDTO) obj;
		if (colorCode == null) {
			if (other.colorCode != null)
				return false;
		} else if (!colorCode.equals(other.colorCode))
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (largeImagePath == null) {
			if (other.largeImagePath != null)
				return false;
		} else if (!largeImagePath.equals(other.largeImagePath))
			return false;
		if (smallImagePath == null) {
			if (other.smallImagePath != null)
				return false;
		} else if (!smallImagePath.equals(other.smallImagePath))
			return false;
		if (swatchImagePath == null) {
			if (other.swatchImagePath != null)
				return false;
		} else if (!swatchImagePath.equals(other.swatchImagePath))
			return false;
		if (thumbnailImagePath == null) {
			if (other.thumbnailImagePath != null)
				return false;
		} else if (!thumbnailImagePath.equals(other.thumbnailImagePath))
			return false;
		return true;
	}
	
	
}
