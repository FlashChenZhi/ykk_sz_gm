package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import jp.co.daifuku.bluedog.ui.control.FixedListCell;
import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.ErrorMessageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.ErrorMessageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ErrorMessageInfoListProxy
{
	public ErrorMessageInfoListProxy(FixedListCell head, ListCell list)
	{
		this.head = head;
		this.list = list;
	}

	public ErrorMessageInfoListProxy(ListCell list)
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

	private int ERROR_MESSAGE_COLUMN = 4;

	public int getERROR_MESSAGE_COLUMN()
	{
		return ERROR_MESSAGE_COLUMN;
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

	public void setErrorMessage(String errorMessage)
	{
		list.setValue(ERROR_MESSAGE_COLUMN, errorMessage);
	}

	public String getErrorMessage()
	{
		return list.getValue(ERROR_MESSAGE_COLUMN);
	}

	public void setHeadValue(ErrorMessageInfoHead head)
	{
		setHeadMessageType(head.getMessageType());
		setHeadTimeRange(StringUtils.formatDateFromDBToPage(head.getDateFrom())
				+ StringUtils.formatDateFromDBToPage(head.getTimeFrom()) + "～"
				+ StringUtils.formatDateFromDBToPage(head.getDateTo())
				+ StringUtils.formatDateFromDBToPage(head.getTimeTo()));
	}

	public void setRowValueByEntity(ErrorMessageInfoEntity entity, int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setTime(StringUtils.formatDateAndTimeFromDBToPage(entity.getTime()));
		setMessageType(entity.getMessageType());
		setErrorMessage(entity.getErrorMessage());
	}
}
