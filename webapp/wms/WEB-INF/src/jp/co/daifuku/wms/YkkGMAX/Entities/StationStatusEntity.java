package jp.co.daifuku.wms.YkkGMAX.Entities;

public class StationStatusEntity
{

	private String stationNo = "";

	private String stationName = "";

	private String transferMode = "";

	private String workMode = "";

	private String machineStatus = "";

	private String workStatus = "";

	private int workCount = 0;

	public String getMachineStatus()
	{
		return machineStatus;
	}

	public void setMachineStatus(String machineStatus)
	{
		this.machineStatus = machineStatus;
	}

	public String getStationName()
	{
		return stationName;
	}

	public void setStationName(String stationName)
	{
		this.stationName = stationName;
	}

	public String getStationNo()
	{
		return stationNo;
	}

	public void setStationNo(String stationNo)
	{
		this.stationNo = stationNo;
	}

	public String getTransferMode()
	{
		return transferMode;
	}

	public void setTransferMode(String transferMode)
	{
		this.transferMode = transferMode;
	}

	public int getWorkCount()
	{
		return workCount;
	}

	public void setWorkCount(int workCount)
	{
		this.workCount = workCount;
	}

	public String getWorkMode()
	{
		return workMode;
	}

	public void setWorkMode(String workMode)
	{
		this.workMode = workMode;
	}

	public String getWorkStatus()
	{
		return workStatus;
	}

	public void setWorkStatus(String workStatus)
	{
		this.workStatus = workStatus;
	}
}
