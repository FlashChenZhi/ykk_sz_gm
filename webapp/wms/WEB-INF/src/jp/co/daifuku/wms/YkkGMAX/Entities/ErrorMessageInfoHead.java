package jp.co.daifuku.wms.YkkGMAX.Entities;

public class ErrorMessageInfoHead
{
	private String messageType = "";
	
	private String dateFrom = "";
	
	private String dateTo = "";
	
	private String timeFrom = "";
	
	private String timeTo = "";

	public String getMessageType()
	{
		return messageType;
	}

	public void setMessageType(String messageType)
	{
		this.messageType = messageType;
	}

	public String getTimeFrom()
	{
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom)
	{
		this.timeFrom = timeFrom;
	}

	public String getTimeTo()
	{
		return timeTo;
	}

	public void setTimeTo(String timeTo)
	{
		this.timeTo = timeTo;
	}

	public String getDateFrom()
	{
		return dateFrom;
	}

	public void setDateFrom(String dateFrom)
	{
		this.dateFrom = dateFrom;
	}

	public String getDateTo()
	{
		return dateTo;
	}

	public void setDateTo(String dateTo)
	{
		this.dateTo = dateTo;
	}
}
