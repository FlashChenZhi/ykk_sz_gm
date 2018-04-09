package jp.co.daifuku.wms.YkkGMAX.Entities;

import java.math.BigDecimal;

public class TerminalViewEntity
{
    private String terminalNo = "";

    private String section = "";

    private String line = "";

    private BigDecimal unitWeightUpper = new BigDecimal(0);

    private BigDecimal unitWeightLower = new BigDecimal(0);

    private BigDecimal storageUpper = new BigDecimal(0);

    private BigDecimal storageLower = new BigDecimal(0);

    private BigDecimal shipUpper = new BigDecimal(0);

    private BigDecimal shipLower = new BigDecimal(0);

    // 0:all, 1:stock in, 2:stock out
    private int viewType = 0;

    public String getLine()
    {
	return line;
    }

    public String getSection()
    {
	return section;
    }

    public BigDecimal getShipLower()
    {
	return shipLower;
    }

    public BigDecimal getShipUpper()
    {
	return shipUpper;
    }

    public BigDecimal getStorageLower()
    {
	return storageLower;
    }

    public BigDecimal getStorageUpper()
    {
	return storageUpper;
    }

    public String getTerminalNo()
    {
	return terminalNo;
    }

    public BigDecimal getUnitWeightLower()
    {
	return unitWeightLower;
    }

    public BigDecimal getUnitWeightUpper()
    {
	return unitWeightUpper;
    }

    public int getViewType()
    {
	return viewType;
    }

    public void setLine(String line)
    {
	this.line = line;
    }

    public void setSection(String section)
    {
	this.section = section;
    }

    public void setShipLower(BigDecimal shipLower)
    {
	this.shipLower = shipLower;
    }

    public void setShipUpper(BigDecimal shipUpper)
    {
	this.shipUpper = shipUpper;
    }

    public void setStorageLower(BigDecimal storageLower)
    {
	this.storageLower = storageLower;
    }

    public void setStorageUpper(BigDecimal storageUpper)
    {
	this.storageUpper = storageUpper;
    }

    public void setTerminalNo(String terminalNo)
    {
	this.terminalNo = terminalNo;
    }

    public void setUnitWeightLower(BigDecimal unitWeightLower)
    {
	this.unitWeightLower = unitWeightLower;
    }

    public void setUnitWeightUpper(BigDecimal unitWeightUpper)
    {
	this.unitWeightUpper = unitWeightUpper;
    }

    public void setViewType(int viewType)
    {
	this.viewType = viewType;
    }
}
