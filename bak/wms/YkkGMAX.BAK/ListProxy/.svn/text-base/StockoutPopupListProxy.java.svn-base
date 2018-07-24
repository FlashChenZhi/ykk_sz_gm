package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.StockoutDetailEntity;
import jp.co.daifuku.wms.YkkGMAX.ListHandler.SystemIdSortableHandler;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class StockoutPopupListProxy
{
	public StockoutPopupListProxy(ListCell list, Page page)
	{
		this.list = list;
		this.page = page;
	}

	private ListCell list;

	private Page page;

	private int ALL_COLUMN = 1;

	private int STOCKIN_TIME_COLUMN = 2;

	private int LOCATION_NO_COLUMN = 3;

	private int TICKET_NO_COLUMN = 4;

	private int STATUS_COLUMN = 5;

	private int ENABLE_TO_STOCKOUT_COUNT_COLUMN = 6;

	private int REMARK_COLUMN = 7;

	private int ORIGINAL_LOCATION_NO_COLUMN = 8;

	private int SYSTEM_ID_COLUMN = 9;

	private final String STOCKOUT_DETAIL_LIST = "STOCKOUT_DETAIL_LIST";

	public int getTICKET_NO_COLUMN()
	{
		return TICKET_NO_COLUMN;
	}

	public int getALL_COLUMN()
	{
		return ALL_COLUMN;
	}

	public int getENABLE_TO_STOCKOUT_COUNT_COLUMN()
	{
		return ENABLE_TO_STOCKOUT_COUNT_COLUMN;
	}

	public int getLOCATION_NO_COLUMN()
	{
		return LOCATION_NO_COLUMN;
	}

	public int getREMARK_COLUMN()
	{
		return REMARK_COLUMN;
	}

	public int getSTATUS_COLUMN()
	{
		return STATUS_COLUMN;
	}

	public int getSTOCKIN_TIME_COLUMN()
	{
		return STOCKIN_TIME_COLUMN;
	}

	public int getORIGINAL_LOCATION_NO_COLUMN()
	{
		return ORIGINAL_LOCATION_NO_COLUMN;
	}

	public int getSYSTEM_ID_COLUMN()
	{
		return SYSTEM_ID_COLUMN;
	}

	public boolean getAll()
	{
		return list.getChecked(ALL_COLUMN);
	}

	public void setAll(boolean all)
	{
		list.setChecked(ALL_COLUMN, all);
	}

	public String getStockinTime()
	{
		return list.getValue(STOCKIN_TIME_COLUMN);
	}

	public void setStockinTime(String stockinTime)
	{
		list.setValue(STOCKIN_TIME_COLUMN, stockinTime);
	}

	public String getLocationNo()
	{
		return list.getValue(LOCATION_NO_COLUMN);
	}

	public void setLocationNo(String locationNo)
	{
		list.setValue(LOCATION_NO_COLUMN, locationNo);
	}

	public String getStatus()
	{
		return list.getValue(STATUS_COLUMN);
	}

	public void setStatus(String status)
	{
		list.setValue(STATUS_COLUMN, status);
	}

	public int getEnableToStockoutCount()
	{
		String enableToStockoutCount = list
				.getValue(ENABLE_TO_STOCKOUT_COUNT_COLUMN);
		if (enableToStockoutCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(
						ENABLE_TO_STOCKOUT_COUNT_COLUMN).replaceAll(",", ""));
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

	public void setEnableToStockoutCount(int enableToStockoutCount)
	{
		list.setValue(ENABLE_TO_STOCKOUT_COUNT_COLUMN, DecimalFormat
				.getIntegerInstance().format(enableToStockoutCount));
	}

	public String getTicketNo()
	{
		return list.getValue(TICKET_NO_COLUMN);
	}

	public void setTicketNo(String ticketNo)
	{
		list.setValue(TICKET_NO_COLUMN, ticketNo);
	}

	public String getRemark()
	{
		return list.getValue(REMARK_COLUMN);
	}

	public void setRemark(String remark)
	{
		list.setValue(REMARK_COLUMN, remark);
	}

	public String getOriginalLocationNo()
	{
		return list.getValue(ORIGINAL_LOCATION_NO_COLUMN);
	}

	public void setOriginalLocationNo(String locationNo)
	{
		list.setValue(ORIGINAL_LOCATION_NO_COLUMN, locationNo);
	}

	public String getSystemId()
	{
		return list.getValue(SYSTEM_ID_COLUMN);
	}

	public void setSystemId(String systemId)
	{
		list.setValue(SYSTEM_ID_COLUMN, systemId);
	}

	public StockoutDetailEntity getStockoutDetailEntity()
	{
		StockoutDetailEntity entity = new StockoutDetailEntity();

		entity.setStockinTime(StringUtils
				.formatDateAndTimeFromPageToDB(getStockinTime()));
		entity.setLocationNo(StringUtils
				.formatLocationNoFromPageToDB(getLocationNo()));
		entity.setTicketNo(getTicketNo());
		entity.setStatus(getStatus());
		entity.setEnableToStockoutCount(getEnableToStockoutCount());
		entity.setRemark(getRemark());
		entity.setOriginalLocationNo(getOriginalLocationNo());
		entity.setSystemId(getSystemId());
		return entity;
	}

	public void setRowValueByEntity(StockoutDetailEntity entity)
	{
		setStockinTime(StringUtils.formatDateAndTimeFromDBToPage(entity
				.getStockinTime()));
		setLocationNo(StringUtils.formatLocationNoFromDBToPage(entity
				.getLocationNo()));
		setTicketNo(entity.getTicketNo());
		setStatus(entity.getStatus());
		setEnableToStockoutCount(entity.getEnableToStockoutCount());
		setRemark(entity.getRemark());
		setOriginalLocationNo(entity.getOriginalLocationNo());
		setSystemId(entity.getSystemId());
		if (SystemIdSortableHandler.contain((ArrayList) page.getSession()
				.getAttribute(STOCKOUT_DETAIL_LIST), entity.getSystemId()))
		{
			setAll(true);
		}
		else
		{
			setAll(false);
		}
	}

}
