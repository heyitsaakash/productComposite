package com.bbb.composite.product.details.dto.sku;

import java.io.Serializable;

/**
 * ShippingDTO attributes specified here.
 *
 * @author psh111
 */
public class ShippingMethodDTO  implements Serializable {

	private static final long serialVersionUID = 6522184134417749805L;

	private String shipMethodCd;
	
	private String shipMethodName;
	
	private String shipMethodDescription;

	private Integer minDaysToShip;
	
	private Integer maxDaysToShip;
	
	private Boolean isltlShipMethod;
	
	private Integer minDaysToShipVDC;
	
	private Integer maxDaysToShipVDC;

	public String getShipMethodCd() {
		return shipMethodCd;
	}

	public void setShipMethodCd(String shipMethodCd) {
		this.shipMethodCd = shipMethodCd;
	}

	public String getShipMethodName() {
		return shipMethodName;
	}

	public void setShipMethodName(String shipMethodName) {
		this.shipMethodName = shipMethodName;
	}

	public Integer getMinDaysToShip() {
		return minDaysToShip;
	}

	public void setMinDaysToShip(Integer minDaysToShip) {
		this.minDaysToShip = minDaysToShip;
	}

	public Integer getMaxDaysToShip() {
		return maxDaysToShip;
	}

	public void setMaxDaysToShip(Integer maxDaysToShip) {
		this.maxDaysToShip = maxDaysToShip;
	}

	public Boolean getIsltlShipMethod() {
		return isltlShipMethod;
	}

	public void setIsltlShipMethod(Boolean isltlShipMethod) {
		this.isltlShipMethod = isltlShipMethod;
	}

	public Integer getMinDaysToShipVDC() {
		return minDaysToShipVDC;
	}

	public void setMinDaysToShipVDC(Integer minDaysToShipVDC) {
		this.minDaysToShipVDC = minDaysToShipVDC;
	}

	public Integer getMaxDaysToShipVDC() {
		return maxDaysToShipVDC;
	}

	public void setMaxDaysToShipVDC(Integer maxDaysToShipVDC) {
		this.maxDaysToShipVDC = maxDaysToShipVDC;
	}

	public String getShipMethodDescription() {
		return shipMethodDescription;
	}

	public void setShipMethodDescription(String shipMethodDescription) {
		this.shipMethodDescription = shipMethodDescription;
	}


	
}
