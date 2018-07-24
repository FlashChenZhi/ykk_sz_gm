package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.ForcibleRemoveTicketNoEntity;
import jp.co.daifuku.wms.YkkGMAX.ListHandler.SystemIdSortableHandler;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ForcibleRemoveTicketNoUpListProxy
{
	private final String FORCIBLE_REMOVE_TICKET_NO_UP_LIST = "FORCIBLE_REMOVE_TICKET_NO_UP_LIST";

	public ForcibleRemoveTicketNoUpListProxy(ListCell list, Page page)
	{
		this.list = list;
		this.page = page;
	}
	
	private ListCell list;
	
	private Page page;
	
	private int ALL_COLUMN = 1;
	
	private int RECEPTION_DATETIME_COLUMN = 2;
	
	private int TICKET_NO_COLUMN = 3;
	
	private int ITEM_CODE_COLUMN = 4;
	
	private int COLOR_CODE_COLUMN = 5;
	
	private int INSTOCK_COUNT_COLUMN = 6;
	
	private int SYSTEM_ID_COLUMN = 7;

	public int getALL_COLUMN()
	{
		return ALL_COLUMN;
	}

	public int getCOLOR_CODE_COLUMN()
	{
		return COLOR_CODE_COLUMN;
	}

	public int getINSTOCK_COUNT_COLUMN()
	{
		return INSTOCK_COUNT_COLUMN;
	}

	public int getITEM_CODE_COLUMN()
	{
		return ITEM_CODE_COLUMN;
	}

	public int getRECEPTION_DATETIME_COLUMN()
	{
		return RECEPTION_DATETIME_COLUMN;
	}

	public int getSYSTEM_ID_COLUMN()
	{
		return SYSTEM_ID_COLUMN;
	}

	public int getTICKET_NO_COLUMN()
	{
		return TICKET_NO_COLUMN;
	}
	
	public boolean getAll()
	{
		return list.getChecked(ALL_COLUMN);
	}
	
	public void setAll(boolean all)
	{
		list.setChecked(ALL_COLUMN, all);
	}
	
	public String getReceptionDateTime()
	{
		return list.getValue(RECEPTION_DATETIME_COLUMN);
	}
	
	public void setReceptionDateTime(String receptionDateTime)
	{
		list.setValue(RECEPTION_DATETIME_COLUMN, receptionDateTime);
	}
	
	public String getTicketNo()
	{
		return list.getValue(TICKET_NO_COLUMN);
	}
	
	public void setTicketNo(String ticketNo)
	{
		list.setValue(TICKET_NO_COLUMN, ticketNo);
	}
	
	public String getColorCode()
	{
		return list.getValue(COLOR_CODE_COLUMN);
	}

	public void setColorCode(String color)
	{
		list.setValue(COLOR_CODE_COLUMN, color);
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
	
	public String getItemCode()
	{
		return list.getValue(ITEM_CODE_COLUMN);
	}

	public void setItemCode(String itemCode)
	{
		list.setValue(ITEM_CODE_COLUMN, itemCode);
	}
	
	public String getSystemId()
	{
		return list.getValue(SYSTEM_ID_COLUMN);
	}
	
	public void setSystemId(String systemId)
	{
		list.setValue(SYSTEM_ID_COLUMN, systemId);
	}
	
	public ForcibleRemoveTicketNoEntity getforcibleRemoveTicketNoUpEntity()
	{
		ForcibleRemoveTicketNoEntity entity = new ForcibleRemoveTicketNoEntity();
		
		entity.setReceptionDateTime(StringUtils.formatDateAndTimeFromPageToDB(getReceptionDateTime()));
		entity.setTicketNo(getTicketNo());
		entity.setItemCode(getItemCode());
		entity.setColorCode(getColorCode());
		entity.setInstockCount(getInstockCount());
		entity.setSystemId(getSystemId());
		
		return entity;
	}
	
	public void setRowValueByEntity(ForcibleRemoveTicketNoEntity entity)
	{
		setReceptionDateTime(StringUtils.formatDateAndTimeFromDBToPage(entity.getReceptionDateTime()));
		setTicketNo(entity.getTicketNo());
		setItemCode(entity.getItemCode());
		setColorCode(entity.getColorCode());
		setInstockCount(entity.getInstockCount());
		setSystemId(entity.getSystemId());
		
		if(SystemIdSortableHandler.contain((ArrayList)page.getSession().getAttribute(FORCIBLE_REMOVE_TICKET_NO_UP_LIST), entity.getSystemId()))
		{
			setAll(true);
		}
		else
		{
			setAll(false);
		}
	}
}
