package com.bbb.composite.product.details.dto;

import java.io.Serializable;

import com.bbb.composite.product.details.dto.sku.ColorVariantDTO;
import com.bbb.composite.product.details.dto.sku.FinishVariantDTO;
import com.bbb.composite.product.details.dto.sku.SizeVariantDTO;

/**
 * SKUCompositeVariantValuesDTO attributes specified here.
 * 
 * @author psh111
 *
 */
public class SKUCompositeVariantValuesDTO implements Serializable{

	private static final long serialVersionUID = -2367854851590900698L;

	private SizeVariantDTO size;
	
	private ColorVariantDTO color;
	
	private FinishVariantDTO finish;

	public SizeVariantDTO getSize() {
		return size;
	}

	public void setSize(SizeVariantDTO size) {
		this.size = size;
	}

	public ColorVariantDTO getColor() {
		return color;
	}

	public void setColor(ColorVariantDTO color) {
		this.color = color;
	}

	public FinishVariantDTO getFinish() {
		return finish;
	}

	public void setFinish(FinishVariantDTO finish) {
		this.finish = finish;
	}

	@Override
	public String toString() {
		return "SKUCompositeVariantValuesDTO [size=" + size + ", color=" + color + ", finish=" + finish + "]";
	}
	
	
	
	
}	
