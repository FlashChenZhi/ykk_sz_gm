package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.FlatStockoutDetailEntity;
import jp.co.daifuku.wms.YkkGMAX.ListHandler.SystemIdSortableHandler;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class FlatStockoutPopupUpListProxy
{
	private ListCell list = null;

	private Page page = null;

	private final String FLAT_STOCKOUT_DETAIL_LIST = "FLAT_STOCKOUT_DETAIL_LIST";

	private int SELECT_COLUMN = 1;

	private int TICKET_NO_COLUMN = 2;

	private int STORAGE_COUNT_COLUMN = 3;

	private int RECEPTION_DATETIME_COLUMN = 4;

	private int SYSTEM_ID_COLUMN = 5;

	private int INSTOCK_COUNT_COLUMN = 6;

	public FlatStockoutPopupUpListProxy(ListCell list, Page page)
	{
		this.list = list;
		this.page = page;
	}

	public int getINSTOCK_COUNT_COLUMN()
	{
		return INSTOCK_COUNT_COLUMN;
	}

	public int getSELECT_COLUMN()
	{
		return SELECT_COLUMN;
	}

	public int getTICKET_NO_COLUMN()
	{
		return TICKET_NO_COLUMN;
	}

	public int getSTORAGE_COUNT_COLUMN()
	{
		return STORAGE_COUNT_COLUMN;
	}

	public int getRECEPTION_DATETIME_COLUMN()
	{
		return RECEPTION_DATETIME_COLUMN;
	}

	public int getSYSTEM_ID_COLUMN()
	{
		return SYSTEM_ID_COLUMN;
	}

	public void setSelected(boolean selected)
	{
		list.setChecked(SELECT_COLUMN, selected);
	}

	public boolean isSelected()
	{
		return list.getChecked(SELECT_COLUMN);
	}

	public void setTicketNo(String ticketNo)
	{
		list.setValue(TICKET_NO_COLUMN, ticketNo);
	}

	public String getTicketNo()
	{
		return list.getValue(TICKET_NO_COLUMN);
	}

	public int getStorageCount()
	{
		String storageCount = list.getValue(STORAGE_COUNT_COLUMN);
		if (storageCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(STORAGE_COUNT_COLUMN)
						.replaceAll(",", ""));
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

	public void setStorageCount(int storageCount)
	{
		list.setValue(STORAGE_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(storageCount));
	}

	public int getInstockCount()
	{
		String storageCount = list.getValue(INSTOCK_COUNT_COLUMN);
		if (storageCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(INSTOCK_COUNT_COLUMN)
						.replaceAll(",", ""));
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
		list.setValue(INSTOCK_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(instockCount));
	}

	public void setReceptionDatetime(String receptionDatetime)
	{
		list.setValue(RECEPTION_DATETIME_COLUMN, receptionDatetime);
	}

	public String getReceptionDatetime()
	{
		return list.getValue(RECEPTION_DATETIME_COLUMN);
	}

	public void setSystemId(String systemId)
	{
		list.setValue(SYSTEM_ID_COLUMN, systemId);
	}

	public String getSystemId()
	{
		return list.getValue(SYSTEM_ID_COLUMN);
	}

	public void setRowValue(FlatStockoutDetailEntity entity)
	{
		setTicketNo(entity.getTicketNo());
		setStorageCount(entity.getStockoutCount());
		setReceptionDatetime(StringUtils.formatDateAndTimeFromDBToPage(entity
				.getReceptionDatetime()));
		setSystemId(entity.getSystemId());
		if (SystemIdSortableHandler.contain((ArrayList) page.getSession()
				.getAttribute(FLAT_STOCKOUT_DETAIL_LIST), entity.getSystemId()))
		{
			setSelected(true);
		}
		else
		{
			setSelected(false);
		}
		setInstockCount(entity.getInstockCount());

	}

	public FlatStockoutDetailEntity getRowValue()
	{
		FlatStockoutDetailEntity entity = new FlatStockoutDetailEntity();

		entity.setTicketNo(getTicketNo());
		entity.setStorageCount(getStorageCount());
		entity.setReceptionDatetime(StringUtils
				.formatDateAndTimeFromPageToDB(getReceptionDatetime()));
		entity.setSystemId(getSystemId());
		entity.setInstockCount(getInstockCount());

		return entity;
	}
}
