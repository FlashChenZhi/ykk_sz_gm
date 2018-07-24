package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import jp.co.daifuku.bluedog.ui.control.FixedListCell;
import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.MessageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.MessageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class MessageInfoListProxy
{
	public MessageInfoListProxy( FixedListCell head,ListCell list)
	{
		this.head = head;
		this.list = list;
	}
	public MessageInfoListProxy(ListCell list)
	{
		this.head = null;
		this.list = list;
	}
	private ListCell list;
	
	private FixedListCell head;
	
	private int HEAD_MESSAGE_TYPE_ROW = 1;
	
	private int HEAD_MESSAGE_TYPE_COLUMN = 1;
	
	private int HEAD_TIME_RANGE_ROW = 2;
	
	private int HEAD_TIME_RANGE_COLUMN = 1;
	
	private int NO_COLUMN = 1;
	
	private int TIME_COLUMN = 2;
	
	private int MESSAGE_TYPE_COLUMN = 3;
	
	private int MESSAGE_COLUMN = 4;

	public int getMESSAGE_COLUMN()
	{
		return MESSAGE_COLUMN;
	}
	public int getHEAD_MESSAGE_TYPE_COLUMN()
	{
		return HEAD_MESSAGE_TYPE_COLUMN;
	}
	public int getHEAD_MESSAGE_TYPE_ROW()
	{
		return HEAD_MESSAGE_TYPE_ROW;
	}
	public int getHEAD_TIME_RANGE_COLUMN()
	{
		return HEAD_TIME_RANGE_COLUMN;
	}
	public int getHEAD_TIME_RANGE_ROW()
	{
		return HEAD_TIME_RANGE_ROW;
	}
	public int getMESSAGE_TYPE_COLUMN()
	{
		return MESSAGE_TYPE_COLUMN;
	}
	public int getNO_COLUMN()
	{
		return NO_COLUMN;
	}
	public int getTIME_COLUMN()
	{
		return TIME_COLUMN;
	}
	
	public void setHeadMessageType(String messageType)
	{
		head.setCurrentRow(HEAD_MESSAGE_TYPE_ROW);
		
		head.setValue(HEAD_MESSAGE_TYPE_COLUMN, messageType);
	}
	
	public String getHeadMessageType()
	{
		head.setCurrentRow(HEAD_MESSAGE_TYPE_ROW);
		
		return head.getValue(HEAD_MESSAGE_TYPE_COLUMN);
	}
	
	public void setHeadTimeRange(String timeRange)
	{
		head.setCurrentRow(HEAD_TIME_RANGE_ROW);
		
		head.setValue(HEAD_TIME_RANGE_COLUMN, timeRange);
	}
	
	public String getHeadTimeRange()
	{
		head.setCurrentRow(HEAD_TIME_RANGE_ROW);
		
		return head.getValue(HEAD_TIME_RANGE_COLUMN);
	}
	
	public void setNo(String no)
	{
		list.setValue(NO_COLUMN, no);
	}
	
	public String getNo()
	{
		return list.getValue(NO_COLUMN);
	}
	
	public void setTime(String time)
	{
		list.setValue(TIME_COLUMN, time);
	}
	
	public String getTime()
	{
		return list.getValue(TIME_COLUMN);
	}
	
	public void setMessageType(String messageType)
	{
		list.setValue(MESSAGE_TYPE_COLUMN, messageType);
	}
	
	public String getMessageType()
	{
		return list.getValue(MESSAGE_TYPE_COLUMN);
	}
	
	public void setMessage(String message)
	{
		list.setValue(MESSAGE_COLUMN, message);
	}
	
	public String getMessage()
	{
		return list.getValue(MESSAGE_COLUMN);
	}
	
	public void setHeadValue(MessageInfoHead head)
	{
		setHeadMessageType(head.getMessageType());
		setHeadTimeRange(StringUtils.formatDateFromDBToPage(head.getDateFrom())
				+ StringUtils.formatDateFromDBToPage(head.getTimeFrom()) + "～"
				+ StringUtils.formatDateFromDBToPage(head.getDateTo())
				+ StringUtils.formatDateFromDBToPage(head.getTimeTo()));
	}
	
	public void setRowValueByEntity(MessageInfoEntity entity,int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setTime(StringUtils.formatDateAndTimeFromDBToPage(entity.getTime()));
		setMessageType(entity.getMessageType());
		setMessage(entity.getMessage());
	}
}
