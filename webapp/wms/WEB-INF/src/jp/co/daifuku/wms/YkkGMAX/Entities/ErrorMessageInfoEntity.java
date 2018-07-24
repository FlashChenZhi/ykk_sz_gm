package jp.co.daifuku.wms.YkkGMAX.Entities;

public class ErrorMessageInfoEntity
{
	private String time = "";
	
	private String messageType = "";
	
	private String errorMessage = "";

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getMessageType()
	{
		return messageType;
	}

	public void setMessageType(String messageType)
	{
		this.messageType = messageType;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}
}
