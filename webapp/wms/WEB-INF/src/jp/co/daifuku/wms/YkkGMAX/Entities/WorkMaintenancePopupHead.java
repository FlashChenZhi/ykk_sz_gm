package jp.co.daifuku.wms.YkkGMAX.Entities;

public class WorkMaintenancePopupHead
{
	private String transferType = "";
	
	private String station = "";
	
	private String division = "";

	public String getDivision()
	{
		return division;
	}

	public void setDivision(String division)
	{
		this.division = division;
	}

	public String getStation()
	{
		return station;
	}

	public void setStation(String station)
	{
		this.station = station;
	}

	public String getTransferType()
	{
		return transferType;
	}

	public void setTransferType(String transferType)
	{
		this.transferType = transferType;
	}
}
