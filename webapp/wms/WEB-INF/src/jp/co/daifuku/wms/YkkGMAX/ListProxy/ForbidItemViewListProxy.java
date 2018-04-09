package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.ForbidItemViewEntity;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ForbidItemViewListProxy
{
	public ForbidItemViewListProxy(ListCell list)
	{
		this.list = list;
	}
	
	private ListCell list;
	
	private int NO_COLUMN = 1;

	private int ITEM_CODE_COLUMN = 2;
	
	private int TICKET_NO_FROM_COLUMN = 3;
	
	private int TICKET_NO_TO_COLUMN = 4;
	
	private int COLOR_CODE_COLUMN = 5;
	
	private int STOCKIN_DATE_FROM_COLUMN = 6;
	
	private int STOCKIN_DATE_TO_COLUMN = 7;

	public int getSTOCKIN_DATE_FROM_COLUMN()
	{
		return STOCKIN_DATE_FROM_COLUMN;
	}

	public int getSTOCKIN_DATE_TO_COLUMN()
	{
		return STOCKIN_DATE_TO_COLUMN;
	}

	public int getCOLOR_CODE_COLUMN()
	{
		return COLOR_CODE_COLUMN;
	}

	public int getTICKET_NO_FROM_COLUMN()
	{
		return TICKET_NO_FROM_COLUMN;
	}

	public int getTICKET_NO_TO_COLUMN()
	{
		return TICKET_NO_TO_COLUMN;
	}

	public int getITEM_CODE_COLUMN()
	{
		return ITEM_CODE_COLUMN;
	}

	public int getNO_COLUMN()
	{
		return NO_COLUMN;
	}
	
	public String getNo()
	{
		return list.getValue(NO_COLUMN);
	}
	
	public void setNo(String no)
	{
		list.setValue(NO_COLUMN, no);
	}
	
	public String getItemCode()
	{
		return list.getValue(ITEM_CODE_COLUMN);
	}
	
	public void setItemCode(String itemCode)
	{
		list.setValue(ITEM_CODE_COLUMN, itemCode);
	}
	
	public String getTicketNoFrom()
	{
		return list.getValue(TICKET_NO_FROM_COLUMN);
	}
	
	public void setTicketNoFrom(String ticketNoFrom)
	{
		list.setValue(TICKET_NO_FROM_COLUMN, ticketNoFrom);
	}
	
	public String getTicketNoTo()
	{
		return list.getValue(TICKET_NO_TO_COLUMN);
	}
	
	public void setTicketNoTo(String ticketNoTo)
	{
		list.setValue(TICKET_NO_TO_COLUMN, ticketNoTo);
	}
	
	public String getColorCode()
	{
		return list.getValue(COLOR_CODE_COLUMN);
	}
	
	public void setColorCode(String colorCode)
	{
		list.setValue(COLOR_CODE_COLUMN, colorCode);
	}
	
	public String getStockinDateFrom()
	{
		return list.getValue(STOCKIN_DATE_FROM_COLUMN);
	}
	
	public void setStockinDateFrom(String stockinDateFrom)
	{
		list.setValue(STOCKIN_DATE_FROM_COLUMN, stockinDateFrom);
	}
	
	public String getStockinDateTo()
	{
		return list.getValue(STOCKIN_DATE_TO_COLUMN);
	}
	
	public void setStockinDateTo(String stockinDateTo)
	{
		list.setValue(STOCKIN_DATE_TO_COLUMN, stockinDateTo);
	}
	
	public void setRowValueByEntity(ForbidItemViewEntity entity,int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setItemCode(entity.getItemCode());
		setTicketNoFrom(entity.getTicketNoFrom());
		setTicketNoTo(entity.getTicketNoTo());
		setColorCode(entity.getColorCode());
		setStockinDateFrom(StringUtils.formatDateAndTimeFromDBToPage(entity.getStockinDateTimeFrom()));
		setStockinDateTo(StringUtils.formatDateAndTimeFromDBToPage(entity.getStockinDateTimeTo()));
	}
}
