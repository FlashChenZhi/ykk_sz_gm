package jp.co.daifuku.wms.YkkGMAX.Entities;

import java.math.BigDecimal;

public class ItemViewEntity
{
	private String itemCode = "";
	private String itemName1 = "";
	private String itemName2 = "";
	private String itemName3 = "";
	private String manageItemFlag = "";
	private BigDecimal masterUnit = new BigDecimal(0);
	private int limitQty = 0;
	private int unitQty = 0;
	private int autoDepoMaxCount = 0;
	private int autoDepoMinCount = 0;
	private String removeConventFlag = "";
	private String unitFlag = "";
	private String bagFlag = "";

	public String getBagFlag()
	{
		return bagFlag;
	}

	public void setBagFlag(String bagFlag)
	{
		this.bagFlag = bagFlag;
	}

	public BigDecimal getMasterUnit()
	{
		return masterUnit;
	}

	public void setMasterUnit(BigDecimal masterUnit)
	{
		this.masterUnit = masterUnit;
	}

	public int getLimitQty()
	{
		return limitQty;
	}

	public void setLimitQty(int limitQty)
	{
		this.limitQty = limitQty;
	}

	public int getUnitQty()
	{
		return unitQty;
	}

	public void setUnitQty(int unitQty)
	{
		this.unitQty = unitQty;
	}

	public int getAutoDepoMaxCount()
	{
		return autoDepoMaxCount;
	}

	public void setAutoDepoMaxCount(int autoDepoMaxCount)
	{
		this.autoDepoMaxCount = autoDepoMaxCount;
	}

	public int getAutoDepoMinCount()
	{
		return autoDepoMinCount;
	}

	public void setAutoDepoMinCount(int autoDepoMinCount)
	{
		this.autoDepoMinCount = autoDepoMinCount;
	}

	public String getRemoveConventFlag()
	{
		return removeConventFlag;
	}

	public void setRemoveConventFlag(String removeConventFlag)
	{
		this.removeConventFlag = removeConventFlag;
	}

	public String getUnitFlag()
	{
		return unitFlag;
	}

	public void setUnitFlag(String unitFlag)
	{
		this.unitFlag = unitFlag;
	}

	public String getManageItemFlag()
	{
		return manageItemFlag;
	}

	public void setManageItemFlag(String manageItemFlag)
	{
		this.manageItemFlag = manageItemFlag;
	}

	public String getItemCode()
	{
		return itemCode;
	}

	public void setItemCode(String itemCode)
	{
		this.itemCode = itemCode;
	}

	public String getItemName1()
	{
		return itemName1;
	}

	public void setItemName1(String itemName1)
	{
		this.itemName1 = itemName1;
	}

	public String getItemName2()
	{
		return itemName2;
	}

	public void setItemName2(String itemName2)
	{
		this.itemName2 = itemName2;
	}

	public String getItemName3()
	{
		return itemName3;
	}

	public void setItemName3(String itemName3)
	{
		this.itemName3 = itemName3;
	}
}
