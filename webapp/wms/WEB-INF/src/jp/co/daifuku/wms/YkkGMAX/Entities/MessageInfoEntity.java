package jp.co.daifuku.wms.YkkGMAX.Entities;

public class MessageInfoEntity
{
	private String time = "";
	
	private String messageType = "";
	
	private String message = "";

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
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
