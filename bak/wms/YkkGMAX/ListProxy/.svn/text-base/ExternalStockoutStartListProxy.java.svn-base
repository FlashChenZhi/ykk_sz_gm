package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExternalStockoutStartEntity;
import jp.co.daifuku.wms.YkkGMAX.ListHandler.SystemIdSortableHandler;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ExternalStockoutStartListProxy
{
	public ExternalStockoutStartListProxy(ListCell list, Page page)
	{
		this.list = list;
		this.page = page;
	}

	private ListCell list;

	private Page page;

	private int CHECK_BOX_COLUMN = 1;

	private int U_COLUMN = 2;

	private int P_COLUMN = 3;

	private int STOCKIN_TIME_COLUMN = 4;

	private int ITEM_CODE_COLUMN = 5;

	private int ITEM_NAME_1_COLUMN = 6;

	private int ITEM_NAME_2_COLUMN = 14;

	private int ITEM_NAME_3_COLUMN = 15;

	private int COLOR_COLUMN = 7;

	private int LOCATION_NO_COLUMN = 8;

	private int TICKET_NO_COLUMN = 9;

	private int STOCKOUT_COUNT_COLUMN = 10;

	private int ORIGINAL_LOCATION_NO_COLUMN = 11;

	private int SYSTEM_ID_COLUMN = 12;

	private int MCKEY_COLUMN = 13;

	private final String EXTERNAL_STOCKOUT_START_LIST = "EXTERNAL_STOCKOUT_START_LIST";

	public int getTICKET_NO_COLUMN()
	{
		return TICKET_NO_COLUMN;
	}

	public int getSTOCKOUT_COUNT_COLUMN()
	{
		return STOCKOUT_COUNT_COLUMN;
	}

	public int getMCKEY_COLUMN()
	{
		return MCKEY_COLUMN;
	}

	public int getCHECK_BOX_COLUMN()
	{
		return CHECK_BOX_COLUMN;
	}

	public int getCOLOR_COLUMN()
	{
		return COLOR_COLUMN;
	}

	public int getITEM_CODE_COLUMN()
	{
		return ITEM_CODE_COLUMN;
	}

	public int getITEM_NAME_1_COLUMN()
	{
		return ITEM_NAME_1_COLUMN;
	}

	public int getITEM_NAME_2_COLUMN()
	{
		return ITEM_NAME_2_COLUMN;
	}

	public int getITEM_NAME_3_COLUMN()
	{
		return ITEM_NAME_3_COLUMN;
	}

	public int getORIGINAL_LOCATION_NO_COLUMN()
	{
		return ORIGINAL_LOCATION_NO_COLUMN;
	}

	public int getSYSTEM_ID_COLUMN()
	{
		return SYSTEM_ID_COLUMN;
	}

	public ListCell getList()
	{
		return list;
	}

	public int getLOCATION_NO_COLUMN()
	{
		return LOCATION_NO_COLUMN;
	}

	public int getP_COLUMN()
	{
		return P_COLUMN;
	}

	public Page getPage()
	{
		return page;
	}

	public int getSTOCKIN_TIME_COLUMN()
	{
		return STOCKIN_TIME_COLUMN;
	}

	public int getU_COLUMN()
	{
		return U_COLUMN;
	}

	public boolean getCheckBox()
	{
		return list.getChecked(CHECK_BOX_COLUMN);
	}

	public void setCheckBox(boolean checkBox)
	{
		list.setChecked(CHECK_BOX_COLUMN, checkBox);
	}

	public String getU()
	{
		return list.getValue(U_COLUMN);
	}

	public void setU(String u)
	{
		list.setValue(U_COLUMN, u);
	}

	public String getP()
	{
		return list.getValue(P_COLUMN);
	}

	public void setP(String p)
	{
		list.setValue(P_COLUMN, p);
	}

	public String getStockinTime()
	{
		return list.getValue(STOCKIN_TIME_COLUMN);
	}

	public void setStockinTime(String stockinTime)
	{
		list.setValue(STOCKIN_TIME_COLUMN, stockinTime);
	}

	public String getItemCode()
	{
		return list.getValue(ITEM_CODE_COLUMN);
	}

	public void setItemCode(String itemCode)
	{
		list.setValue(ITEM_CODE_COLUMN, itemCode);
	}

	public String getItemName1()
	{
		return list.getValue(ITEM_NAME_1_COLUMN);
	}

	public void setItemName1(String itemName1)
	{
		list.setValue(ITEM_NAME_1_COLUMN, itemName1);
	}

	public String getItemName2()
	{
		return list.getValue(ITEM_NAME_2_COLUMN);
	}

	public void setItemName2(String itemName2)
	{
		list.setValue(ITEM_NAME_2_COLUMN, itemName2);
	}

	public String getItemName3()
	{
		return list.getValue(ITEM_NAME_3_COLUMN);
	}

	public void setItemName3(String itemName3)
	{
		list.setValue(ITEM_NAME_3_COLUMN, itemName3);
	}

	public String getColor()
	{
		return list.getValue(COLOR_COLUMN);
	}

	public void setColor(String color)
	{
		list.setValue(COLOR_COLUMN, color);
	}

	public String getLocationNo()
	{
		return list.getValue(LOCATION_NO_COLUMN);
	}

	public void setLocationNo(String locationNo)
	{
		list.setValue(LOCATION_NO_COLUMN, locationNo);
	}

	public String getTicketNo()
	{
		return list.getValue(TICKET_NO_COLUMN);
	}

	public void setTicketNo(String ticketNo)
	{
		list.setValue(TICKET_NO_COLUMN, ticketNo);
	}

	public int getStockoutCount()
	{
		String stockoutCount = list.getValue(STOCKOUT_COUNT_COLUMN);
		if (stockoutCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(STOCKOUT_COUNT_COLUMN)
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

	public void setStockoutCount(int stockoutCount)
	{
		list.setValue(STOCKOUT_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(stockoutCount));
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

	public String getMckey()
	{
		return list.getValue(MCKEY_COLUMN);
	}

	public void setMckey(String mckey)
	{
		list.setValue(MCKEY_COLUMN, mckey);
	}

	public ExternalStockoutStartEntity getStockoutStartEntity()
	{
		ExternalStockoutStartEntity entity = new ExternalStockoutStartEntity();

		entity.setU(getU());
		entity.setP(getP());
		entity.setStockinDateTime(StringUtils
				.formatDateAndTimeFromPageToDB(getStockinTime()));
		entity.setItemCode(getItemCode());
		entity.setItemName1(getItemName1());
		entity.setItemName2(getItemName2());
		entity.setItemName3(getItemName3());
		entity.setColor(getColor());
		entity.setLocationNo(StringUtils
				.formatLocationNoFromPageToDB(getLocationNo()));
		entity.setTicketNo(getTicketNo());
		entity.setStockoutCount(getStockoutCount());
		entity.setOriginalLocationNo(getOriginalLocationNo());
		entity.setSystemId(getSystemId());
		entity.setMckey(getMckey());
		return entity;
	}

	public void setRowValueByEntity(ExternalStockoutStartEntity entity)
	{
		setU(entity.getU());
		setP(entity.getP());
		setStockinTime(StringUtils.formatDateAndTimeFromDBToPage(entity
				.getStockinDateTime()));
		setItemCode(entity.getItemCode());
		setItemName1(entity.getItemName1());
		setItemName2(entity.getItemName2());
		setItemName3(entity.getItemName3());
		setColor(entity.getColor());
		setLocationNo(StringUtils.formatLocationNoFromDBToPage(entity
				.getLocationNo()));
		setTicketNo(entity.getTicketNo());
		setStockoutCount(entity.getStockoutCount());
		setOriginalLocationNo(entity.getOriginalLocationNo());
		setSystemId(entity.getSystemId());
		setMckey(entity.getMckey());

		if (SystemIdSortableHandler.contain((ArrayList) page.getSession()
				.getAttribute(EXTERNAL_STOCKOUT_START_LIST), entity
				.getSystemId()))
		{
			setCheckBox(true);
		}
		else
		{
			setCheckBox(false);
		}
	}
}
