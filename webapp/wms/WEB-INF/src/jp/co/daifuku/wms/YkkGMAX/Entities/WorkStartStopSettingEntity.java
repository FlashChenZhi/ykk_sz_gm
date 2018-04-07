package jp.co.daifuku.wms.YkkGMAX.Entities;

public class WorkStartStopSettingEntity
{
	private String controllerNo = "";

	private String systemStatus = "";

	private int workCount = 0;

	public String getControllerNo()
	{
		return controllerNo;
	}

	public void setControllerNo(String controllerNo)
	{
		this.controllerNo = controllerNo;
	}

	public String getSystemStatus()
	{
		return systemStatus;
	}

	public void setSystemStatus(String systemStatus)
	{
		this.systemStatus = systemStatus;
	}

	public int getWorkCount()
	{
		return workCount;
	}

	public void setWorkCount(int workCount)
	{
		this.workCount = workCount;
	}
}
