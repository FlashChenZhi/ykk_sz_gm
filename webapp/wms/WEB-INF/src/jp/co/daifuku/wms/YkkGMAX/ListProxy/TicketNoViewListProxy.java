package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.TicketNoViewEntity;

public class TicketNoViewListProxy
{
	public TicketNoViewListProxy(ListCell list)
	{
		this.list = list;
	}
	
	private ListCell list;
	
	private int NO_COLUMN = 1;
	
	private int TICKET_NO_COLUMN = 2;
	
	private int ITEM_CODE_COLUMN = 3;
	
	private int ITEM_NAME_COLUMN = 4;
	
	private int COLOR_CODE_COLUMN = 5;
	
	private int INSTOCK_COUNT_COLUMN = 6;

	public int getINSTOCK_COUNT_COLUMN()
	{
		return INSTOCK_COUNT_COLUMN;
	}

	public int getITEM_CODE_COLUMN()
	{
		return ITEM_CODE_COLUMN;
	}

	public int getITEM_NAME_COLUMN()
	{
		return ITEM_NAME_COLUMN;
	}

	public int getNO_COLUMN()
	{
		return NO_COLUMN;
	}

	public int getTICKET_NO_COLUMN()
	{
		return TICKET_NO_COLUMN;
	}
	
	public int getCOLOR_CODE_COLUMN()
	{
		return COLOR_CODE_COLUMN;
	}

	public void setNo(String no)
	{
		list.setValue(NO_COLUMN, no);
	}
	
	public String getNo()
	{
		return list.getValue(NO_COLUMN);
	}
	
	public void setTicketNo(String ticketNo)
	{
		list.setValue(TICKET_NO_COLUMN, ticketNo);
	}
	
	public String getTicketNo()
	{
		return list.getValue(TICKET_NO_COLUMN);
	}
	
	public void setItemCode(String itemCode)
	{
		list.setValue(ITEM_CODE_COLUMN, itemCode);
	}
	
	public String getItemCode()
	{
		return list.getValue(ITEM_CODE_COLUMN);
	}
	
	public void setItemName(String itemName)
	{
		list.setValue(ITEM_NAME_COLUMN, itemName);
	}
	
	public String getItemName()
	{
		return list.getValue(ITEM_NAME_COLUMN);
	}
	
	public void setColorCode(String colorCode)
	{
		list.setValue(COLOR_CODE_COLUMN, colorCode);
	}
	
	public String getColorCode()
	{
		return list.getValue(COLOR_CODE_COLUMN);
	}
	
	public int getInstockCount()
	{
		String instockCount = list.getValue(INSTOCK_COUNT_COLUMN);
		if (instockCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(
						INSTOCK_COUNT_COLUMN).replaceAll(",", ""));
			}
			catch (Exception ex)
			{
				return 0;
			}
		}
		else
		{
			return 0;
		}
	}

	public void setInstockCount(int instockCount)
	{
		list.setValue(INSTOCK_COUNT_COLUMN, DecimalFormat
				.getIntegerInstance().format(instockCount));
	}
	
	public TicketNoViewEntity getTicketNoViewEntity()
	{
		TicketNoViewEntity entity = new TicketNoViewEntity();
		
		entity.setTicketNo(getTicketNo());
		entity.setItemCode(getItemCode());
		entity.setItemName(getItemName());
		entity.setColorCode(getColorCode());
		entity.setInstockCount(getInstockCount());
		
		return entity;
	}
	
	public void setRowValueByEntity(TicketNoViewEntity entity, int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setTicketNo(entity.getTicketNo());
		setItemCode(entity.getItemCode());
		setItemName(entity.getItemName());
		setColorCode(entity.getColorCode());
		setInstockCount(entity.getInstockCount());
	}
}
