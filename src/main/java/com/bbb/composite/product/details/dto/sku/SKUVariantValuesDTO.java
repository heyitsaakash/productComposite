package com.bbb.composite.product.details.dto.sku;

import java.io.Serializable;

/**
 * SKUVariantValuesDTO attributes specified here.
 *
 * @author psh111
 */
public class SKUVariantValuesDTO implements Serializable{

	private static final long serialVersionUID = -2367854851590900698L;

	private SizeVariantDTO sizeVariant;
	
	private ColorVariantDTO colorVariant;
	
	private FinishVariantDTO finishVariant;
	
	public SizeVariantDTO getSizeVariant() {
		return sizeVariant;
	}
	public void setSizeVariant(SizeVariantDTO sizeVariant) {
		this.sizeVariant = sizeVariant;
	}
	public ColorVariantDTO getColorVariant() {
		return colorVariant;
	}
	public void setColorVariant(ColorVariantDTO colorVariant) {
		this.colorVariant = colorVariant;
	}
	public FinishVariantDTO getFinishVariant() {
		return finishVariant;
	}
	public void setFinishVariant(FinishVariantDTO finishVariant) {
		this.finishVariant = finishVariant;
	}
	@Override
	public String toString() {
		return "SKUVariantValuesDTO [sizeVariant=" + sizeVariant + ", colorVariant=" + colorVariant
				+ ", finishVariant=" + finishVariant + "]";
	}
	
	
	
}	
